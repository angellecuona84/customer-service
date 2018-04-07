package com.everymundo.rabbitmq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * {@link ProducerOutput} class
 *
 * @author Angel Lecuona
 *
 */
@Component
public class ProducerOutput {

    @Autowired
    private RabbitStreamChannel rabbitStreamChannel;

    public void deleteConsumer(String id) {
        rabbitStreamChannel.orderOutput().send(MessageBuilder.withPayload(id).build());
    }
}