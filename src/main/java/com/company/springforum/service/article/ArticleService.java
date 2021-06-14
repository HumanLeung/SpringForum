package com.company.springforum.service.article;


import com.company.springforum.dto.ArticleDTO;
import com.company.springforum.dto.MarksDTO;
import com.company.springforum.dto.RankDTO;
import com.company.springforum.model.Articles;
import com.company.springforum.model.Marks;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ArticleService {
    int addArticle(ArticleDTO articleDTO, String Num);

    void upload(MultipartFile file, Integer num);

    void upload(Integer num);

    public void delete(int id);

    List<RankDTO> findArticlesWithSort(int pageNo);

    public RankDTO findByArticleId(int id);

    public Marks mark(int aid, Long uid);

    public List<MarksDTO> findAllMarks(String num);

    public Articles updateCount(int aid);

    public List<Articles> search(String con);

}
