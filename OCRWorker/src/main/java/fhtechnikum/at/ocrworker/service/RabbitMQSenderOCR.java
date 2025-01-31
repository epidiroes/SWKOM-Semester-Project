package fhtechnikum.at.ocrworker.service;

import fhtechnikum.at.ocrworker.configuration.RabbitMQConfigOCR;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RabbitMQSenderOCR {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public RabbitMQSenderOCR() {
        log.info("RabbitMQServiceOCR started");
    }

    public void sendMessageToRest(String title, String id) {
        String infoForRest = id + "_" + title;
        rabbitTemplate.convertAndSend(RabbitMQConfigOCR.RESULT_QUEUE, infoForRest);
        log.info("ocrText_fileName gesendet: {}", infoForRest);
    }
}
