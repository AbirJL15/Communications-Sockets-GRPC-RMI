import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import org.example.Messaging;
import org.example.MessagingServiceGrpc;

public class MessageServiceImpl extends MessagingServiceGrpc.MessagingServiceImplBase {
    @Override
    public void sendMessage(Messaging.TextMessage request, StreamObserver<Messaging.SendMessageResponse> responseObserver) {
        // Implement message sending logic here
        String messageId = "1234"; // Generate a message ID
        boolean success = true; // Assuming the message sending is successful

        // Extract the message text from the request
        String messageText = request.getText();

        // Build the response with message ID, success status, and message text
        Messaging.SendMessageResponse response = Messaging.SendMessageResponse.newBuilder()
                .setMessageId(messageId)
                .setSuccess(success)
                .setMessageText(messageText) // Set the message text
                .build();

        // Send the response to the client
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getReceivedMessages(Messaging.GetReceivedMessagesRequest request, StreamObserver<Messaging.TextMessage> responseObserver) {
        // Implement logic to retrieve received messages for the given user ID
        // In this example, we'll return some dummy messages
        Messaging.TextMessage message1 = Messaging.TextMessage.newBuilder()
                .setId("1")
                .setSenderId("sender_id")
                .setRecipientId(request.getUserId())
                .setText("Hello from sender")
                .build();
        Messaging.TextMessage message2 = Messaging.TextMessage.newBuilder()
                .setId("2")
                .setSenderId("another_sender_id")
                .setRecipientId(request.getUserId())
                .setText("Another message")
                .build();
        // Send each message through the stream
        responseObserver.onNext(message1);
        responseObserver.onNext(message2);
        responseObserver.onCompleted();
    }



    public static void main(String[] args) throws Exception {
        // Start the gRPC server
        Server server = ServerBuilder.forPort(9090)
                .addService(new MessageServiceImpl())
                .build();

        server.start();

        System.out.println("Server started");

        // Block until the server is terminated
        server.awaitTermination();
    }
}
