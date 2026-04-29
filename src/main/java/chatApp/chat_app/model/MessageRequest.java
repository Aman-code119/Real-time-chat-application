package chatApp.chat_app.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter@AllArgsConstructor
@NoArgsConstructor
public class MessageRequest
{
    private String content;
    private String sender;
    private String status;
    private LocalDateTime timeStamp;
    private String messageType;
}
