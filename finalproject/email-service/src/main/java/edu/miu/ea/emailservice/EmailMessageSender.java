package edu.miu.ea.emailservice;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.miu.ea.contracts.email.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class EmailMessageSender {
    @Autowired
    private KafkaTemplate<String, Email> kafkaTemplate;

    private Gson gson = new GsonBuilder().create();

    @Value("${email-service.message-queue.name}")
    private String topicName;

    public void send() {
        Email email = new Email();
        email.setTarget("hzhao@miu.edu");
        email.setTitle("Your reservation is going to expire");
        email.setBody("No content");
        kafkaTemplate.send(topicName, email);
    }
}
