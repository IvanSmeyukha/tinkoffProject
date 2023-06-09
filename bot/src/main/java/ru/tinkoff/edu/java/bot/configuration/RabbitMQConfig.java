package ru.tinkoff.edu.java.bot.configuration;

import java.util.HashMap;
import java.util.Map;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.support.converter.ClassMapper;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.bot.dto.LinkUpdateRequest;
import ru.tinkoff.edu.java.bot.service.UpdatesSender;
import ru.tinkoff.edu.java.bot.service.listener.ScrapperQueueListener;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "true")
public class RabbitMQConfig {
    private final String queueName;
    private final String exchangeName;
    private final String routingKey;

    private static final String DLX_PREFIX = ".dlx";

    public RabbitMQConfig(ApplicationConfig applicationConfig) {
        this.queueName = applicationConfig.queueName();
        this.exchangeName = applicationConfig.exchangeName();
        this.routingKey = applicationConfig.routingkeyName();
    }

    @Bean
    public DirectExchange deadDirectExchange() {
        return new DirectExchange(
            exchangeName + DLX_PREFIX,
            false,
            false
        );
    }

    @Bean
    public Queue deadQueue() {
        return QueueBuilder
            .nonDurable(queueName + DLX_PREFIX)
            .build();
    }

    @Bean
    public Binding deadBinding() {
        return BindingBuilder.bind(deadQueue()).to(deadDirectExchange()).with(routingKey);
    }

    @Bean
    public ClassMapper classMapper() {
        Map<String, Class<?>> mappings = new HashMap<>();
        mappings.put("ru.tinkoff.edu.java.scrapper.dto.LinkUpdateRequest", LinkUpdateRequest.class);

        DefaultClassMapper classMapper = new DefaultClassMapper();
        classMapper.setTrustedPackages("ru.tinkoff.edu.java.scrapper.dto.*");
        classMapper.setIdClassMapping(mappings);
        return classMapper;
    }

    @Bean
    public MessageConverter jsonMessageConverter(ClassMapper classMapper) {
        Jackson2JsonMessageConverter jsonConverter = new Jackson2JsonMessageConverter();
        jsonConverter.setClassMapper(classMapper);
        return jsonConverter;
    }

    @Bean
    public ScrapperQueueListener scrapperQueueListener(UpdatesSender updatesSender) {
        return new ScrapperQueueListener(updatesSender);
    }
}
