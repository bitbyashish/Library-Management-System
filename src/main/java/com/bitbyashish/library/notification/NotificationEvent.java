package com.bitbyashish.library.notification;

import lombok.Data;

@Data
public class NotificationEvent {
    private Long userId;
    private String message;
}
