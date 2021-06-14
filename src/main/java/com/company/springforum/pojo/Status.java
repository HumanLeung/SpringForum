package com.company.springforum.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class Status {
    private String state;
    private Receiver receiver;
}
