package com.company.springforum.tools;

import java.util.Calendar;
import java.util.Random;

public class IdGenerator {


    static public String generateId(int top) {
        String Id ;
        Calendar rightNow = Calendar.getInstance();
        int hour = rightNow.get(Calendar.MINUTE);
        int seconds = rightNow.get(Calendar.SECOND);
        int month = rightNow.get(Calendar.MONTH);
        Random rd = new Random(System.currentTimeMillis());
        int num = rd.nextInt(10);
        boolean result = rd.nextBoolean();
        int fiveDigits = rd.nextInt(8000)+1000;
        if (result){
            Id = top +""+ num+ ""+ hour + "" + fiveDigits + "" + seconds + "" ;
        } else {
            Id = top +""+ num+ ""+ month + "" + seconds + "" + fiveDigits + "";
        }
        System.out.println(Id);
        return Id;
    }
    static public String generateRoom(int id) {
        Random rd = new Random(System.currentTimeMillis());
        Calendar rightNow = Calendar.getInstance();
        int num = rd.nextInt(5);
        int seconds = rightNow.get(Calendar.SECOND);
        return  (id +""+ num +""+seconds);
    }
}
