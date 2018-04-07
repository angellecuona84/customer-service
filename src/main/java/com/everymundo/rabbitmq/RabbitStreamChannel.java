package com.everymundo.rabbitmq;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
/**
 * {@link RabbitStreamChannel} interface
 *
 * @author Angel Lecuona
 *
 */
public interface RabbitStreamChannel {

    public static final String ORDER_OUTPUT_CHANNEL = "customerChannel";

    @Output(RabbitStreamChannel.ORDER_OUTPUT_CHANNEL)
    MessageChannel orderOutput();

}
