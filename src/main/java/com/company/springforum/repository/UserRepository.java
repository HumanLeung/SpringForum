package com.company.springforum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.company.springforum.model.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "SELECT max(id) FROM User")
    public int max();

    List<User> findUsersByPhoneNum(String num);

    User findUserByEmail(String email);

    List<User> findUsersByEmail(String email);

    Integer deleteUserByEmail(String email);

    User findById(Long id);

    User findUserByUserNum(String num);

    User findUserByPhoneNum(String num);

    @Query(value = "select avatar, username from User where id = :id")
    List<String> findArtUserInfo(@Param("id") Long ID);




}
