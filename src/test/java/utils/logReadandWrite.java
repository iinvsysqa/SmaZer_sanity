package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import jssc.SerialPort;
import jssc.SerialPortException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;



public class logReadandWrite {
    private static logReadandWrite instance = null;
    private SerialPort serialPort;
    private volatile boolean running = false;
    private final Path logFilePath = Path.of("device.log");  // Changed to .log extension
    private Thread logThread;
    private final Object writeLock = new Object();
    private final Queue<String> logQueue = new ConcurrentLinkedQueue<>();

    public logReadandWrite(String portName) {
        serialPort = new SerialPort(portName);
    }

    public static synchronized logReadandWrite getInstance(String portName) {
        if (instance == null) {
            instance = new logReadandWrite(portName);
        }
        return instance;
    }

    public synchronized void openPort() {
        try {
            if (!serialPort.isOpened()) {
                serialPort.openPort();
                serialPort.setParams(115200, 8, 1, 0);
                running = true;

                // Clear the log file at the start
                Files.writeString(logFilePath, "", java.nio.file.StandardOpenOption.TRUNCATE_EXISTING, java.nio.file.StandardOpenOption.CREATE);

                System.out.println("Port opened successfully.");
                startLogThread();
                startLogFlusher(); // Ensure log flushing happens in the background
            } else {
                System.out.println("Port is already open.");
            }
        } catch (SerialPortException | IOException ex) {
            System.out.println("Error opening port: " + ex.getMessage());
        }
    }

    private void startLogThread() {
        if (logThread == null || !logThread.isAlive()) {
            logThread = new Thread(() -> {
                while (running) {
                    try {
                        if (serialPort.isOpened()) {
                            int availableBytes = serialPort.getInputBufferBytesCount();
                            if (availableBytes > 0) {
                                byte[] buffer = serialPort.readBytes(availableBytes);
                                if (buffer != null && buffer.length > 0) {
                                    String received = new String(buffer).trim();
                                    logQueue.add(received);
                                }
                            }
                        } else {
                            System.out.println("Error: Port is closed.");
                            break;
                        }
                    } catch (SerialPortException ex) {
                        System.out.println("Error reading from port: " + ex.getMessage());
                    }

                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        System.out.println("Log thread interrupted.");
                        break;
                    }
                }
            });
            logThread.start();
        }
    }

    public void write(String comment) {
        new Thread(() -> {
            synchronized (writeLock) {
                try {
                    if (serialPort.isOpened()) {
                        serialPort.writeBytes(comment.getBytes());
                        System.out.println("Comment sent: " + comment);
                    } else {
                        System.out.println("Error: Cannot write, port is not open.");
                    }
                } catch (SerialPortException ex) {
                    System.out.println("Error writing to port: " + ex.getMessage());
                }
            }
        }).start();
    }

    public synchronized void closePort() {
        try {
            if (serialPort.isOpened()) {
                running = false;
                if (logThread != null) {
                    logThread.interrupt();
                }
                serialPort.closePort();
                flushLogsToFile();  // Ensure any leftover logs are saved
                System.out.println("Port closed successfully.");
            } else {
                System.out.println("Port is already closed.");
            }
        } catch (SerialPortException ex) {
            System.out.println("Error closing port: " + ex.getMessage());
        }
    }

    private void startLogFlusher() {
        new Thread(() -> {
            while (running) {
                flushLogsToFile();
                try {
                    Thread.sleep(5000); // Flush logs every 5 seconds
                } catch (InterruptedException e) {
                    System.out.println("Log flusher interrupted.");
                    break;
                }
            }
        }).start();
    }

    public void flushLogsToFile() {
        synchronized (writeLock) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFilePath.toFile(), true))) {
                while (!logQueue.isEmpty()) {
                    writer.write(logQueue.poll() + System.lineSeparator());
                }
                writer.flush();
            } catch (IOException e) {
                System.out.println("Error writing to file: " + e.getMessage());
            }
        }
    }
}
