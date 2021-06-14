package com.company.springforum.security.jwt;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "roles", schema = "myforum")
public class RolesSecurity {
    private String roleName;
    private Long roleId;
    private List<UserSecurity> user;

    @Basic
    @Column(name = "role_name")
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Id
    @Column(name = "role_id")
    @GeneratedValue
    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER,cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH, CascadeType.REMOVE})
    @JoinTable(name="user_roles", joinColumns = @JoinColumn(name="role_id")
            ,inverseJoinColumns = @JoinColumn(name="user_id"))
    public List<UserSecurity> getUser() {
        return user;
    }

    public void setUser(List<UserSecurity> user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RolesSecurity that = (RolesSecurity) o;
        return roleId == that.roleId && Objects.equals(roleName, that.roleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleName, roleId);
    }

    @Override
    public String toString() {
        return "RolesSecurity{" +
                "roleName='" + roleName + '\'' +
                ", roleId=" + roleId +
                '}';
    }
}
