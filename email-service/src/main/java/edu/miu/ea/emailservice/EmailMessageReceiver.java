package edu.miu.ea.emailservice;

import edu.miu.ea.contracts.email.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class EmailMessageReceiver {
    private static final Logger log = LoggerFactory.getLogger(EmailMessageReceiver.class);

    @KafkaListener(topics = {"${email-service.message-queue.name}"})
    public void listen(Email email) {
        log.info("Email send to: " +email.getTarget() + ", title=" + email.getTitle());
    }
}
