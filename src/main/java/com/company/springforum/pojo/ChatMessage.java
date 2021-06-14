package com.company.springforum.pojo;

public class ChatMessage {
    private String sender;
    private String content;
    private int roomNum;
    private String userId;

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ChatMessage() {

  }

    public String getSender() {
        return sender;
    }

    public void setUsername(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(int roomNum) {
        this.roomNum = roomNum;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "sender='" + sender + '\'' +
                ", content='" + content + '\'' +
                ", roomNum=" + roomNum +
                ", userId=" + userId +
                '}';
    }
}
