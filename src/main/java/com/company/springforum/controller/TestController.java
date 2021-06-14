package com.company.springforum.controller;

import com.company.springforum.dto.ArticleDTO;
import com.company.springforum.dto.UserDto;
import com.company.springforum.model.*;
import com.company.springforum.pojo.Phone;
import com.company.springforum.pojo.PhoneStatus;
import com.company.springforum.pojo.Receiver;
import com.company.springforum.pojo.Status;
import com.company.springforum.repository.*;
import com.company.springforum.service.article.ArticleService;
import com.company.springforum.service.user.UserService;
import com.company.springforum.tools.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*",  allowedHeaders = "*")
public class TestController {

    @Autowired
    public UserRepository repository;

    @Autowired
    public RoleRepository roleRepository;

    @Autowired
    public UserSecurityRepo repo;

    @Autowired
    public UserService service;

    @Autowired
    public ArticleRepository articleRepository;

    @Autowired
    public TagsRepository tagsRepository;

    @Autowired
    ArticleService Artservice;

    @Autowired RoomRepository roomRepository;

    @Autowired
    MarksRepository marksRepository;


    @GetMapping("/hello")
    public List<User> hello(HttpServletRequest request ,HttpServletResponse response) {
//        System.out.println(repository.findUsersByPhoneNum("2691642766@qq.com"));
        System.out.println(repository.findUsersByPhoneNum("12424234").size());
      List<User> re = repository.findUsersByEmail("2691642766@qq.com");
        System.out.println(request.getCookies()
        );
//        System.out.println(re);
        System.out.println(re);
        return re;
    }

    @PostMapping("/receiver")
    public ResponseEntity<Status> testReceiver(HttpServletRequest request , HttpServletResponse response) {
//         List<User> users = repository.findUsersByEmail("2691642766@qq.com");
        return new ResponseEntity<>(new Status("OK", new Receiver("helol","askdje","asdad")),HttpStatus.OK);
    }

    @GetMapping("/test2")
    public void testMax() {
//         List<User> users = repository.findUsersByEmail("2691642766@qq.com");
        List<User> re = repository.findUsersByEmail(null);
//        System.out.println(re.get(0).getRoles());
        List<Roles> roles = re.get(0).getRoles();
        List<SimpleGrantedAuthority> rolesList = new ArrayList<>();
        for (Roles t: roles) {
            System.out.println(t.getRoleName());
            rolesList.add(new SimpleGrantedAuthority(t.getRoleName()));
        }
        System.out.println(rolesList.get(0));

//        User user = re.get(0);
//        Roles role = new Roles("manager");
//        user.addRoles(role);
//        roleRepository.save(role);
//        System.out.println(repository.findById(9L));
    }
    @GetMapping("/me")
    public String testJwt(HttpServletRequest request ,HttpServletResponse response) throws IOException {
        System.out.println(service.deleteUserByEmail("GuangDongSam@outlook.com"));
        return "ok";
    }

    @GetMapping("/cookie")
    public String testCookie(HttpServletRequest request ,HttpServletResponse response){
        Cookie cookie = new Cookie("username","jaew");
        cookie.setMaxAge(60 * 60);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return "jaja";
    }

    @RequestMapping(value="/upload", method=RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("num") String num) {
        String Paths = "src/main/resources/static/avatars/"+"/"+num+"/";
        File directory = new File(Paths);
        if (!directory.exists()){
            directory.mkdirs();
        }
        String path = "src/main/resources/static/avatars/"+num+"/"+file.getOriginalFilename();
        File convertFile = new File(path);
        try {
            convertFile.createNewFile();
            FileOutputStream fout = new FileOutputStream(convertFile);
            fout.write(file.getBytes());
            fout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        User user = repository.findUserByUserNum(num);
        user.setAvatar("http://localhost:8080/avatars/"+num+"/"+file.getOriginalFilename());
        repository.save(user);
        System.out.println(file);
    }

    @PostMapping("/article")
    public void addArticle(@RequestBody ArticleDTO articles) {
        System.out.println(articles);
//        User user = repository.findUserByEmail("2691642766@qq.com");
//        Articles articles1 = new Articles(articles.getArticleTitle(),articles.getArticleContent(),
//                articles.getArticleBgi());
//        System.out.println(articles1);
//        user.addArticles(articles1);
//         Articles articles2 = articleRepository.save(articles1);
//         for (Tags tags :articles.getTags()) {
//             Tags tags1 = new Tags(tags.getTagColor(),tags.getTagName());
//             articles2.addTags(tags1);
//         }
//         for (Tags tags :articles2.getTags()){
//             tagsRepository.save(tags);
//         }
    }

    @GetMapping("/testNum")
    public ResponseEntity<Integer> testNum() {
        return new ResponseEntity<>(1,HttpStatus.OK);
    }
    @GetMapping("/testBgi")
    public ResponseEntity<Integer> testBgi() {
        articleRepository.findBgi(1);
        return new ResponseEntity<>(1,HttpStatus.OK);
    }

//    @GetMapping("/delete")
//    public void delete() {
//        articleRepository.deleteById(17);
//    }
    @GetMapping("/getAll")
    public void getAll() {
       List<Articles> articles = articleRepository.findAllByArticleIdIsNotNull();
       for (Articles articles1 : articles) {
//           System.out.println(articles1.getUser().getId());
//           System.out.println(repository.findById(articles1.getUser().getId()));;
//           System.out.println(repository.findArtUserInfo(articles1.getUser().getId()));
       }
    }

    @GetMapping("/delete")
    public void delete(){
     Room room  = roomRepository.findByRoomId(8);
     room.setArticles(null);
     roomRepository.save(room);
     roomRepository.deleteById(8);
    }

    @GetMapping("/load/{email}")
    public ResponseEntity<UserDto> loadUser(@PathVariable String email) {
        User user =  service.loadUser(email);
        System.out.println(user);
        if (user != null) {
            return new ResponseEntity<>(new UserDto("OK", user), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new UserDto("FAIL",null), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/room")
    public void newRoom() {
       Articles articles  = articleRepository.findByArticleId(9);
       Room room  = new Room(IdGenerator.generateRoom(articles.getArticleId()));
       room.setArticles(articles);
       roomRepository.save(room);
    }

    @GetMapping("/delete3")
    public void artDelete() {
        Articles articles = articleRepository.findByArticleId(20);
        articles.setUser(null);
        articleRepository.save(articles);
        articleRepository.deleteById(20);
    }

    @GetMapping("/AS")
    public void arS() {
    List<Articles> articles = articleRepository.findAllByArticleIdIsNotNull();
    for (Articles articles1 : articles ){
        System.out.println(articles1.getUser().getPassword());
    }
    }
//
//    @GetMapping("/desc")
//    public List<Articles>sort() {
//        List<Articles> articles = Artservice.findArticlesWithSort();
//        System.out.println(articles);
//        return articles;
//    }
    @GetMapping("/testMark/{aid}/{uid}")
    public ResponseEntity<String> testMark(@PathVariable int aid, @PathVariable Long uid) {
        Integer result = marksRepository.checkIfMark(aid, uid);
        if (result != null) {
            return new ResponseEntity<>("OK", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Bad", HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/markIt")
    public void markIt() {
        int uid = 11;
      Integer result = marksRepository.checkIfMark(9,11L);
      if (result.equals(uid)) {
        Marks marks = marksRepository.findByMarksId(1);
          marks.setUser(null);
          marks.setArticles(null);
          marksRepository.delete(marks);
          System.out.println(marks);
      } else {
         Marks marks = new Marks();
         User user = repository.findById(11L);
         Articles articles = articleRepository.findByArticleId(9);
         user.addMarks(marks);
         Marks newMarks = marksRepository.save(marks);
         articles.addMarks(newMarks);
         marksRepository.save(newMarks);
      }
    }

    @GetMapping("/search/{con}")
    public void search(@PathVariable String con) {
        System.out.println(articleRepository.searchText(con));
        System.out.println(articleRepository.searchTextByLikeTitle(con));
        System.out.println(articleRepository.searchTextByLikeContent(con));
    }

    @GetMapping("/roomtest")
    public Room roomTest() {
        return roomRepository.findSome();
    }
}
