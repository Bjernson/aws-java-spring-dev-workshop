package hello;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.AmazonSQSException;
import com.amazonaws.services.sqs.model.CreateQueueResult;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageBatchRequest;
import com.amazonaws.services.sqs.model.SendMessageBatchRequestEntry;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import java.util.Date;
import java.util.List;

public class SendReceiveMaxMessages
{
    private static final String QUEUE_NAME = "testQueue";

    public static void main(String[] args)
    {
        final AmazonSQS sqs = AmazonSQSClientBuilder.standard().build();

        try {
            CreateQueueResult create_result = sqs.createQueue(QUEUE_NAME);
        } catch (AmazonSQSException e) {
            if (!e.getErrorCode().equals("QueueAlreadyExists")) {
                throw e;
            }
        }

        String queueUrl = sqs.getQueueUrl(QUEUE_NAME).getQueueUrl();

//        SendMessageRequest send_msg_request = new SendMessageRequest()
//                .withQueueUrl(queueUrl)
//                .withMessageBody("hello world")
//                .withDelaySeconds(5);
//        sqs.sendMessage(send_msg_request);
//
//
//        // Send multiple messages to the queue
//        SendMessageBatchRequest send_batch_request = new SendMessageBatchRequest()
//                .withQueueUrl(queueUrl)
//                .withEntries(
//                        new SendMessageBatchRequestEntry(
//                                "msg_1", "Hello from message 1"),
//                        new SendMessageBatchRequestEntry(
//                                "msg_2", "Hello from message 2")
//                                .withDelaySeconds(10));
//        sqs.sendMessageBatch(send_batch_request);


        // receive messages from the queue
        ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(queueUrl);
        receiveMessageRequest.setMaxNumberOfMessages(10);
        List<Message> messages = sqs.receiveMessage(receiveMessageRequest).getMessages();        

        System.out.println("Message size = " + messages.size());
        
        // delete messages from the queue
        for (Message m : messages) {
            System.out.println("Messages = " + m.getBody());
            sqs.deleteMessage(queueUrl, m.getReceiptHandle());
        }
    }
}
