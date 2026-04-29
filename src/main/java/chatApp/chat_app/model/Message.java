package chatApp.chat_app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Message
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String sender;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String content;
    private String status = "PENDING"; // Default status
    private String messageType;
    @CreationTimestamp // Ye apne aap current time save kar lega
    private LocalDateTime timeStamp;


    public Message(String sender, String content) {
        this.sender = sender;
        this.content = content;
    }
    @ManyToOne
    @JoinColumn(name = "room_id") // Database mein ek 'room_id' column ban jayega
    private Room room;
}
