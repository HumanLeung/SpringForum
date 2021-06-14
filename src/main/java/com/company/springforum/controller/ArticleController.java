package com.company.springforum.controller;


import com.company.springforum.dto.ArticleDTO;
import com.company.springforum.dto.MarksDTO;
import com.company.springforum.dto.RankDTO;
import com.company.springforum.model.Articles;
import com.company.springforum.model.Marks;
import com.company.springforum.model.User;
import com.company.springforum.repository.MarksRepository;
import com.company.springforum.service.article.ArticleService;
import com.company.springforum.service.room.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin(origins = "*",  allowedHeaders = "*")
@RequestMapping("api/article")
public class ArticleController {

    public ArticleService articleService;
    public RoomService roomService;
    public MarksRepository marksRepository;

    @Autowired
    public ArticleController( ArticleService articleService, RoomService roomService, MarksRepository marksRepository) {
        this.articleService = articleService;
        this.roomService = roomService;
        this.marksRepository = marksRepository;
    }

    @PostMapping("/new")
    public ResponseEntity<Integer> addArticle(@RequestBody ArticleDTO articleDTO, @RequestParam("num") String num) {
      int artId = articleService.addArticle(articleDTO, num);
      if (artId > 0) {
          return new ResponseEntity<>(artId, HttpStatus.OK);
      } else {
          return new ResponseEntity<>(artId, HttpStatus.BAD_REQUEST);
      }
    }

    @RequestMapping(value = "/upload", method=RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file, @RequestParam("id") Integer id){
        System.out.println(file+"file");
        if (file == null) {
          articleService.upload(id);
        }else{
            articleService.upload(file, id);
        }
        return new ResponseEntity<>("ok",HttpStatus.OK);
    }

    @Cacheable(value = "papers", key = "'getAllArticles'+#pageNo")
    @GetMapping("/getArticles/{pageNo}")
    public List<RankDTO> getAllArticles(@PathVariable int pageNo) {
        return articleService.findArticlesWithSort(pageNo);
    }

    @GetMapping("/getArticles/paper/{aid}")
    public RankDTO getPaper(@PathVariable int aid) {
       return articleService.findByArticleId(aid);
    }

    @PostMapping("/mark/{aid}/{uid}")
    public ResponseEntity<String> mark(@PathVariable int aid,@PathVariable Long uid) {
        Marks mark = articleService.mark(aid, uid);
        if (mark != null) {
            return new ResponseEntity<>("mark", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("unmark", HttpStatus.OK);
        }
    }
    @GetMapping("/marks/{userNum}")
    public List<MarksDTO> check(@PathVariable String userNum) {
        List<MarksDTO> dtos = articleService.findAllMarks(userNum);
        System.out.println(dtos);
        return dtos;
    }

    @PutMapping("/updateCount/{aid}")
    public ResponseEntity<String> updateLookCount(@PathVariable int aid) {
       Articles articles = articleService.updateCount(aid);
       if (articles != null) {
           return new ResponseEntity<>("OK",HttpStatus.OK);
       }
       return new ResponseEntity<>("BAD",HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/room/{roomId}/{num}")
    public ResponseEntity<String> addRoom(@PathVariable int roomId, @PathVariable String num) {
        User user = roomService.addRoom(roomId, num);
        if (user != null) {
            return new ResponseEntity<>("join", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("joined", HttpStatus.OK);
        }
    }

    @GetMapping("/checkMark/{aid}/{uid}")
    public ResponseEntity<String> testMark(@PathVariable int aid, @PathVariable Long uid) {
        Integer result = marksRepository.checkIfMark(aid, uid);
        if (result != null) {
            return new ResponseEntity<>("OK", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("BAD", HttpStatus.OK);
        }
    }

    @GetMapping("/search/{con}")
    public List<Articles> search(@PathVariable String con) {
       return articleService.search(con);
    }

    @DeleteMapping("/delete/{aid}")
    public void delete(@PathVariable int aid) {
        articleService.delete(aid);
    }
}
