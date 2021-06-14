package com.company.springforum.service.article;

import com.company.springforum.dto.ArticleDTO;
import com.company.springforum.dto.MarksDTO;
import com.company.springforum.dto.RankDTO;
import com.company.springforum.model.*;
import com.company.springforum.pojo.CommentPOJO;
import com.company.springforum.repository.*;
import com.company.springforum.tools.FileTool;
import com.company.springforum.tools.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class ArticleServiceImpl implements ArticleService{

    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final TagsRepository tagsRepository;
    private final RoomRepository roomRepository;
    private final MarksRepository marksRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public ArticleServiceImpl (UserRepository userRepository, ArticleRepository articleRepository,
                               TagsRepository tagsRepository, RoomRepository roomRepository,
                               MarksRepository marksRepository, CommentRepository commentRepository) {
       this.userRepository = userRepository;
       this.articleRepository = articleRepository;
       this.tagsRepository = tagsRepository;
       this.roomRepository = roomRepository;
       this.marksRepository = marksRepository;
       this.commentRepository = commentRepository;
    }
    @Transactional
    @Override
    public int addArticle(ArticleDTO articleDTO, String num) {
        System.out.println(articleDTO);
        System.out.println(num);
        User user = userRepository.findUserByUserNum(num);
        String path = num;
        Articles articles = new Articles(articleDTO.getArticleTitle(),articleDTO.getArticleContent(),
                path, 0);
        user.addArticles(articles);
        Articles newArticle = articleRepository.save(articles);
        for (Tags tags :articleDTO.getTags()) {
             Tags tags1 = new Tags(tags.getTagColor(),tags.getTagName());
             newArticle.addTags(tags1);
        }
        Tags result = null;
        for (Tags tags :newArticle.getTags()){
          result  = tagsRepository.save(tags);
        }

        Room room  = new Room(IdGenerator.generateRoom(newArticle.getArticleId()));
        room.setArticles(newArticle);
        roomRepository.save(room);
        return newArticle.getArticleId();
    }

    @Override
    @Transactional
    public void upload(MultipartFile file, Integer id) {
        Articles articles = articleRepository.findByArticleId(id);
        String path = articles.getArticleBgi();
        String bgiPath = "src/main/resources/static/myforum/"+path+"/article/bgi"+"/"+id;
        FileTool.DirectoryTool(bgiPath);
        FileTool.createFile(bgiPath,file);
        String bgiPaths = "http://localhost:8080/myforum/"+ path+"/article/bgi"+"/"+id+"/"+file.getOriginalFilename();
        articles.setArticleBgi(bgiPaths);
        articleRepository.save(articles);
    }

    @Override
    @Transactional
    public void upload(Integer id) {
        Articles articles = articleRepository.findByArticleId(id);
        articles.setArticleBgi("http://localhost:8080/myforum/default/article/bgi/img.png");
        articleRepository.save(articles);

    }
    @Transactional
    @Override
    public void delete(int aid) {
       Articles article = articleRepository.findByArticleId(aid);
          for (Comments comment : article.getComments()) {
              if (comment != null) {
                  comment.setUser(null);
                  comment.setArticles(null);
                 Comments deleteCom = commentRepository.save(comment);
                 commentRepository.delete(deleteCom);
              }
          }
          for (Marks mark : article.getMarks()) {
              if (mark != null) {
                  mark.setUser(null);
                  mark.setArticles(null);
                Marks deleteMark = marksRepository.save(mark);
                marksRepository.delete(deleteMark);
              }
          }
/*             Room room = article.getRoom();
             room.setArticles(null);
             Room deleteRoom = roomRepository.save(room);
             roomRepository.delete(deleteRoom);*/
             article.setUser(null);
             Articles deleteArticle = articleRepository.save(article);
             articleRepository.delete(deleteArticle);
    }

    @Override
    @Transactional
    public List<RankDTO> findArticlesWithSort(int pageNo) {
        int pageSize = 5;
        Pageable pageable = PageRequest.of(pageNo,pageSize, Sort.Direction.DESC, "lookCount");
        List<Articles> articles = articleRepository.findAll(pageable).toList();
        System.out.println(articles);
        List<RankDTO> dtos = new ArrayList<>();
        for (Articles articles1 : articles) {
            RankDTO rankDTO = new RankDTO();
            rankDTO.setArticleId(articles1.getArticleId());
            rankDTO.setArticleBgi(articles1.getArticleBgi());
            rankDTO.setArticleDate(articles1.getArticleDate());
            rankDTO.setArticleTitle(articles1.getArticleTitle());
            rankDTO.setArticleContent(articles1.getArticleContent());
            rankDTO.setRoom(articles1.getRoom());
            rankDTO.setLookCount(articles1.getLookCount());
            rankDTO.setUsername(articles1.getUser().getUsername());
            rankDTO.setTags(articles1.getTags());
            rankDTO.setUserId(articles1.getUser().getId());
            rankDTO.setUserNum(articles1.getUser().getUserNum());
            rankDTO.setAvatar(articles1.getUser().getAvatar());
            dtos.add(rankDTO);
        }
        return dtos;
    }

    @Override
    @Transactional
   public RankDTO findByArticleId(int id) {
            Articles articles = articleRepository.findByArticleId(id);
            System.out.println(articles);
            RankDTO dto = new RankDTO();
          List<CommentPOJO> pojos = new ArrayList<>();
        for (Comments comment: articles.getComments()) {
            CommentPOJO  commentPOJO = new CommentPOJO();
            commentPOJO.setCommentId(comment.getCommentId());
            commentPOJO.setContent(comment.getContent());
            commentPOJO.setUserNum(comment.getUser().getUserNum());
            commentPOJO.setUsername(comment.getUser().getUsername());
            commentPOJO.setAvatar(comment.getUser().getAvatar());
            pojos.add(commentPOJO);
           }
           dto.setAvatar(articles.getUser().getAvatar());
           dto.setUsername(articles.getUser().getUsername());
           dto.setUserNum(articles.getUser().getUserNum());
           dto.setArticleTitle(articles.getArticleTitle());
           dto.setArticleBgi(articles.getArticleBgi());
           dto.setArticleId(articles.getArticleId());
           dto.setRoom(articles.getRoom());
           dto.setComments(pojos);
           dto.setArticleContent(articles.getArticleContent());
           return dto;
   }


    @Transactional
    @Override
    public Marks mark(int aid, Long uid) {
      Integer result = marksRepository.checkIfMark(aid, uid);
      if (result == null){
          User user = userRepository.findById(uid);
          Marks mark = new Marks();
          user.addMarks(mark);
          Marks newMark = marksRepository.save(mark);
          Articles articles = articleRepository.findByArticleId(aid);
          articles.addMarks(newMark);
          return marksRepository.save(newMark);
      } else {
          Integer mid = marksRepository.checkIfMark(aid, uid);
          Marks marks = marksRepository.findByMarksId(mid);
          marks.setUser(null);
          marks.setArticles(null);
          Marks deleteMark = marksRepository.save(marks);
          marksRepository.deleteById(deleteMark.getMarksId());
          System.out.println(marks);
      }
          return null;
    }

    @Override
    @Transactional
    public List<MarksDTO> findAllMarks(String num) {
        System.out.println("markUserNum"+num);
       User user = userRepository.findUserByUserNum(num);
        System.out.println(user+"userNummark");
//        System.out.println(user.getMarks());
        List<MarksDTO> marks = new ArrayList<>();
      if (user.getMarks() != null) {
          for (Marks mark: user.getMarks()) {
              MarksDTO marksDTO = new MarksDTO();
              marksDTO.setUsername(mark.getArticles().getUser().getUsername());
              marksDTO.setAvatar(mark.getArticles().getUser().getAvatar());
              marksDTO.setArticleBgi(mark.getArticles().getArticleBgi());
              marksDTO.setArticleId(mark.getArticles().getArticleId());
              marksDTO.setArticleContent(mark.getArticles().getArticleContent());
              marksDTO.setArticleTitle(mark.getArticles().getArticleTitle());
              marksDTO.setLookCount(mark.getArticles().getLookCount());
              marksDTO.setMarkId(mark.getMarksId());
              marksDTO.setUserId(mark.getUser().getId());
              marksDTO.setTags(mark.getArticles().getTags());
              marksDTO.setCommentsCount(mark.getArticles().getComments().size());
              marks.add(marksDTO);

          }
          return marks;
      }
        return null;
    }
    @Override
    @Transactional
    public Articles updateCount(int aid) {
        Articles article = articleRepository.findByArticleId(aid);
        article.setLookCount(article.getLookCount()+1);
        return articleRepository.save(article);
    }

    @Transactional
    @Override
    public List<Articles> search(String con) {

        if (con.length() < 2) {
            System.out.println("sd");
        }
        List<Articles> articlesList;
        List<Articles> articlesContent;

       List<Articles> articles = articleRepository.searchText(con);
       if (articles.size() == 0 ){
           articles = articleRepository.searchTextByLikeTitle(con);
           articlesContent = articleRepository.searchTextByLikeContent(con);
           Set<Articles> set = new LinkedHashSet<>(articles);
           set.addAll(articlesContent);
           articlesList = new ArrayList<>(set);
           System.out.println(articlesList);

           return articlesList;
       }
        return articles;
    }
}
