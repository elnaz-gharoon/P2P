package org.example;

import java.io.*;
import java.net.*;
import javax.net.ssl.*;

public class SSLChatClient {
    public static void main(String[] args) throws Exception {
        // SSL-Context erstellen
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, null, new java.security.SecureRandom());

        // Verbindung zum Server aufbauen
        SSLSocketFactory socketFactory = sslContext.getSocketFactory();
        SSLSocket socket = (SSLSocket) socketFactory.createSocket("127.0.0.1", 9999);

        System.out.println("Mit Server verbunden: " + socket.getInetAddress().getHostAddress());

        // Streams für die Kommunikation erstellen
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

        // Schleife zum Senden und Empfangen von Nachrichten
        String message;
        while (true) {
            System.out.print("Du: ");
            message = userInput.readLine(); // Eingabe des Benutzers
            writer.println(message); // Nachricht an Server senden

            // Wenn der Benutzer "exit" eingibt, wird die Verbindung beendet
            if (message.equalsIgnoreCase("exit")) {
                break;
            }

            // Nachricht vom Server empfangen
            String response = reader.readLine();
            System.out.println("Server: " + response);
        }

        // Verbindung schließen
        socket.close();
        System.out.println("Verbindung beendet.");
    }
}
