package chatApp.chat_app.SecurityConfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // 2. Ye annotation bahut zaroori hai
public class securityconf {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // 3. CSRF band kiya taaki POST request chale
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/rooms/**,/chat/**,/chat-bot/**").permitAll() // 4. Rooms wali APIs public kar di
                        .anyRequest().permitAll()
                );

        return http.build();
    }
}