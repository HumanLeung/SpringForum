package com.company.springforum.tools;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

// FIXME: 2021/5/5

public class FileTool {
    static public String DirectoryTool (String Paths) {
        File directory = new File(Paths);
        if (!directory.exists()){
            directory.mkdirs();
        }
        return Paths;
    }
    
    static public String createFile(String Path, MultipartFile file) {
       String files = Path +"/"+file.getOriginalFilename();
       File nf = new File(files);
       if (!nf.exists()){
           try {
               nf.createNewFile();
               FileOutputStream fout = new FileOutputStream(nf);
               fout.write(file.getBytes());
               fout.close();
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
       return Path +"/"+file.getOriginalFilename();
    }
    
}
