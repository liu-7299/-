package com.fh.admin.controller;

import com.fh.admin.biz.commodity.ICommodityService;
import com.fh.admin.commons.DataTableMap;
import com.fh.admin.commons.Log;
import com.fh.admin.commons.ServerResponse;
import com.fh.admin.param.CommodityParam;
import com.fh.admin.po.Commodity;
import com.fh.admin.util.ExcelUtil;
import com.fh.admin.util.FileUtil;
import com.fh.admin.util.SystemCount;
import com.fh.admin.vo.commodity.CommodityVo;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
import java.util.List;

@Controller
@RequestMapping("commodity")
public class CommodityController {

    @Autowired
    private ICommodityService commodityService;
//private static final Logger LOGGER = LoggerFactory.getLogger(CommodityController.class);
    private static final Logger log = LoggerFactory.getLogger(CommodityController.class);

    @RequestMapping("skipListJsp")
    public String skipListJsp(){
        return SystemCount.COMM_LIST_JSP;
    }

    @RequestMapping("queryCommodityPage")
    @ResponseBody
    public DataTableMap queryCommodityPage(CommodityParam comm){
        Long count = commodityService.queryCommCount(comm);
        List<CommodityVo> list = commodityService.queryCommodityPage(comm);
        DataTableMap map = new DataTableMap(comm.getDraw(),count,count,list);
        return map;
    }

    @RequestMapping("addCommodity")
    @ResponseBody
    @Log("添加一个商品")
    public ServerResponse addCommodity(Commodity comm){
        log.info("添加了一个商品");
        return commodityService.addCommodity(comm);
    }

    @RequestMapping("deleteCommodity")
    @ResponseBody
    @Log("删除一个商品")
    public ServerResponse deleteCommodity(Long id){
        return commodityService.deleteCommodity(id);
    }

    @RequestMapping("queryCommodityById")
    @ResponseBody
    public ServerResponse queryCommodityById(Long id){
        return commodityService.queryCommodityById(id);
    }

    @RequestMapping("updateCommodity")
    @ResponseBody
    @Log("修改商品")
    public ServerResponse updateCommodity(Commodity comm){
        return commodityService.updateCommodity(comm);
    }

    @RequestMapping("updateCommodityStatus")
    @ResponseBody
    @Log("修改商品状态")
    public ServerResponse updateCommodityStatus(Long id){
        return commodityService.updateCommodityStatus(id);
    }

    @Autowired
    HttpServletRequest request;

    @RequestMapping("wordEduce")
    public void wordEduce(HttpServletResponse response) throws IOException, TemplateException {
        Map<String,Object> map = new HashMap();
        List<CommodityVo> list = commodityService.queryCommodity();
        List<Map<String,Object>> lis = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            Map<String,Object> obj = new HashMap();
            obj.put("name", list.get(i).getName());
            obj.put("price", list.get(i).getPrice());
            obj.put("imgPath", list.get(i).getImgPath());
            obj.put("createTime", list.get(i).getCreateTime());//list.get(i).getCreateTime()
            obj.put("stock", list.get(i).getStock());
            obj.put("status", list.get(i).getStatus());
            lis.add(obj);
        }
        map.put("list", lis);
        Configuration configuration = new Configuration();
        configuration.setDefaultEncoding("UTF-8");
        configuration.setClassForTemplateLoading(this.getClass(), "/muban");
        Template template = configuration.getTemplate("commodity.ftl");
        File file = new File("D://word商品测试.docx");
        FileOutputStream fos = new FileOutputStream(file);
        OutputStreamWriter osw = new OutputStreamWriter(fos,"UTF-8");
        template.process(map, osw);
        //关流
        osw.close();
        FileUtil.downloadFile(request, response, file.getPath(), file.getName());
        file.delete();
    }

    @RequestMapping("excelEduceComm")
    public void excelEduceComm(CommodityParam commodityParam,HttpServletResponse response){
        List<Commodity> list = commodityService.queryCommodityPam(commodityParam);
        String[] heads = {"商品名","商品价格","商品库存","生产时间","分类名"};
        String[] prpos = {"name","price","stock","createTime","flName"};
        XSSFWorkbook xssDworkbook = ExcelUtil.getXSSDworkbook("商品列表", list, heads, prpos, Commodity.class);
        FileUtil.excelDownload(xssDworkbook,response);
    }

    @RequestMapping("pdfEduce")
    public void pdfEduce(HttpServletResponse response){
        //定义一个字节流数组
        ByteArrayOutputStream byo = new ByteArrayOutputStream();
        //查询到数据集合
        List<CommodityVo> list = commodityService.queryCommodity();
        //创建一个doucment对象 文本对象
        Document document = new Document();
        document.setPageSize(PageSize.A4);
        //创建字体 设置为中文
        BaseFont font = null;
        //创建列的字体样式
        Font keyFont = null;
        try {
            font = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            //创建列的字体样式
            keyFont = new Font(font, 10,Font.BOLD);
            //创建pdf文件
            PdfWriter.getInstance(document, byo);
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //设置一个表头数组
        String[] str = {"商品名","商品价格","商品图片","生产日期","库存量","商品状态"};
        //设置书写器
        PdfPTable table = FileUtil.createTable(str.length);
        //打开文本对象
        document.open();
        //设置标题
        Font headFont = new Font(font,25,Font.BOLD);
        //设置标题的颜色
        headFont.setColor(BaseColor.PINK);
        //创建标题
        PdfPCell headCell = FileUtil.createHeadline("用户信息", headFont);
        headCell.setExtraParagraphSpace(30);
        //放入书写器
        table.addCell(headCell);
        for (int i = 0; i < str.length; i++) {
            table.addCell(FileUtil.createCell(str[i], keyFont, Element.ALIGN_CENTER));
        }
        //把查询到的数据集合遍历到书写器里面
        for (CommodityVo comm : list){
                table.addCell(FileUtil.createCell(comm.getName(), keyFont, Element.ALIGN_CENTER));
                table.addCell(FileUtil.createCell(comm.getPrice(), keyFont, Element.ALIGN_CENTER));
                table.addCell(FileUtil.createCell(comm.getImgPath(), keyFont, Element.ALIGN_CENTER));
                table.addCell(FileUtil.createCell(comm.getCreateTime(), keyFont, Element.ALIGN_CENTER));
                table.addCell(FileUtil.createCell(String.valueOf(comm.getStock()), keyFont, Element.ALIGN_CENTER));
                table.addCell(FileUtil.createCell(String.valueOf(comm.getStatus()), keyFont, Element.ALIGN_CENTER));
        }
        //放入文本对象
        try {
            document.add(table);
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        document.close();
        FileUtil.pdfDownload(response, byo);
    }

}
