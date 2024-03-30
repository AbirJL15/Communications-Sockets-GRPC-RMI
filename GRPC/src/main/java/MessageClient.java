import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.example.Messaging;
import org.example.MessagingServiceGrpc;

import java.util.Iterator;
import java.util.Scanner;

public class MessageClient {
    public static void main(String[] args) {
        // Create a channel to connect to the server
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();

        // Create a stub for making RPC calls
        MessagingServiceGrpc.MessagingServiceBlockingStub stub = MessagingServiceGrpc.newBlockingStub(channel);

        try {
            // Scanner for user input
            Scanner scanner = new Scanner(System.in);

            // Prompt user for sender ID
            System.out.print("Enter sender ID: ");
            String senderId = scanner.nextLine();

            // Prompt user for recipient ID
            System.out.print("Enter recipient ID: ");
            String recipientId = scanner.nextLine();

            // Prompt user for message text
            System.out.print("Enter message text: ");
            String messageText = scanner.nextLine();

            // Create a message to send
            Messaging.TextMessage message = Messaging.TextMessage.newBuilder()
                    .setId("1")
                    .setSenderId(senderId)
                    .setRecipientId(recipientId)
                    .setText(messageText)
                    .build();

            // Call the SendMessage RPC method
            Messaging.SendMessageResponse sendResponse = stub.sendMessage(message);

            // Print the response
            System.out.println("Message sent. Response: " + sendResponse);

            // Now, let's retrieve the received messages for the specified recipient
            System.out.print("Enter recipient ID to retrieve messages: ");
            recipientId = scanner.nextLine();

            // Create a request to retrieve received messages
            Messaging.GetReceivedMessagesRequest receivedMessagesRequest = Messaging.GetReceivedMessagesRequest.newBuilder()
                    .setUserId(recipientId)
                    .build();

            // Call the GetReceivedMessages RPC method
            Iterator<Messaging.TextMessage> receivedMessagesIterator = stub.getReceivedMessages(receivedMessagesRequest);

            // Print the received messages
            System.out.println("Received messages for user '" + recipientId + "':");
            while (receivedMessagesIterator.hasNext()) {
                Messaging.TextMessage receivedMessage = receivedMessagesIterator.next();
                System.out.println("Sender ID: " + receivedMessage.getSenderId());
                System.out.println("Text: " + receivedMessage.getText());
                System.out.println("--------------------");
            }

        } catch (Exception e) {
            // Handle any exceptions that occur during the RPC calls
            System.err.println("Error: " + e.getMessage());
        } finally {
            // Shutdown the channel
            channel.shutdown();
        }
    }
}