package com.pinyougou.shop.controller;

import entity.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import util.FastDFSClient;

@RestController
public class uploadController {

@Value("${FILE_SERVER_URL}")
    private  String FILE_SERVER_URL ;

  @RequestMapping("/upload")
    public Result upload(MultipartFile file)  {

            //1.获取文件的扩站名
         String filename = file.getOriginalFilename();
         String substring = filename.substring(filename.lastIndexOf('.') + 1); //最后一个点后面的就是而文件的扩站名

           // 2创建一个 fastDFS的客户端
         try {
             FastDFSClient fastDFSClient = new FastDFSClient("classpath:config/fdfs_client.conf");
            // 3. 执行 上传
             String path = fastDFSClient.uploadFile(file.getBytes(),substring);
             //4、拼接返回的 url 和 ip 地址，拼装成完整的 url
             String url =   FILE_SERVER_URL+path;

             return  new Result(true,url);
         } catch (Exception e) {
             e.printStackTrace();

             return   new Result(false,"上传失败");
         }


     }
}
