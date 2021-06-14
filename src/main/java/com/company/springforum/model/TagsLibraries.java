package com.company.springforum.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tags_libraries", schema = "myforum", catalog = "")
public class TagsLibraries {
    private int tagId;
    private String tagColor;
    private String tagName;

    @Id
    @Column(name = "tag_id")
    @GeneratedValue
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
        TagsLibraries that = (TagsLibraries) o;
        return tagId == that.tagId && Objects.equals(tagColor, that.tagColor) && Objects.equals(tagName, that.tagName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tagId, tagColor, tagName);
    }
}
