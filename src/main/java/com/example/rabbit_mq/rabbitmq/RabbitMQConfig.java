package com.example.rabbit_mq.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

  @Bean
  Queue queue() {
    return new Queue("queueName", false);
  }

  @Bean
  TopicExchange exchange() {
    return new TopicExchange("EXCHANGE_NAME");
  }

  @Bean
  Binding binding(Queue queue, TopicExchange exchange) {
    return BindingBuilder.bind(queue).to(exchange).with("ROUTING_KEY");
  }

  //create MessageListenerContainer using default connection factory
  @Bean
  MessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory) {
    SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
    simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
    simpleMessageListenerContainer.setQueues(queue());
    return simpleMessageListenerContainer;
  }

//  @Bean
//  public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
//    final var rabbitTemplate = new RabbitTemplate(connectionFactory);
//    rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
//    return rabbitTemplate;
//  }
//
//  @Bean
//  public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
//    return new Jackson2JsonMessageConverter();
//  }
}
