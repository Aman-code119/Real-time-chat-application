package chatApp.chat_app.repository;

import chatApp.chat_app.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface messageRepository extends JpaRepository<Message,Long>
{

}
