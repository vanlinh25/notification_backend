package com.example.rabbit_mq.rabbitmq;

import com.example.rabbit_mq.fire_base.FirebaseMessagingService;
import com.example.rabbit_mq.fire_base.Note;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class Listener {

  private final FirebaseMessagingService service;
  private final ObjectMapper mapper;

  @RabbitListener(queues = "queueName")
  public void onMessage(Message message) {
    log.info("Consuming Message - " + new String(message.getBody()));
    try {
      Note note = mapper.readValue(new String(message.getBody()), Note.class);
      String response = service.sendNotification(note);
      log.info("Firebase response: " + response);
    } catch (Exception e) {
      log.warn(e.getMessage());
    }
  }
}
