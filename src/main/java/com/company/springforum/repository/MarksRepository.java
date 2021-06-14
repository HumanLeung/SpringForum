package com.company.springforum.repository;

import com.company.springforum.model.Marks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MarksRepository extends JpaRepository<Marks, Integer> {

    @Query(value="select marks_id From Marks where article_id = :aid and user_id = :uid", nativeQuery = true)
    public Integer checkIfMark(@Param("aid")int aid, @Param("uid") Long uid);

    public Marks findByMarksId(Integer id);


}
