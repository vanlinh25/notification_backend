package com.example.rabbit_mq.controller;

import com.example.rabbit_mq.fire_base.FirebaseMessagingService;
import com.example.rabbit_mq.fire_base.Note;
import com.google.firebase.messaging.FirebaseMessagingException;
import java.io.IOException;
import java.util.Base64;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class Controller {

  private final RabbitTemplate rabbitTemplate;

  private final FirebaseMessagingService service;

  @PostMapping("/send")
  public ResponseEntity<String> sendMessage(@RequestHeader("Content-Type") String contentType, @RequestParam("file") MultipartFile file)
      throws IOException {

    byte[] encoded = Base64.getEncoder().encode(file.getBytes());// Outputs "SGVsbG8="

    Message message = MessageBuilder
        .withBody(encoded)
        .setHeader("ContentType", contentType)
        .build();

    rabbitTemplate.send("EXCHANGE_NAME", "ROUTING_KEY", message);

    return new ResponseEntity<>(new String(encoded), HttpStatus.ACCEPTED);
  }

  @PostMapping("/send-notification")
  public String sendNotification(@RequestBody Note note) {
    return service.sendNotification(note);
  }
}
