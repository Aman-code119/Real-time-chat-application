package chatApp.chat_app.service;

import chatApp.chat_app.model.Message;
import chatApp.chat_app.model.Room;
import chatApp.chat_app.repository.roomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class allservice
{
    @Autowired
    roomRepository r1;
    public ResponseEntity<?> CreateRoom(String roomId)
    {
        if(r1.findByRoomId(roomId)!=null)
        {
            // room alredy here
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Room already exists!");
        }
           // create room
        Room newroom=new Room();
        newroom.setRoomId(roomId);
        Room saveroom=r1.save(newroom);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveroom);
    }


    public ResponseEntity<?> Joinroom(String roomid)
    {
        Room room1= r1.findByRoomId(roomid);
        if(room1==null)
        {
           return ResponseEntity.badRequest().body("Room not found");
        }
         return ResponseEntity.ok(room1);
    }

    public ResponseEntity<List<Message>> getMessage(String roomId, int page, int size)
    {
        // 1. Room dhundo
        Room room = r1.findByRoomId(roomId);
        if (room == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // 2. Messages ki list nikalo
        List<Message> messages = room.getMessage();

        // Professional tarike mein hum Repository mein Pageable use karte hain,
        // par abhi ke liye tum subList use kar sakte ho:
        int start = Math.min(page * size, messages.size());
        int end = Math.min((page + 1) * size, messages.size());

        List<Message> paginatedMessages = messages.subList(start, end);

        return ResponseEntity.ok(paginatedMessages);
    }
}
