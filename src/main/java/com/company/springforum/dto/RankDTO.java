package com.company.springforum.dto;


import com.company.springforum.model.Room;
import com.company.springforum.model.Tags;
import com.company.springforum.pojo.CommentPOJO;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RankDTO {
    private int articleId;
    private String articleTitle;
    private String articleContent;
    private Timestamp articleDate;
    private String articleBgi;
    private Integer lookCount;
    private List<Tags> tags;
    private String username;
    private String userNum;
    private Long userId;
    private String avatar;
    private Room room;
    private List<CommentPOJO> comments;
}
