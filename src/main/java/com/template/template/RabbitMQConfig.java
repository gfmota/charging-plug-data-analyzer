package com.template.template;

import jakarta.annotation.PostConstruct;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class RabbitMQConfig {

    @Value("${number-of-consumers}")
    private int numberOfConsumers;

    @Autowired
    private SimpleRabbitListenerContainerFactory containerFactory;

    @PostConstruct
    public void registerListeners() {
        for (int i = 1; i <= numberOfConsumers; i++) {
            createMessageListenerContainer("charging-plug-station-daily-report-queue-" + i);
            createMessageListenerContainer("charging-plug-station-hourly-report-queue-" + i);
            createMessageListenerContainer("charging-plug-station-current-status-queue-" + i);
        }
    }

    private void createMessageListenerContainer(final String queueName) {
        final SimpleMessageListenerContainer container = containerFactory.createListenerContainer();
        container.setQueueNames(queueName);
        final var listener = new PrinterListener(queueName);
        container.setMessageListener(listener);
        container.start();
    }
}

