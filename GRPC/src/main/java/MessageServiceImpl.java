import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import org.example.Messaging.*;
import org.example.MessagingServiceGrpc;

public class MessageServiceImpl extends MessagingServiceGrpc.MessagingServiceImplBase {

    // Map to store sent messages, keyed by recipient ID
    private Map<String, List<TextMessage>> sentMessages = new HashMap<>();

    // Atomic integer for generating message IDs
    private AtomicInteger messageIdGenerator = new AtomicInteger(0);

    @Override
    public void sendMessage(TextMessage request, StreamObserver<SendMessageResponse> responseObserver) {
        // Generate a unique message ID
        String messageId = String.valueOf(messageIdGenerator.incrementAndGet());

        // Assuming the message sending is successful
        boolean success = true;

        // Store the message with recipient ID
        List<TextMessage> recipientMessages = sentMessages.computeIfAbsent(request.getRecipientId(), k -> new ArrayList<>());
        recipientMessages.add(request);

        // Build the response
        SendMessageResponse response = SendMessageResponse.newBuilder()
                .setSuccess(success)
                .setMessageText(request.getText())
                .build();

        // Send the response to the client
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getReceivedMessages(GetReceivedMessagesRequest request, StreamObserver<TextMessage> responseObserver) {
        // Get messages for the specified recipient ID
        List<TextMessage> messages = sentMessages.getOrDefault(request.getUserId(), new ArrayList<>());

        // Send each message through the stream
        for (TextMessage message : messages) {
            responseObserver.onNext(message);
        }
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
