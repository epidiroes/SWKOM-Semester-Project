package fhtechnikum.at.swkom_paperless_groupf.apps.rabbitMQ;

import fhtechnikum.at.swkom_paperless_groupf.apps.configuration.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RabbitMQReceiver {

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void receiveMessage(String message) {
        log.info("Nachricht empfangen: {}", message);
    }
}