package com.template.template;

import lombok.AllArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

@AllArgsConstructor
public class PrinterListener implements MessageListener {
    private final String queueName;

    @Override
    public void onMessage(final Message message) {
        System.out.println("Message received in queue " + queueName + ": " + new String(message.getBody()));
    }
}
