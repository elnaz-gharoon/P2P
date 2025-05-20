package Tests.Implemantations;

import org.example.SSLChatServer;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SSLChatServerTest {
    @Test
    public void testMessageExchange() throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        // Starte den Server in einem Thread
        executor.submit(() -> {
            try {
                SSLChatServer.main(null); // Achtung: Starte lokal testbaren Server
            } catch (Exception ignored) {
            }
        });
        Thread.sleep(2000);

        // Simuliere Client-Verbindung
        Socket socket = new Socket("127.0.0.1", 9999);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        out.println("test message");
        // Beispiel-Erwartung: "Empfangen von Server: test message"
        String response = in.readLine();

        assertTrue(response.contains("test message"));

        out.println("exit");
        socket.close();
    }
    }
