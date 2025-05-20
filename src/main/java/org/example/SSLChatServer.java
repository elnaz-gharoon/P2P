package org.example;

import javax.net.ssl.*;
import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Scanner;

public class SSLChatServer {
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

            // SSLServerSocketFactory erstellen
            SSLServerSocketFactory serverSocketFactory = sslContext.getServerSocketFactory();
            SSLServerSocket serverSocket = (SSLServerSocket) serverSocketFactory.createServerSocket(9999);
            System.out.println("SSL-Server läuft auf Port 9999...");

            while (true) {
                // Auf Client-Verbindung warten
                SSLSocket socket = (SSLSocket) serverSocket.accept();
                System.out.println("Client verbunden: " + socket.getInetAddress());

                // Eingabestream vom Client lesen
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                // Nachricht vom Client lesen (in diesem Fall "Hallo")
                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println("Nachricht vom Client: " + message);
                }

                // Verbindung schließen
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
