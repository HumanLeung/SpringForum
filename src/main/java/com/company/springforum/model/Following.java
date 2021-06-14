package com.company.springforum.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "following")
public class Following {
    private Long id;
    private Integer followerId;

    @Id
    @GeneratedValue
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "follower_id")
    public Integer getFollowerId() {
        return followerId;
    }

    public void setFollowerId(Integer followerId) {
        this.followerId = followerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Following following = (Following) o;
        return Objects.equals(id, following.id) && Objects.equals(followerId, following.followerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, followerId);
    }
}
