import java.io.*;
import java.net.*;

public class BlockingEchoServer {
    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(5000)) {
            System.out.println("Server started on port 5000...");

            while (true) {
                // 1. Wait (block) until a client connects
                Socket client = server.accept();
                System.out.println("Accepted: " + client.getInetAddress());

                // 2. Handle this one connection
                handleClient(client);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket client) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()))) {

            String line;
            while ((line = in.readLine()) != null) {
                out.write("Echo: " + line + "\n");
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
