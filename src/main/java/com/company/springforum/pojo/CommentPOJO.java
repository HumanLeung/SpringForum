package com.company.springforum.pojo;


import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommentPOJO {
    private int commentId;
    private String content;
    private String username;
    private String userNum;
    private String avatar;
}
