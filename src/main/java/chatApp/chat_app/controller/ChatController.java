package chatApp.chat_app.controller;

import chatApp.chat_app.model.Message;
import chatApp.chat_app.model.MessageRequest;
import chatApp.chat_app.model.Room;
import chatApp.chat_app.repository.messageRepository;
import chatApp.chat_app.repository.roomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class ChatController
{
    @Autowired
    private messageRepository repo1;
    @Autowired
    private roomRepository repo2;

    private Map<String, Integer> roomActiveUsers = new ConcurrentHashMap<>();
    @MessageMapping("/sendMessage/{roomId}")
    @SendTo("/topic/{roomId}")
    public MessageRequest sendMessage(@DestinationVariable String roomId,MessageRequest request) {
        // 1. Room dhoondo
        Room room = repo2.findByRoomId(roomId);
        // 2. Naya Message object banao
        Message message = new Message();
        message.setContent(request.getContent());
        message.setSender(request.getSender());
        message.setRoom(room); // Isse message room se jud jayega

        message.setStatus("DELIVERED");

        // 3. TEXT or IMAGE
        message.setMessageType(request.getMessageType());
        // 4. Database mein save karo
        Message savedMessage=repo1.save(message); // Return hote hi @SendTo sabko bhej dega

        // 4. Request (DTO) ko update karo taaki Frontend ko pata chale
        request.setStatus(savedMessage.getStatus()); // "DELIVERED" bhej rahe hain
        request.setTimeStamp(savedMessage.getTimeStamp()); // DB wala asli time bhej rahe hai
        return request;
    }

    // 2.  typing checking ka liya
    @MessageMapping("/typing/{roomId}")
    @SendTo("/topic/typing/{roomId}")
    public Map<String, String> handleTyping(@DestinationVariable String roomId, Map<String, String> payload) {

        // payload mein hum bhejenge { "username": "abc", "isTyping": "true" }
        return payload;
    }

    //3.online count user
    @MessageMapping("/stats/{roomId}")
    @SendTo("/topic/stats/{roomId}")
    public Integer updateRoomStats(@DestinationVariable String roomId, String action) {
        int count = roomActiveUsers.getOrDefault(roomId, 0);
        if ("JOIN".equals(action))
            count++;
        else if ("LEAVE".equals(action))
            count = Math.max(0, count - 1);  //for safety taki count -1 ma na aa jay

        roomActiveUsers.put(roomId, count);
        return count;
    }
}
