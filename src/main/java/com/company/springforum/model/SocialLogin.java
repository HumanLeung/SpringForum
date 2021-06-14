package com.company.springforum.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="sociallogin")
public class SocialLogin {
    private Long id;
    private String socialId;
    private User user;

    public SocialLogin(String socialId) {
        this.socialId = socialId;
    }

    public SocialLogin() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "social_id")
    public String getSocialId() {
        return socialId;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setSocialId(String socialId) {
        this.socialId = socialId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SocialLogin that = (SocialLogin) o;
        return Objects.equals(id, that.id) && Objects.equals(socialId, that.socialId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, socialId);
    }
}
