package com.niranjana.ecommerce.notification.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.niranjana.ecommerce.notification.service.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService{

    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    public void sendNotification(Long userId, String message) {
        log.info("Sending notification to userId={}, message={}", userId, message);

        // Simulate email/SMS
        System.out.println("📩 Notification sent to user " + userId + ": " + message);
    }
}