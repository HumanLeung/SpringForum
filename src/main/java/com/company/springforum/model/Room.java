package com.company.springforum.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="room")
public class Room {
    private int roomId;
    private String roomNum;
    private Articles articles;

    public Room(String roomNum) {
        this.roomNum = roomNum;
    }

    public Room() {

    }

    @JsonIgnore
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,CascadeType.DETACH,
            CascadeType.REFRESH,CascadeType.REMOVE})
    @JoinColumn(name="article_id")
    public Articles getArticles() {
        return articles;
    }

    public void setArticles(Articles articles) {
        this.articles = articles;
    }

    @Id
    @Column(name = "room_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    @Basic
    @Column(name = "room_num")
    public String getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(String roomNum) {
        this.roomNum = roomNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return roomId == room.roomId && roomNum.equals(room.roomNum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomId, roomNum);
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomId=" + roomId +
                ", roomNum='" + roomNum + '\'' +
                ", articles=" + articles +
                '}';
    }
}
