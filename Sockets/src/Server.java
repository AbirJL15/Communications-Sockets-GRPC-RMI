
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;

    // Constructor to initialize the ServerSocket
    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }
    // Method to start the server and listen for incoming connections
    public void start_Server() { //to keep our server running
        try {
            System.out.println("Waiting for connections .. ");
            // Continuously listen for connections until the server socket is closed
            while (!serverSocket.isClosed()){
                // Accept incoming client connection
                Socket socket =serverSocket.accept();
                System.out.println("A new client has connected ");
                // Create a new thread for handling the client
                ClientHandler clientHandler = new ClientHandler(socket);//responsible for communcating with the client
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        }
        catch (IOException e){
            e.printStackTrace();

        }
    }

    public void closeServerSocket(){
        try {
            if (serverSocket!=null) {
                serverSocket.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1234); //server listening for clients on this port number
        Server server = new Server(serverSocket);// Create a new server instance
        server.start_Server();// Start the server
    }
}
