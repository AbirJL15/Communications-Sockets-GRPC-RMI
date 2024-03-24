
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket; // Socket for communication with the server
    private BufferedReader bufferedReader;// Reader to read data from the server
    private BufferedWriter bufferedWriter;// Writer to send data to the server
    private String username;

    // Constructor to initialize client with a socket and username
    public  Client(Socket socket , String username) {
        try {
            this.socket=socket;
            // Initialize reader and writer for the socket's input and output streams
            this.bufferedWriter=new BufferedWriter( new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username=username;
        } catch (IOException e) {
            closeEverything(socket,bufferedReader,bufferedWriter);
        }
    }

    // Method to send messages to the server
    public void sendMessage(){ //send message to the client handler
        try {

            bufferedWriter.write(username);//so the client handler knows who we are
            bufferedWriter.newLine();
            bufferedWriter.flush();

            Scanner scanner = new Scanner(System.in);//get input from the console
            while (socket.isConnected()){
                String messageToSend= scanner.nextLine();//get what the user typed and send it over
                // Send the message to the server with username prefix
                bufferedWriter.write(username + ": " + messageToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (IOException e){
            closeEverything(socket,bufferedReader, bufferedWriter);
        }
    }


    public void listenForMessage(){ //for listening to messages from the server (messages that have been broadcasted from other users)
        new Thread(new Runnable() { //because its a blocking operation
            @Override
            public void run() { //executed in a different thread
                String msgFromGroupChat;

                while (socket.isConnected()){ //connected to the server
                    try {
                        msgFromGroupChat=bufferedReader.readLine(); //read messages from the server
                        System.out.println(msgFromGroupChat);  //Print received message to the console
                    } catch (IOException e){
                        closeEverything(socket,bufferedReader, bufferedWriter);
                    }
                }

            }
        }).start();
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try {
            if(bufferedReader!= null){
                bufferedReader.close();
            }
            if(bufferedWriter!=null){
                bufferedWriter.close();
            }
            if (socket!=null){
                socket.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your username for the group chat");
        String username = scanner.nextLine();
        // Connect to the server running on localhost at port 1234
        Socket socket = new Socket("localhost", 1234);
        // Create a new client instance with the provided username and socket
        Client client=new Client(socket, username);
        // Start listening for messages from the server
        client.listenForMessage();
        // Start sending messages to the server
        client.sendMessage();

    }
}
