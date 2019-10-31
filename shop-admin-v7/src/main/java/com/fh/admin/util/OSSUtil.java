package com.fh.admin.util;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.OSSObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

public class OSSUtil {
    //log
    private static final Logger LOG = LoggerFactory.getLogger(OSSUtil.class);
    //阿里云API的内或外网域名
    private static String ENDPOINT = "https://oss-cn-beijing.aliyuncs.com";//endpoint以北京为例，其它region请按实际情况填写
    //阿里云API的密钥Access Key ID
    private static String ACCESS_KEY_ID = "LTAI4Ficp1V6SGCHpGaw6c8R";
    //阿里云API的密钥Access Key Secret
    private static String ACCESS_KEY_SECRET = "Q1G2ROPQNiZE7ycb1Q4Ku1asnhqz3V";
    //oss服务器Bucket名
    private static String BUCKETNAME = "liu-7299";
    //你要存放的Bucket的目录
    private static String DISKNAME = "gen/img/";
    //阿里云OSS客户端对象
    private static OSSClient client = null;
    //
    private static String PATH = "https://liu-7299.oss-cn-beijing.aliyuncs.com/";

    private static OSSClient getClient(){//获取阿里云OSS客户端对象
        return new OSSClient(ENDPOINT,ACCESS_KEY_ID, ACCESS_KEY_SECRET);
    }

    public static final String uploadOSSObject(InputStream is,String fileName) {
        try {
            client = getClient();
            String suffix = FileUtil.getSuffix(fileName);//获取文件后缀名
            fileName = UUID.randomUUID().toString() + suffix;//新文件名
            DISKNAME = DateUtil.dateToString(new Date(), DateUtil.STRING_Y_M_D) + "/";//以日期为文件夹名
            OSSUtil.client.putObject(BUCKETNAME, DISKNAME + fileName, is);//上传文件
        } catch (OSSException e) {
            e.printStackTrace();
            LOG.error("上传阿里云OSS服务器异常." + e.getMessage(), e);
            throw new RuntimeException(e);
        } catch (ClientException e) {
            e.printStackTrace();
            LOG.error("上传阿里云OSS服务器异常." + e.getMessage(), e);
            throw new RuntimeException(e);
        } finally {
            try {
                if(null != client){
                    client.shutdown();
                }
                if(null != is){
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return PATH + DISKNAME + fileName;//返回全路径
    }
    //删除OSS服务器上的文件
    public static void deleteOSSFile(String imgPath){//文件在oss服务器上的全路径
        try {
            if(StringUtils.isNotEmpty(imgPath)){
                client = getClient();
                String path = imgPath.replace(PATH, "");
                client.deleteObject(BUCKETNAME, path);
                LOG.info("删除" + BUCKETNAME + "下的文件" + path + "成功");
            }
        } catch (OSSException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (ClientException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally {
            try {
                if(null != client){
                    client.shutdown();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    //根据key获取OSS服务器上的文件输入流
    public static final InputStream getOSSInputStream(String filePath){
        if(null != filePath){
            client = getClient();
            OSSObject ossObj = client.getObject(BUCKETNAME, filePath);
            return ossObj.getObjectContent();
        }
        return null;
    }

}
