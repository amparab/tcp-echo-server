## Steps to demo

### Blocking Server

Run the following commands in **linux** terminal
```bash
javac BlockingEchoServer.java
java BlockingEchoServer
```
You should see something like `Server started on port 5000...`

Open a second terminal. This represents your first client.
In this terminal run the following command
```bash
nc 127.0.0.1 5000
```
On the server, you should see something like `Accepted: /127.0.0.1`
On your client terminal, try typing any input and press enter. The server should echo it back to you.

Now, open a third terminal. This represents your second client.
Try to connect with the server from this client.
```bash
nc 127.0.0.1 5000
```
This connection would not be accepted by the server and it would go in waiting mode.
Now, if you close your first client, your second client will get connected immediately.

This is the nature of Blocking Server.

### Non Blocking Server

Run the following commands in **linux** terminal
```bash
javac NonBlockingEchoServer.java
java NonBlockingEchoServer
```
You should see something like `Server started on port 5000...`

Open a second terminal. This represents your first client.
In this terminal run the following command
```bash
nc 127.0.0.1 5000
```
On the server, you should see something like `Accepted: /127.0.0.1`
On your client terminal, try typing any input and press enter. The server should echo it back to you.

Now, open a third terminal. This represents your second client.
Try to connect with the server from this client.
```bash
nc 127.0.0.1 5000
```
Now, you would see that our second client has successfully connected.
This is epoll doing it's magic under the hood!

To know more about how this works, do give my blog a read. [I/O Multiplexing: From the basics](https://medium.com/@amruta0303/i-o-multiplexing-from-the-basics-238c27aaf13f)