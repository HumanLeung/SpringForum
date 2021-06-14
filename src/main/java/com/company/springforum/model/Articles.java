package com.company.springforum.model;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="articles")
public class Articles implements Serializable {
    private int articleId;
    private String articleTitle;
    private String articleContent;
    private Timestamp articleDate;
    private String articleBgi;
    private Integer lookCount;
    private User user;
    private Room room;
    private List<Tags> tags;
    private List<Comments> comments;
    private List<Marks> marks;

    @OneToMany(mappedBy = "articles", cascade = CascadeType.ALL)
    public List<Marks> getMarks() {
        return marks;
    }

    public void setMarks(List<Marks> marks) {
        this.marks = marks;
    }

    @OneToMany(mappedBy="articles" ,cascade = CascadeType.ALL)
    public List<Comments> getComments() {
        return comments;
    }

    public void setComments(List<Comments> comments) {
        this.comments = comments;
    }

    public void addComments(Comments comment) {
        if (comments == null) {
            comments = new ArrayList<>();
        }
        comments.add(comment);
        comment.setArticles(this);
    }

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name="article_tags", joinColumns = @JoinColumn(name="article_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    public List<Tags> getTags() {
        return tags;
    }

    public void setTags(List<Tags> tags) {
        this.tags = tags;
    }

    public Articles(String articleTitle, String articleContent, String articleBgi,Integer lookCount) {
        this.articleTitle = articleTitle;
        this.articleContent = articleContent;
        this.articleBgi = articleBgi;
        this.lookCount = lookCount;
    }

    public Articles() {

    }
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void addMarks(Marks mark) {
        if (marks == null ){
            marks = new ArrayList<>();
        }
        marks.add(mark);
        mark.setArticles(this);
    }

    @Id
    @Column(name = "article_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public void addTags(Tags tag) {
        if (tags == null) {
            tags = new ArrayList<>();
        }
        tags.add(tag);
    }

    @Basic
    @Column(name = "article_title")
    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    @Basic
    @Column(name ="look_count")
    public Integer getLookCount() {
        return lookCount;
    }

    public void setLookCount(Integer lookCount) {
        this.lookCount = lookCount;
    }

    @Basic
    @Column(name = "article_content")
    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    @Basic
    @Column(name = "article_date")
    public Timestamp getArticleDate() {
        return articleDate;
    }

    public void setArticleDate(Timestamp articleDate) {
        this.articleDate = articleDate;
    }

    @Basic
    @Column(name = "article_bgi")
    public String getArticleBgi() {
        return articleBgi;
    }

    public void setArticleBgi(String articleBgi) {
        this.articleBgi = articleBgi;
    }

    @OneToOne(mappedBy = "articles",cascade = {CascadeType.PERSIST, CascadeType.MERGE,CascadeType.DETACH,
            CascadeType.REFRESH,CascadeType.REMOVE})
    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Articles articles = (Articles) o;
        return articleId == articles.articleId && Objects.equals(articleTitle, articles.articleTitle) && Objects.equals(articleContent, articles.articleContent) && Objects.equals(articleDate, articles.articleDate) && Objects.equals(articleBgi, articles.articleBgi);
    }

    @Override
    public int hashCode() {
        return Objects.hash(articleId, articleTitle, articleContent, articleDate, articleBgi);
    }

//    @Override
//    public String toString() {
//        return "Articles{" +
//                "articleId=" + articleId +
//                ", articleTitle='" + articleTitle + '\'' +
//                ", articleContent='" + articleContent + '\'' +
//                ", articleDate=" + articleDate +
//                ", articleBgi='" + articleBgi + '\'' +
//                ", tags=" + tags + '\'' +
//                ", lookCount=" + lookCount + '\'' +
//                ", user=" + user.getId() + '\'' +
//                ", user=" + comments + '\'' +
//                '}';
//    }
}
