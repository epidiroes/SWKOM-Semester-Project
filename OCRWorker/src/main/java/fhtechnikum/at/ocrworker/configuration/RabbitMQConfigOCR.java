package fhtechnikum.at.ocrworker.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfigOCR {

    public static final String OCR_QUEUE = "paperless.ocr_queue";
    public static final String RESULT_QUEUE = "paperless.result_queue";

    @Bean
    public Queue ocrQueue() {
        return new Queue(OCR_QUEUE);
    }

    @Bean
    public Queue resultQueue() {
        return new Queue(RESULT_QUEUE);
    }



}