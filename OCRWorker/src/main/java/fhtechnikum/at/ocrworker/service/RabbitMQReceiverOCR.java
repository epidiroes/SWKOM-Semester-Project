package fhtechnikum.at.ocrworker.service;

import fhtechnikum.at.ocrworker.configuration.RabbitMQConfigOCR;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;


@Service
@Slf4j
public class RabbitMQServiceOCR {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private final OCRService ocrService;

    public RabbitMQServiceOCR(OCRService ocrService) {
        this.ocrService = ocrService;
        log.info("RabbitMQServiceOCR started");
    }

    // Service1 sendet nur an Queue A (Service2 empfängt)
    public void sendMessageToRest(String title, String id) {
        String infoForRest = id + "_" + title;
        rabbitTemplate.convertAndSend(RabbitMQConfigOCR.RESULT_QUEUE, infoForRest);
        log.info("ocrText_fileName gesendet: {}", infoForRest);
    }

    // Service1 empfängt nur von Queue B (Service2 sendet)
    @RabbitListener(queues = RabbitMQConfigOCR.OCR_QUEUE)
    public void receive(String message) {
        log.info("RabbitMQListener received message: {}", message);
        try{
            ocrService.getFile(message);

        }catch (Exception e){
            log.error("RabbitMQListener error", e);
        }
    }
}
