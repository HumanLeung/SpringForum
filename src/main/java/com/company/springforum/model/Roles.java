package com.company.springforum.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="roles")
public class Roles {
    private Long id;
    private String roleName;

    private List<User> user;

    public Roles(String roleName) {
        this.roleName = roleName;
    }

    public Roles() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    public Long getId() {
        return id;
    }

    @Basic
    @JsonProperty("role_name")
    @Column(name = "role_name")
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH, CascadeType.REMOVE})
    @JoinTable(name="user_roles", joinColumns = @JoinColumn(name="role_id")
            ,inverseJoinColumns = @JoinColumn(name="user_id"))
    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Roles roles = (Roles) o;
        return id.equals(roles.id) && Objects.equals(roleName, roles.roleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleName, id);
    }

    @Override
    public String toString() {
        return "Roles{" +
                "roleId=" + id +
                ", roleName='" + roleName + '\'' +
                '}';
    }
}
