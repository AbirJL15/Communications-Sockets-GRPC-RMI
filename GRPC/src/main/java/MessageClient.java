import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.example.Messaging.*;
import org.example.MessagingServiceGrpc;

public class MessageClient {
    public static void main(String[] args) {
        // Create a channel to connect to the server
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();

        // Create a stub for making RPC calls
        MessagingServiceGrpc.MessagingServiceBlockingStub stub = MessagingServiceGrpc.newBlockingStub(channel);

        try {
            // Create a message to send
            TextMessage message = TextMessage.newBuilder()
                    .setId("1")
                    .setSenderId("sender_id")
                    .setRecipientId("recipient_id")
                    .setText("Hello, world!")
                    .build();

            // Call the SendMessage RPC method
            SendMessageResponse response = stub.sendMessage(message);

            // Print the response
            System.out.println("Message sent. Response: " + response);
        } catch (Exception e) {
            // Handle any exceptions that occur during the RPC call
            System.err.println("Error sending message: " + e.getMessage());
        } finally {
            // Shutdown the channel
            channel.shutdown();
        }
    }
}
