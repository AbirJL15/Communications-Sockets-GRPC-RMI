
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;


    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void start_Server() { //to keep our server running
        try {
            System.out.println("Waiting for connections .. ");
            while (!serverSocket.isClosed()){

                Socket socket =serverSocket.accept();//waiting for a client to connect, a blocking method
                System.out.println("A new client has connected ");
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
        Server server = new Server(serverSocket);
        server.start_Server();
    }
}
