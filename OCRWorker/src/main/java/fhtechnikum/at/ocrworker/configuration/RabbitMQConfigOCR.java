package fhtechnikum.at.ocrworker.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String OCR_QUEUE = "document_processing_queue";
    public static final String RESULT_QUEUE = "document_result_queue";

    @Bean
    public Queue ocrQueue() {
        return new Queue(OCR_QUEUE);
    }

    @Bean
    public Queue resultQueue() {
        return new Queue(RESULT_QUEUE);
    }
}