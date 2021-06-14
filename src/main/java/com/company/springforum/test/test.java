package com.company.springforum.test;

import java.io.File;

public class test {
    public static void main(String[] args) {
        String path = System.getProperty("user.dir")+"\\src\\main\\main\\resources\\static\\myforum\\" +
                "*\\article\\bgi";
        File file = new File(path);
        System.out.println(file);
        System.out.println(path);
    }
}
