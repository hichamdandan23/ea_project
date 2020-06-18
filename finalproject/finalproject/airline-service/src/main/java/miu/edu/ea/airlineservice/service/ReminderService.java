package miu.edu.ea.airlineservice.service;

import edu.miu.ea.contracts.email.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ReminderService {
    @Autowired
    private KafkaTemplate<String, Email> kafkaTemplate;

    @Value("${email-service.message-queue.name}")
    private String topicName;

    private void send() {
        Email email = new Email();
        email.setTarget("hzhao@miu.edu");
        email.setTitle("Your reservation is going to expire");
        email.setBody("No content");
        kafkaTemplate.send(topicName, email);
    }

    public void sendFlightReminder() {
        send();
    }
}
