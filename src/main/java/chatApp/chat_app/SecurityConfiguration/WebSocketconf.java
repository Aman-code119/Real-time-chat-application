package chatApp.chat_app.SecurityConfiguration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketconf implements WebSocketMessageBrokerConfigurer
{

        @Override
        public void registerStompEndpoints(StompEndpointRegistry registry) {
            // 1. Connection Point: Client isi URL se handshake karega
            registry.addEndpoint("/chat")
                    .setAllowedOrigins("null")
                    .setAllowedOriginPatterns("*") // Frontend ka address (React/Next.js)
                    .withSockJS(); // Purane browsers ke support ke liye

//            registry.addEndpoint("/chat")
//                    .setAllowedOriginPatterns("*"); //postman ka liya
        }

        @Override
        public void configureMessageBroker(MessageBrokerRegistry registry) {
            // 2. Message Rasta (Broker)

            // Jo messages server sabko bhejega (Broadcast), unka prefix "/topic" hoga
            registry.enableSimpleBroker("/topic");

            // Jo messages client server ko bhejega (Controller ke liye), unka prefix "/app" hoga
            registry.setApplicationDestinationPrefixes("/app");
        }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        registration.setMessageSizeLimit(10 * 1024 * 1024); // 10MB limit
        registration.setSendBufferSizeLimit(10 * 1024 * 1024); // 10MB buffer
        registration.setSendTimeLimit(20000); // 20 seconds
    }
}
