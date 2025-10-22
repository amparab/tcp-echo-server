
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

public class NonBlockingEchoServer {
    public static void main(String[] args) throws IOException {
        // 1. Create a selector (like epoll instance)
        Selector selector = Selector.open();

        // 2. Create a server socket channel (listening socket)
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.bind(new InetSocketAddress(5000));
        serverChannel.configureBlocking(false);

        // 3. Register it with selector for "ACCEPT" events: epoll_ctl(ADD, server_fd, EPOLLIN)
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("Server started on port 5000...");

        // 4. Start event loop
        while (true) {
            // This is like epoll_wait(): blocks until any channel is ready
            selector.select();

            // 5. Get list of ready channels (events)
            Iterator<SelectionKey> keys = selector.selectedKeys().iterator();

            while (keys.hasNext()) {
                SelectionKey key = keys.next();
                keys.remove();

                if (key.isAcceptable()) {
                    // New client connection
                    ServerSocketChannel server = (ServerSocketChannel) key.channel();
                    SocketChannel client = server.accept();
                    client.configureBlocking(false);
                    // epoll_ctl(ADD, server_fd, EPOLLIN)
                    client.register(selector, SelectionKey.OP_READ);
                    System.out.println("Accepted connection from " + client.getRemoteAddress());
                }
                else if (key.isReadable()) {
                    // Data available to read
                    SocketChannel client = (SocketChannel) key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(256);
                    int bytesRead = client.read(buffer);

                    if (bytesRead == -1) {
                        client.close();
                        System.out.println("Connection closed");
                    } else {
                        buffer.flip();
                        client.write(buffer); // Echo back
                    }
                }
            }
        }
    }
}
