package com.company.springforum.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name="temps")
public class Temps {
    private int tempId;
    private String tempContent;
    private String tempArticleTitle;
    private Timestamp tempDate;

    @Id
    @GeneratedValue
    @Column(name = "temp_id")
    public int getTempId() {
        return tempId;
    }

    public void setTempId(int tempId) {
        this.tempId = tempId;
    }

    @Basic
    @Column(name = "temp_content")
    public String getTempContent() {
        return tempContent;
    }

    public void setTempContent(String tempContent) {
        this.tempContent = tempContent;
    }

    @Basic
    @Column(name = "temp_article_title")
    public String getTempArticleTitle() {
        return tempArticleTitle;
    }

    public void setTempArticleTitle(String tempArticleTitle) {
        this.tempArticleTitle = tempArticleTitle;
    }

    @Basic
    @Column(name = "temp_date")
    public Timestamp getTempDate() {
        return tempDate;
    }

    public void setTempDate(Timestamp tempDate) {
        this.tempDate = tempDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Temps temps = (Temps) o;
        return tempId == temps.tempId && Objects.equals(tempContent, temps.tempContent) && Objects.equals(tempArticleTitle, temps.tempArticleTitle) && Objects.equals(tempDate, temps.tempDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tempId, tempContent, tempArticleTitle, tempDate);
    }
}
