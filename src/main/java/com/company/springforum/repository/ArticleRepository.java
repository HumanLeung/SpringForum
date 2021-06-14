package com.company.springforum.repository;


import com.company.springforum.model.Articles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Articles, Integer> {

    @Query(value="select article_bgi from Articles where article_id = :id", nativeQuery = true)
    String findBgi(@Param("id") Integer id);

    Articles findByArticleId(Integer id);

    List<Articles> findAllByArticleIdIsNotNull();

    void deleteByArticleId(int id);

    Articles findByArticleId(int id);

    @Query(value = "SELECT * FROM Articles  WHERE MATCH(article_title, article_content) AGAINST( :con )", nativeQuery = true)
    List<Articles> searchText(@Param("con") String con);

    @Query(value = "SELECT * FROM Articles  WHERE article_title like '%' :con '%' ", nativeQuery = true)
    List<Articles> searchTextByLikeTitle(@Param("con") String con);

    @Query(value = "SELECT * FROM Articles  WHERE article_content like '%' :con '%' ", nativeQuery = true)
    List<Articles> searchTextByLikeContent(@Param("con") String con);


}