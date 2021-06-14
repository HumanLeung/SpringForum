package com.company.springforum.repository;

import com.company.springforum.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface RoomRepository extends JpaRepository<Room,Integer> {

    Room findByRoomId(int id);

    @Query(value = "select room_id, room_num, article_id from Room where user_id = 17", nativeQuery = true)
    public Room findSome();
}
