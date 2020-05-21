package com.wjjzst.queue.rabbitmq.rabbitmqspringcloud.consumer;

import com.rabbitmq.client.Channel;
import com.wjjzst.queue.rabbitmq.rabbitmqspringcloud.entity.Order;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RabbitReceiver {
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queue-1",
                    durable = "true"),
            exchange = @Exchange(value = "exchange-1",
                    durable = "true",
                    type = "topic",
                    ignoreDeclarationExceptions = "true"),
            key = "springboot.*")
    )
    @RabbitHandler
    public void onMessage(Message message, Channel channel) throws Exception {
        System.err.println("--------------------------------------------------");
        System.err.println("消费端: " + message.getPayload());
        Long deliveryTag = (Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
        // 手工ack
        channel.basicAck(deliveryTag, false); //第二个参数表示不批量的ack 而是一个一个ack
    }
    // 完全注解方式定义死信队列 设置时间还有点问题
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${spring.rabbitmq.listener.order.queue.name}",
                    durable = "${spring.rabbitmq.listener.order.queue.durable}",
                    exclusive = "${spring.rabbitmq.listener.order.queue.exclusive}",
                    autoDelete = "${spring.rabbitmq.listener.order.queue.autoDelete}",
                    arguments = {
                            @Argument(name = "x-dead-letter-exchange",value = "${spring.rabbitmq.listener.order.queue.arguments.x-dead-letter-exchange}"),
                            // @Argument(name = "x-message-ttl",value = "${spring.rabbitmq.listener.order.queue.arguments.x-message-ttl}"),
                            @Argument(name = "x-dead-letter-routing-key",value = "${spring.rabbitmq.listener.order.queue.arguments.x-dead-letter-routing-key}")
                    }
            ),
            exchange = @Exchange(value = "${spring.rabbitmq.listener.order.exchange.name}",
                    durable = "${spring.rabbitmq.listener.order.exchange.durable}",
                    type = "${spring.rabbitmq.listener.order.exchange.type}",
                    ignoreDeclarationExceptions = "${spring.rabbitmq.listener.order.exchange.ignoreDeclarationExceptions}"),
            key = "${spring.rabbitmq.listener.order.key}")
    )

    @RabbitHandler
    public void onOrderMessage(@Payload Order order, Channel channel, @Headers Map<String, Object> headers) throws Exception {
        System.err.println("--------------------------------------------------");
        System.err.println("消费端order: " + order.getId());
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        // 手工ack
        channel.basicAck(deliveryTag, false); //第二个参数表示不批量的ack 而是一个一个ack
    }

}
