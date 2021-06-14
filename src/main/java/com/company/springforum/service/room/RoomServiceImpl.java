package com.company.springforum.service.room;

import com.company.springforum.model.Room;
import com.company.springforum.model.User;
import com.company.springforum.repository.RoomRepository;
import com.company.springforum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class RoomServiceImpl implements RoomService{

    public RoomRepository roomRepository;
    public UserRepository userRepository;

    @Autowired
    public RoomServiceImpl(UserRepository userRepository, RoomRepository roomRepository) {
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    public int checkIfExist(String userNum,int roomId) {
        User user = userRepository.findUserByUserNum(userNum);
        List<Room> rooms = user.getRooms();
        if (rooms.size() > 0) {
            for (Room room : rooms) {
                if (room.getRoomId() == roomId){
                    return -1;
                }
            }
        }
        return 1;
    }

    @Override
    public User addRoom(int roomNum, String num) {
        if (checkIfExist(num,roomNum) > 0) {
            User user = userRepository.findUserByUserNum(num);
            Room room = roomRepository.findByRoomId(roomNum);
            user.addRooms(room);
            return userRepository.save(user);
        }
        return null;
    }
}
