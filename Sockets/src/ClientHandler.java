
import java.io.*;
import java.net.Socket;
import java.nio.channels.ScatteringByteChannel;
import java.util.ArrayList;

public class ClientHandler implements Runnable {

    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>(); //to keep track of all our clients
    private Socket socket; //to establish a connection between a client and a server
    private BufferedReader bufferedReader; //to read data sent from the client
    private BufferedWriter bufferedWriter; //to send data to clients
    private String clientUsername;

    public ClientHandler(Socket socket)
    {
        try{
            this.socket=socket;
            this.bufferedWriter=new BufferedWriter( new OutputStreamWriter(socket.getOutputStream()));//caracter stream: OutputStreamWriter
            this.bufferedReader=new BufferedReader( new InputStreamReader(socket.getInputStream())); //sent from the client, reading a line
            this.clientUsername= bufferedReader.readLine();
            clientHandlers.add(this);
            broadcastMessage("SERVER: " + clientUsername +" has entered the chat!");
        } catch (IOException e) {
            closeEverything(socket, bufferedReader,bufferedWriter);
        }
    }

    @Override
    public void run() { //everything in this method is whats run on a seperate thread: listening to msgs ( a blocking operation) thats why we use threads
        String messageFromClient ;

        while(socket.isConnected()){
            try{ //listening to messages
                messageFromClient=bufferedReader.readLine();
                broadcastMessage(messageFromClient);
            } catch(IOException e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
                break;
            }
        }
    }
    public void broadcastMessage(String messageToSend){
        for (ClientHandler clientHandler: clientHandlers){ //looping through the list and sending the msg to every client connected
            try {
                if (!clientHandler.clientUsername.equals(clientUsername)){//everyone but the one who sent it
                    clientHandler.bufferedWriter.write(messageToSend);//
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                }
            } catch (IOException e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
            }
        }
    }

    public void removeClientHandler(){
        clientHandlers.remove(this);//user left the chat, we don't send him messages
        broadcastMessage("SERVER: " + clientUsername +"has left the chat!");

    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
        removeClientHandler();
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
}
