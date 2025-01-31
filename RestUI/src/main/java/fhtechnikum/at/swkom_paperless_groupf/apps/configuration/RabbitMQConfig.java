package fhtechnikum.at.swkom_paperless_groupf.apps.configuration;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String OCR_QUEUE = "paperless.ocr_queue";
    public static final String RESULT_QUEUE = "paperless.result_queue";
    public static final String EXCHANGE_NAME = "paperless.exchange";
    public static final String ROUTING_KEY = "paperless_routing_key";

    @Bean
    public Queue ocrQueue() {
        return new Queue(OCR_QUEUE, true);
    }

    @Bean
    public Queue resultQueue() {
        return new Queue(RESULT_QUEUE, true);
    }
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding binding(Queue ocrQueue, TopicExchange exchange) {
        return BindingBuilder.bind(ocrQueue).to(exchange).with(ROUTING_KEY);
    }
}