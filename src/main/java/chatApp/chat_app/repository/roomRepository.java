package chatApp.chat_app.repository;

import chatApp.chat_app.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface roomRepository extends JpaRepository<Room ,String> {
    Room findByRoomId(String roomId);
}
