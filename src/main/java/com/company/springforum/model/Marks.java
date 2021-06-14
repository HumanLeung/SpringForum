package com.company.springforum.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="marks")
public class Marks {
    private int marksId;
    private User user;
    private Articles articles;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="article_id")
    public Articles getArticles() {
        return articles;
    }

    public void setArticles(Articles articles) {
        this.articles = articles;
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

    @Id
    @Column(name = "marks_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getMarksId() {
        return marksId;
    }

    public void setMarksId(int marksId) {
        this.marksId = marksId;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Marks marks = (Marks) o;
        return marksId == marks.marksId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(marksId);
    }

    @Override
    public String toString() {
        return "Marks{" +
                "marksId=" + marksId +
                '}';
    }
}
