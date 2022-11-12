package top.hellocode.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.hellocode.common.R;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

/**
 * @author HelloCode
 * @blog https://www.hellocode.top
 * @date 2022年11月08日 19:17
 */
@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {
    @Value("${Reggie.base-path}")
    private String basePath;

    /**
     * @Description: 文件上传
     * @param: file
     * @return R<String>
     **/
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file){
        // 获取文件初始名
        String originalFilename = file.getOriginalFilename();
        // 获取文件后缀
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        // 通过UUID生成新文件名并拼接后缀
        String fileName = UUID.randomUUID().toString().replace("-", "") + suffix;
        log.info(fileName);
        File path = new File(basePath);
        // 路径不存在就创建
        if(!path.exists()){
            path.mkdirs();
        }
        try {
            file.transferTo(new File(basePath + fileName));
        } catch (IOException e) {
            e.printStackTrace();
            R.error("图片上传失败");
        }
        return R.success(fileName);
    }

    /**
     * @Description: 文件下载
     * @param: name
     * @return R<String>
     **/
    @GetMapping("/download")
    public void download(HttpServletResponse response,String name){
        try {
            // 获取文件IO流对象
            FileInputStream fileInputStream = new FileInputStream(new File(basePath + name));
            ServletOutputStream outputStream = response.getOutputStream();
            // 设置文件格式
            response.setContentType("image/jpeg");
            // 循环读写
            byte[] bytes = new byte[1024];
            int len = 0;
            while((len = fileInputStream.read(bytes)) != -1){
                outputStream.write(bytes,0,len);
                outputStream.flush();
            }
            // 释放资源
            fileInputStream.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
