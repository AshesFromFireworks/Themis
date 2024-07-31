package com.themis.Controller;

import com.themis.Model.DTO.test;
import com.themis.Utils.ImgBase64;
import com.themis.Utils.ImgCompress;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.io.*;

@RestController
public class TestController {


    @RequestMapping("/UploadFile")
    public String deal(MultipartFile file) throws IOException {
        byte[] bytes = ImgCompress.compressPicForScale(file.getBytes(), 200);
        String file_name = "E:\\competition\\测试\\res\\test-res.png";
        File out = new File(file_name);
        FileOutputStream fileOutputStream = new FileOutputStream(out);
        fileOutputStream.write(bytes);
        fileOutputStream.close();
        return file_name;
    }


    @RequestMapping("/download/{filename}")
    public void img(HttpServletResponse response, @PathVariable("filename") String filename){
        File file = new File("result.jpg");
        try {
            FileInputStream in = new FileInputStream(file);
            OutputStream out = response.getOutputStream();
            response.setContentType("application/octet-stream; charset=UTF-8");
            byte[] a = new byte[1024];
            while(in.read(a) != -1){
                out.write(a);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
