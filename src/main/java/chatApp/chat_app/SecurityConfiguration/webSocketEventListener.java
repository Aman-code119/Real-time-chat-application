package chatApp.chat_app.SecurityConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class webSocketEventListener
{
        @Autowired
        private SimpMessagingTemplate messagingTemplate;

        @EventListener
        public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
            // Jab bhi koi connection tootega, ye apne aap trigger hoga
            // Yahan tum apna roomActiveUsers wala Map update kar sakte ho
            System.out.println("User Disconnected!");
        }
}
