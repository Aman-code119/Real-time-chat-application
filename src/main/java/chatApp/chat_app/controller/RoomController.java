package chatApp.chat_app.controller;
import chatApp.chat_app.model.Message;
import chatApp.chat_app.model.Room;
import chatApp.chat_app.repository.roomRepository;
import chatApp.chat_app.service.allservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/rooms")
@CrossOrigin("*")  // for browser security
public class RoomController
{
    @Autowired
    private allservice s1;
    @Autowired
    private roomRepository repo1;


    @PostMapping("/create")
    public ResponseEntity<?> createRoom(@RequestBody Map<String, String> body) {
        String actualRoomId = body.get("roomId"); // Ye JSON se sirf naam nikalega
        return s1.CreateRoom(actualRoomId);
    }
    @GetMapping("/joinroom/{roomid}")
    public ResponseEntity<?> joinroom(@PathVariable String roomid)
    {
        return s1.Joinroom(roomid);
    }


    @GetMapping("/{roomId}/message")
    public ResponseEntity<List<Message>> getMessages(
            @PathVariable String roomId,
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "20", required = false) int size) {

        return s1.getMessage(roomId, page, size);
    }

    @GetMapping("/{roomId}/exists")
    public ResponseEntity<Boolean> checkRoomExists(@PathVariable String roomId) {
        Room room = repo1.findByRoomId(roomId); // Ya jo bhi tumhara repository method hai
        if (room != null) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.ok(false);
        }
    }
}
