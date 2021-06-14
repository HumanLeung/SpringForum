package com.company.springforum.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="user")
public class User implements Serializable {
    private Long id;
    private String username;
    private String email;
    private String password;
    private String avatar;
    private String phoneNum;
    private String userNum;
    private Integer userType;
    private List<Comments> comments;
    private List<Articles> articles;
    private List<Following> follower;
    private List<Roles> roles;
    private List<Marks> marks;
    private List<Room> rooms;

    public User(String username, String email, String password, String userNum, Integer userType) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.userNum = userNum;
        this.userType = userType;
    }
    public User(String username,String userNum,String avatar,Integer userType){
        this.username = username;
        this.userNum = userNum;
        this.userNum = userNum;
        this.userType = userType;
        this.avatar = avatar;
    }


    public User() {

    }

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public void addRooms(Room room) {
        if (rooms == null) {
            rooms = new ArrayList<>();
        }
        rooms.add(room);
    }

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL})
    public List<Marks> getMarks() {
        return marks;
    }

    public void setMarks(List<Marks> marks) {
        this.marks = marks;
    }
    public void addMarks(Marks mark) {
        if (marks == null) {
             marks = new ArrayList<>();
        }
        marks.add(mark);
        mark.setUser(this);
    }

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL})
    public List<Comments> getComments() {
        return comments;
    }

    public void setComments(List<Comments> comments) {
        this.comments = comments;
    }

    public void addComments(Comments comment) {
        if (comments == null) {
              comments = new ArrayList<>();
        }
        comments.add(comment);
        comment.setUser(this);
    }

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "user",cascade = {CascadeType.ALL})
    public List<Articles> getArticles() {
        return articles;
    }

    public void setArticles(List<Articles> articles) {
        this.articles = articles;
    }

    public void addArticles(Articles article) {
        if (articles == null) {
            articles = new ArrayList<>();
        }
        articles.add(article);
        article.setUser(this);
    }

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "user_type")
    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    @Basic
    @Column(name = "password")
    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "avatar")
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Basic
    @Column(name = "phone_num")
    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
    @Basic
    @Column(name = "user_num")
    public String getUserNum() {
        return userNum;
    }

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @JoinTable(name="user_roles" ,joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id"))
    public List<Roles> getRoles() {
        return roles;
    }

    public void setRoles(List<Roles> roles) {
        this.roles = roles;
    }

    public void addRoles(Roles role) {
        if (roles == null) {
            roles = new ArrayList<>();
        }
        roles.add(role);
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(username, user.username) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(avatar, user.avatar) && Objects.equals(phoneNum, user.phoneNum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, password, avatar, phoneNum);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", avatar='" + avatar + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", userNum='" + userNum + '\'' +
                ", comments=" + comments +
                ", articles=" + articles +
                ", follower=" + follower +
                ", roles=" + roles +
                ", marks='" + marks +
                ", room='" + rooms +
                '}';
    }
}
