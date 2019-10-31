package com.fh.admin.util;

import org.apache.commons.net.ftp.*;

import java.io.*;
import java.util.UUID;

public class FtpClientUtils implements Serializable {
//  服务器的ip
    private String hostName = "192.168.217.128";
//  服务器的端口
    private int port = 21;
//  用户名
    private String userName = "ftplxy";
//  密码
    private String passWord = "123";
//  存放的位置
    private String path = "img";
 
 
    /**
     * 获得FTP连接方式
     */
    public FTPClient getConnectionFTP() {
        //创建FTPClient对象
        FTPClient ftp = new FTPClient();
        try {
            //连接FTP服务器
            ftp.connect(hostName, port);
 
            //下面三行代码必须要，而且不能改变编码格式，否则不能正确下载中文文件
            ftp.setControlEncoding("GBK");
            FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
            conf.setServerLanguageCode("zh");
 
            //登录ftp
            ftp.login(userName, passWord);
 
            if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
                ftp.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ftp;
    }
 
    /**
     * 关闭连接FTP方式
     * @param ftp FTPClient对象
     * @return boolean
     */
    public boolean closeFTP(FTPClient ftp) {
        if (ftp.isConnected()) {
            try {
                ftp.disconnect();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
 
    /**
     * 上传文件到FTP的方式
     * @param ftp FTPClient对象
     * @param fileName 文件名
     * @param inputStream 文件流
     * @return boolean
     */
    public String uploadFile(FTPClient ftp, String fileName, InputStream inputStream) {
 
        try {
//          让客户端告诉服务端开通一个端口用来数据传输（必须要 不然会一直卡死）
            ftp.enterLocalPassiveMode();
 
//          获取文件存放的目录
            ftp.changeWorkingDirectory(path);
 
//          获取文件目录所有的文件
            FTPFile[] fs = ftp.listFiles();
 
//          判断是否有重复的文件名、有的话就重新生成一个
            fileName = FtpClientUtils.isFileExist(fileName, fs);
 
//          如果缺省该句 传输txt正常 但图片和其他格式的文件传输出现乱码
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
 
//          开始上传
            boolean boo  = ftp.storeFile(fileName, inputStream);
            if (boo){
                return fileName;
            }
 
            //关闭输入流
            inputStream.close();
 
            //退出ftp
            ftp.logout();
 
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
 
    /**
     * 删除文件FTP方式
     * @param ftp FTPClient对象
     * @param fileName FTP服务器上要删除的文件名
     * @return
     */
    public boolean deleteFile(FTPClient ftp, String fileName) {
        boolean success = false;
        try {
//          让客户端告诉服务端开通一个端口用来数据传输（必须要 不然会一直卡死）
            ftp.enterLocalPassiveMode();
 
//          获取文件存放的目录
            ftp.changeWorkingDirectory(path);
 
//          开始删除
            success = ftp.deleteFile(fileName);
 
//          退出登录
            ftp.logout();
 
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }
 
    /**
     * 从ftp服务器上下载文件
     * @param ftp FTPClient对象
     * @param path FTP服务器上传地址
     * @param fileName 本地文件路径
     * @param localPath 本里存储路径
     * @return boolean
     */
    public boolean downFile(FTPClient ftp, String path, String fileName, String localPath) {
    	boolean success = false;
        try {
//          让客户端告诉服务端开通一个端口用来数据传输（必须要 不然会一直卡死）
            ftp.enterLocalPassiveMode();
 
//          获取文件存放的目录
            ftp.changeWorkingDirectory(path);
 
//          获取指定目录下的所有文件
            FTPFile[] fs = ftp.listFiles();
 
            for (FTPFile ff : fs) {
                if (ff.getName().equals(fileName)) {
                    File localFile = new File(localPath + "\\" + ff.getName());
                    OutputStream outputStream = new FileOutputStream(localFile);
                    //将文件保存到输出流outputStream中
                    ftp.retrieveFile(new String(ff.getName().getBytes("GBK"), "ISO-8859-1"), outputStream);
                    outputStream.flush();
                    outputStream.close();
                }
            }
//          退出登录
            ftp.logout();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }
 
    /**
     * 根据文件名获取文件流
     * @param ftp
     * @param fileName
     * @return
     */
    public InputStream downFile(FTPClient ftp,String fileName) {
//      让客户端告诉服务端开通一个端口用来数据传输（必须要 不然会一直卡死）
        ftp.enterLocalPassiveMode();
        try {
//          获取文件存放的目录
            ftp.changeWorkingDirectory(path);
 
//          获取指定目录下的所有文件
            FTPFile[] fs = ftp.listFiles();
 
            for (FTPFile ff : fs) {
                if (ff.getName().equals(fileName)) {
                    return ftp.retrieveFileStream(ff.getName());
                }
            }
//          退出登录
            ftp.logout();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
 
    /**
     * 判断是否有重名文件
     * @param fileName
     * @param fs
     * @return
     */
    public static String isFileExist(String fileName, FTPFile[] fs) {
        for (FTPFile ftpFile : fs) {
            if (ftpFile.getName().equals(fileName)) {
                return UUID.randomUUID().toString()+ fileName.substring(fileName.indexOf("."));
            }
        }
        return fileName;
    }

}
