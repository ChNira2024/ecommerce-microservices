package com.niranjana.ecommerce.notification.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.niranjana.ecommerce.notification.service.NotificationService;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired private NotificationService notificationService;

    @PostMapping("/send")
    public ResponseEntity<?> send(@RequestParam Long userId,
                                 @RequestParam String message) {

        notificationService.sendNotification(userId, message);
        return ResponseEntity.ok("Notification sent");
    }
}