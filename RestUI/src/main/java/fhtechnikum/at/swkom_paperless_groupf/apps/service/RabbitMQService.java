package fhtechnikum.at.swkom_paperless_groupf.apps.service;

import fhtechnikum.at.swkom_paperless_groupf.apps.configuration.RabbitMQConfig;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;


@Service
@Slf4j
public class RabbitMQService {


    private final RabbitTemplate rabbitTemplate;

    public RabbitMQService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    // Service1 sendet nur an Queue A (Service2 empfängt)
    public void sendMessageToOCR(String title, String id) {
        String infoForOCR = id + "_" + title;
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, infoForOCR);
        log.info("ID_Title gesendet: {}", infoForOCR);
    }

    // Service1 empfängt nur von Queue B (Service2 sendet)
    @RabbitListener(queues = RabbitMQConfig.RESULT_QUEUE)
    public void receiveMessage(String message) {
        log.info("Nachricht empfangen: {}", message);
    }
}
