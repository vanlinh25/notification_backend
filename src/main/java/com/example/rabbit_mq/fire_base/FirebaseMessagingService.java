package com.example.rabbit_mq.fire_base;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FirebaseMessagingService {

  private final FirebaseMessaging firebaseMessaging;

  public FirebaseMessagingService(FirebaseMessaging firebaseMessaging) {
    this.firebaseMessaging = firebaseMessaging;
  }


  public String sendNotification(Note note) {

    Notification notification = Notification
        .builder()
        .setTitle(note.getSubject())
        .setBody(note.getContent())
        .build();

    Message message = Message
        .builder()
        .setToken(note.getToken())
        .setNotification(notification)
        .putData("content", note.getSubject())
        .putData("body", note.getContent())
        .build();

    try {
      return firebaseMessaging.send(message);
    } catch (FirebaseMessagingException e) {
      log.info(e.getMessage());
      return null;
    }
  }
}
