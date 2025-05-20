package org.example;

import javax.net.ssl.*;
import java.io.*;
import java.security.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class SSLChatServer {

    private static final AtomicInteger clientCounter = new AtomicInteger(1);

    public static void main(String[] args) {
        try {
            // Keystore laden
            char[] keystorePassword = "2883gh".toCharArray();
            KeyStore keystore = KeyStore.getInstance("PKCS12");
            FileInputStream keystoreFile = new FileInputStream("C:\\Users\\ElnazGharoon\\chatkeystore.p12");
            keystore.load(keystoreFile, keystorePassword);

            // KeyManagerFactory initialisieren
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(keystore, keystorePassword);

            // SSLContext initialisieren
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(kmf.getKeyManagers(), null, new SecureRandom());

            // SSLServerSocket erstellen
            SSLServerSocketFactory serverSocketFactory = sslContext.getServerSocketFactory();
            SSLServerSocket serverSocket = (SSLServerSocket) serverSocketFactory.createServerSocket(9999);
            System.out.println("SSL-Server läuft auf Port 9999...");

            ExecutorService pool = Executors.newCachedThreadPool();

            while (true) {
                SSLSocket clientSocket = (SSLSocket) serverSocket.accept();
                int clientId = clientCounter.getAndIncrement();
                pool.execute(() -> handleClient(clientSocket, clientId));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(SSLSocket socket, int clientId) {
        try {
            System.out.println("Client #" + clientId + " verbunden: " + socket.getInetAddress());

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("[Client " + clientId + "]: " + message);
                out.println("Empfangen von Server: " + message);

                if (message.equalsIgnoreCase("exit")) {
                    break;
                }
            }

            socket.close();
            System.out.println("Client #" + clientId + " getrennt.");

        } catch (IOException e) {
            System.out.println("Fehler mit Client #" + clientId + ": " + e.getMessage());
        }
    }
}
