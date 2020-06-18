package edu.miu.ea.emailservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableDiscoveryClient
@EnableAsync
public class EmailServiceApplication {
    private static final Logger log = LoggerFactory.getLogger(EmailServiceApplication.class);

    @Bean
    public StringJsonMessageConverter jsonConverter() {
        return new StringJsonMessageConverter();
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(EmailServiceApplication.class, args);
        //testSendEmail(context);
    }

    static private void testSendEmail(ConfigurableApplicationContext context) {

        EmailMessageSender sender = context.getBean(EmailMessageSender.class);

        //log.info("EmailServiceApplication started");
        for (;;) {
            try {
                Thread.sleep(1000);
                sender.send();
                //log.info("EmailMessageSender.send()");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
