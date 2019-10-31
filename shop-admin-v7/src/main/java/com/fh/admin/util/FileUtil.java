package com.fh.admin.util;

import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import sun.misc.BASE64Encoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

public class FileUtil {

    /**
     * 导出pdf文件
     */
    public static void pdfDownload(HttpServletResponse response, ByteArrayOutputStream byffer) {

        // inline在浏览器中直接显示，不提示用户下载
        // attachment弹出对话框，提示用户进行下载保存本地
        // 默认为inline方式
        response.setHeader("Content-Disposition", "attachment; filename=" + UUID.randomUUID().toString() + ".pdf");
        // 不同类型的文件对应不同的MIME类型
        response.setContentType("application/octet-stream;charset=UTF-8");
        ServletOutputStream out;
        try {
            //获取输出流
            out = response.getOutputStream();
            //调用方法下载
            byffer.writeTo(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置标题
     */
    public static PdfPCell createHeadline(String value, Font font) {
        // 创建一个单元格
        PdfPCell cell = new PdfPCell();
        // new Paragraph()是段落的处理，可以设置段落的对齐方式，缩进和间距。
        cell.setPhrase(new Paragraph(value, font));
        //设置单元格的水平对齐方式
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        // 设置单元格的垂直对齐方式
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setMinimumHeight(30);//设置表格行高
        cell.setBorderWidth(0f);//去除表格的边框
        cell.setColspan(19);//跨列
        return cell;
    }

    /**
     * 创建单元格 设置内容
     */
    public static PdfPCell createCell(String value, Font font, int align) {
        // 创建一个单元格
        PdfPCell cell = new PdfPCell();
        // 设置单元格的垂直对齐方式
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        // 设置单元格的水平对齐方式
        cell.setHorizontalAlignment(align);
        cell.setPhrase(new Phrase(value, font));
        return cell;
    }

    static int maxWidth = 520;// 总宽度

    /**
     * 创建一个书写器
     */
    public static PdfPTable createTable(int colNumber) {
        // 创建表格
        PdfPTable table = new PdfPTable(colNumber);
        // 设置表格的总宽度
        table.setTotalWidth(maxWidth);
        //锁定宽度
        table.setLockedWidth(true);
        // 设置表格的垂直对齐方式
        table.setHorizontalAlignment(Element.ALIGN_CENTER);
        // 设置边框
        table.getDefaultCell().setBorder(1);
        return table;
    }

    /**
     * 导出excel
     */
    public static void excelDownload(XSSFWorkbook wirthExcelWB, HttpServletResponse response) {
        OutputStream out = null;
        try {
            out = response.getOutputStream();
            // 让浏览器识别是什么类型的文件
            response.reset(); // 重点突出
            response.setCharacterEncoding("UTF-8"); // 重点突出
            response.setContentType("application/x-msdownload");// 不同类型的文件对应不同的MIME类型
            // // 重点突出
            // inline在浏览器中直接显示，不提示用户下载
            // attachment弹出对话框，提示用户进行下载保存本地
            // 默认为inline方式
            response.setHeader("Content-Disposition", "attachment;filename=" + UUID.randomUUID().toString() + ".xlsx");
            wirthExcelWB.write(out);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != out) {
                try {
                    out.close();
                    out = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 文件下载
     */
    public static void downloadFile(HttpServletRequest request,HttpServletResponse response,String path,String name){
        BufferedInputStream bis = null;
        InputStream is = null;
        OutputStream os = null;
        BufferedOutputStream bos = null;
        try {
            File file=new File(path);
            is = new FileInputStream(file);  //文件流的声明
            os = response.getOutputStream(); //重点突出(特别注意),通过response获取的输出流，作为服务端向客户端浏览器输出内容的通道
            // 为了提高效率使用缓冲区流
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(os);
            // 处理下载文件名的乱码问题(根据浏览器的不同进行处理)
            if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
                name = new String(name.getBytes("GB2312"),"ISO-8859-1");
            } else {
                // 对文件名进行编码处理中文问题
                name = java.net.URLEncoder.encode(name, "UTF-8");// 处理中文文件名的问题
                name = new String(name.getBytes("UTF-8"), "GBK");// 处理中文文件名的问题
            }
            response.reset(); // 重点突出
            response.setCharacterEncoding("UTF-8"); // 重点突出
            response.setContentType("application/x-msdownload");// 不同类型的文件对应不同的MIME类型 // 重点突出
            response.setHeader("Content-Disposition", "attachment;filename="+ name);// 重点突出
            int bytesRead = 0;
            byte[] buffer = new byte[4096];
            while ((bytesRead = bis.read(buffer)) != -1){ //重点
                bos.write(buffer, 0, bytesRead);// 将文件发送到客户端
                bos.flush();
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        } finally {
            // 特别重要
            // 1. 进行关闭是为了释放资源
            // 2. 进行关闭会自动执行flush方法清空缓冲区内容
            try {
                if (null != bis) {
                    bis.close();
                    bis = null;
                }
                if (null != bos) {
                    bos.close();
                    bos = null;
                }
                if (null != is) {
                    is.close();
                    is = null;
                }
                if (null != os) {
                    os.close();
                    os = null;
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex.getMessage());
            }
        }
    }

    /**
     * InputStream
     **/
    public static void downloadFileInputStream(HttpServletRequest request,HttpServletResponse response,InputStream is,String hz){
        BufferedInputStream bis = null;
        OutputStream os = null;
        BufferedOutputStream bos = null;
        try {
            os = response.getOutputStream(); //重点突出(特别注意),通过response获取的输出流，作为服务端向客户端浏览器输出内容的通道
            // 为了提高效率使用缓冲区流
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(os);
            // 处理下载文件名的乱码问题(根据浏览器的不同进行处理)
            String name = UUID.randomUUID().toString() + hz;
            if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
                name = new String(name.getBytes("GB2312"),"ISO-8859-1");
            } else {
                // 对文件名进行编码处理中文问题
                name = java.net.URLEncoder.encode(name, "UTF-8");// 处理中文文件名的问题
                name = new String(name.getBytes("UTF-8"), "GBK");// 处理中文文件名的问题
            }
            response.reset(); // 重点突出
            response.setCharacterEncoding("UTF-8"); // 重点突出
            response.setContentType("application/x-msdownload");// 不同类型的文件对应不同的MIME类型 // 重点突出
            response.setHeader("Content-Disposition", "attachment;filename="+ name);// 重点突出
            int bytesRead = 0;
            byte[] buffer = new byte[4096];
            while ((bytesRead = bis.read(buffer)) != -1){ //重点
                bos.write(buffer, 0, bytesRead);// 将文件发送到客户端
                bos.flush();
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        } finally {
            // 特别重要
            // 1. 进行关闭是为了释放资源
            // 2. 进行关闭会自动执行flush方法清空缓冲区内容
            try {
                if (null != bis) {
                    bis.close();
                    bis = null;
                }
                if (null != bos) {
                    bos.close();
                    bos = null;
                }
                if (null != is) {
                    is.close();
                    is = null;
                }
                if (null != os) {
                    os.close();
                    os = null;
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex.getMessage());
            }
        }
    }

    public static String getImageStr(String image)
            throws IOException {
        InputStream is = new FileInputStream(image);
        BASE64Encoder encoder = new BASE64Encoder();
        byte[] data = new byte[is.available()];
        is.read(data); is.close();
        return encoder.encode(data);
    }

    public static String getSuffix(String fileName){
        int i = fileName.lastIndexOf(".");
        return fileName.substring(i);
    }

}
