package com.company.springforum.security.jwt;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user", schema = "myforum")
public class UserSecurity {
    private Long userId;
    private String email;
    private String password;
    private String userNum;
    private List<RolesSecurity> roles;


    @Id
    @Column(name = "user_id")
    @GeneratedValue
    public Long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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
    @Column(name = "user_num")
    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @ManyToMany(fetch = FetchType.EAGER ,cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH, CascadeType.REMOVE})
    @JoinTable(name="user_roles" ,joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id"))
    public List<RolesSecurity> getRoles() {
        return roles;
    }

    public void setRoles(List<RolesSecurity> roles) {
        this.roles = roles;
    }

//    @Basic
//    @Column(name = "avatar")
//    public String getAvatar() {
//        return avatar;
//    }
//
//    public void setAvatar(String avatar) {
//        this.avatar = avatar;
//    }

//    @Basic
//    @Column(name = "phone_num")
//    public String getPhoneNum() {
//        return phoneNum;
//    }
//
//    public void setPhoneNum(String phoneNum) {
//        this.phoneNum = phoneNum;
//    }

//    @Basic
//    @Column(name = "user_num")
//    public String getUserNum() {
//        return userNum;
//    }

//    public void setUserNum(String userNum) {
//        this.userNum = userNum;
//    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        UserSecurity that = (UserSecurity) o;
//        return userId == that.userId && Objects.equals(username, that.username) && Objects.equals(email, that.email) && Objects.equals(password, that.password) && Objects.equals(avatar, that.avatar) && Objects.equals(phoneNum, that.phoneNum) && Objects.equals(userNum, that.userNum);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(userId, username, email, password, avatar, phoneNum, userNum);
//    }

    @Override
    public String toString() {
        return "UserSecurity{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", password='" + password;
    }
}
