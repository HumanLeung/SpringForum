package com.company.springforum.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="tags")
public class Tags implements Serializable {
    private int tagId;
    private String tagColor;
    private String tagName;
    private List<Articles> articles;

    public Tags(String tagColor, String tagName) {
        this.tagColor = tagColor;
        this.tagName = tagName;
    }

    public Tags() {

    }

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name="article_tags",joinColumns = @JoinColumn(name="tag_id")
            ,inverseJoinColumns=@JoinColumn(name="article_id"))
    public List<Articles> getArticles() {
        return articles;
    }

    public void setArticles(List<Articles> articles) {
        this.articles = articles;
    }

    @Id
    @Column(name = "tag_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    @Basic
    @Column(name = "tag_color")
    public String getTagColor() {
        return tagColor;
    }

    public void setTagColor(String tagColor) {
        this.tagColor = tagColor;
    }

    @Basic
    @Column(name = "tag_name")
    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tags tags = (Tags) o;
        return tagId == tags.tagId && Objects.equals(tagColor, tags.tagColor) && Objects.equals(tagName, tags.tagName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tagId, tagColor, tagName);
    }

    @Override
    public String toString() {
        return "Tags{" +
                "tagId=" + tagId +
                ", tagColor='" + tagColor + '\'' +
                ", tagName='" + tagName + '\'' +
                '}';
    }
}
