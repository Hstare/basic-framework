package com.hew.basicframework.controller;

import com.hew.basicframework.entity.ExcelDataListener;
import com.hew.basicframework.utils.ExcelUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * @author HeXiaoWei
 * @date 2020/10/17 22:32
 */
@RestController
public class ExcelController {
    @PostMapping("upload")
    public String upload(MultipartFile file) throws IOException {
        return "success";
    }

    @GetMapping("download")
    public void download(HttpServletResponse response, String fileName) throws IOException {
//        ExcelUtils.write(fileName,);
    }
}
