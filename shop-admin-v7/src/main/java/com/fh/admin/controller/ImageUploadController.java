package com.fh.admin.controller;

import com.fh.admin.commons.ServerResponse;
import com.fh.admin.util.FtpClientUtils;
import com.fh.admin.util.OSSUtil;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("img")
public class ImageUploadController {

    @RequestMapping("imgUpload")
    @ResponseBody
    public ServerResponse getImageUpload(MultipartFile file){
        Map map = new HashMap();
        InputStream inputStream;
        String imgName = null;
        String newName = null;
        try {
            if(!file.isEmpty()) {//判断文件是否为空
                imgName = file.getOriginalFilename();
                inputStream = file.getInputStream();
                FtpClientUtils ftp = new FtpClientUtils();
                FTPClient connectionFTP = ftp.getConnectionFTP();
                newName = ftp.uploadFile(connectionFTP, imgName, inputStream);
                System.out.println();
                ftp.closeFTP(connectionFTP);
            }
            if(newName == null){
                System.out.println();
                newName = imgName;
            }
            return ServerResponse.success(newName);
        } catch (IOException e) {
            e.printStackTrace();
            return ServerResponse.error();
        }
    }

    @RequestMapping("imageUpload")
    @ResponseBody
    public ServerResponse imageUpload(MultipartFile file){
        String fileName = file.getOriginalFilename();
        InputStream is = null;
        try {
            is = file.getInputStream();
            String newName = OSSUtil.uploadOSSObject(is,fileName);
            return ServerResponse.success(newName);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally {
            if(null != is){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
