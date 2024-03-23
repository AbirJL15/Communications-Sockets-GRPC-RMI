
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;

    public  Client(Socket socket , String username) {
        try {
            this.socket=socket;
            this.bufferedWriter=new BufferedWriter( new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username=username;
        } catch (IOException e) {
            closeEverything(socket,bufferedReader,bufferedWriter);
        }
    }
    public void sendMessage(){ //send message to the client handler
        try {
            bufferedWriter.write(username);//so the client handler knows who we are
            bufferedWriter.newLine();//
            bufferedWriter.flush();

            Scanner scanner = new Scanner(System.in);//get input from the console
            while (socket.isConnected()){
                String messageToSend= scanner.nextLine();//get what the user typed and send it over
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
                        msgFromGroupChat=bufferedReader.readLine(); //read
                        System.out.println(msgFromGroupChat);  //ouput
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
        Socket socket = new Socket("localhost", 1234);
        Client client=new Client(socket, username);
        client.listenForMessage();
        client.sendMessage();

    }
}
