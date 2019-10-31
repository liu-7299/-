package com.fh.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.fh.admin.biz.menu.IMenuService;
import com.fh.admin.biz.user.IUserService;
import com.fh.admin.commons.DataTableMap;
import com.fh.admin.commons.Log;
import com.fh.admin.commons.ResponseEnum;
import com.fh.admin.commons.ServerResponse;
import com.fh.admin.param.UpdatePass;
import com.fh.admin.param.UserParam;
import com.fh.admin.po.Menu;
import com.fh.admin.po.User;
import com.fh.admin.servlet.DistributedSession;
import com.fh.admin.util.*;
import com.fh.admin.vo.user.UserVo;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
import java.util.List;

@Controller
@RequestMapping("user")
public class UserController {

    @Resource(name = "userService")
    private IUserService userService;
    @Resource(name="serviceMenu")
    private IMenuService menuService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @RequestMapping("logout")
    public String logout(){
        //request.getSession().invalidate();
        String sessionId = DistributedSession.getSessionId(request, response);
        RedisUtil.del(KeyUtil.buildUserKey(sessionId),
                      KeyUtil.buildMenuKey(sessionId),
                      KeyUtil.buildMyMenuKey(sessionId));
        return "redirect:/";
    }

    @RequestMapping("skipListJsp")
    public String skipListJsp(){
        return SystemCount.USER_LIST_JSP;
    }

    @RequestMapping("skipUpdatePasswordJsp")
    public String skipUpdatePasswordJsp(){
        return SystemCount.USER_UPDATEPASSWORD_JSP;
    }

    @RequestMapping("selectUserByName")
    @ResponseBody
    public String selectUserByName(User user){
        User userInfo = userService.selectUserByName(user);
        if(userInfo == null){
            return "{\"valid\":true}";
        }
        return "{\"valid\":false}";
    }

    @RequestMapping("login")
    @ResponseBody
    public ServerResponse login(User user,String remFlag){
        if(StringUtils.isEmpty(user.getPassword()) || StringUtils.isEmpty(user.getUserName()) || StringUtils.isEmpty(user.getCode())){
            return ServerResponse.error(ResponseEnum.LOGION_ERROR_PASS_NULL);//为空
        }
        String sessionId = DistributedSession.getSessionId(request, response);
        String imgCode = RedisUtil.get(KeyUtil.buildCodeKey(sessionId));
        if(!user.getCode().equalsIgnoreCase(imgCode)){
            return ServerResponse.error(ResponseEnum.IMGCODE_IS_ERROR);//不一致
        }
        User userInfo = userService.queryUserByName(user.getUserName());
        if(userInfo == null){
            return ServerResponse.error(ResponseEnum.LOGION_ERROR_USER_NULL);//用户不存在
        }
        String newTime = DateUtil.dateToString(new Date(),DateUtil.STRING_Y_M_D);
        String errorTime = DateUtil.dateToString(userInfo.getErrorTime(),DateUtil.STRING_Y_M_D);
        if(userInfo.getErrorCount() >= SystemCount.LOGIN_ERROR_COUNT && newTime.equals(errorTime)){
            return ServerResponse.error(ResponseEnum.LOGION_ERROR_USER_SYS);//用户锁定
        }//Md5Util.md5(Md5Util.md5(user.getPassword())+userInfo.getSalt()))
        if(!userInfo.getPassword().equals(Md5Util.passToMd5Pass(user.getPassword(),userInfo.getSalt()))){//!userInfo.getUserName().equals(user.getUserName()) ||
            if(!newTime.equals(errorTime)) userInfo.setErrorCount(1);
            else userInfo.setErrorCount(userInfo.getErrorCount() + 1);
            if(userInfo.getErrorCount() == SystemCount.LOGIN_ERROR_COUNT){
                //Sendmail.toQQSendmail(userInfo,Sendmail.getRequestPath(request))
                String lockUserContent = PathUtil.getLockUserContent(userInfo.getRealName(), PathUtil.getRequestPath(request));
                MailUtil.sendmail(userInfo.getEmail(),userInfo.getUserName(),"账户被锁定！！！",lockUserContent);
            }
            userInfo.setErrorTime(new Date());
            userService.updateUserByErrorTime(userInfo);
            return ServerResponse.error(ResponseEnum.LOGION_ERROR_NAPW_XXXX);//密码错误
        }
        String time = DateUtil.dateToString(userInfo.getTopLoginTime(),DateUtil.STRING_Y_M_D);
        if(!newTime.equals(time) || time == "") userInfo.setLoginCount(1);
        else userInfo.setLoginCount(userInfo.getLoginCount()+1);

        List<Menu> list = menuService.queryMenuByUserId(userInfo.getId());//菜单
        List<Menu> llt = menuService.queryMenuOrUser(userInfo.getId());//拥有所有权限
        List<Menu> lt = menuService.queryMenuUser();//所有权限
        //request.getSession().setAttribute("llt",llt);
        String llts = JSONObject.toJSONString(llt);
        RedisUtil.set(SystemCount.ALL_MENU_LIST,llts);//,SystemCount.USER_EXPIRE
        //request.getSession().setAttribute("lt",lt);
        String lts = JSONObject.toJSONString(lt);
        RedisUtil.setex(KeyUtil.buildMyMenuKey(sessionId),lts,SystemCount.USER_EXPIRE);
        //request.getSession().setAttribute(SystemCount.MENU_LIST,list);
        String lists = JSONObject.toJSONString(list);
        RedisUtil.setex(KeyUtil.buildMenuKey(sessionId),lists,SystemCount.USER_EXPIRE);
        //request.getSession().setAttribute(SystemCount.LOGIN_USER,userInfo);
        String userInfos = JSONObject.toJSONString(userInfo);
        RedisUtil.setex(KeyUtil.buildUserKey(sessionId),userInfos,SystemCount.USER_EXPIRE);

        userInfo.setErrorCount(0);
        userService.updateUserByTime(userInfo);

        //记住用户名、密码功能(注意：cookie存放密码会存在安全隐患)
        /*if("1".equals(remFlag)){ //"1"表示用户勾选记住密码
             //*String cookieUserName = Utils.encrypt(name);
             String cookiePwd = Utils.encrypt(passWord);
             String loginInfo = cookieUserName+","+cookiePwd;//*
            String loginInfo = user.getUserName()+","+user.getPassword();
            Cookie userCookie=new Cookie("loginInfo",loginInfo);

            userCookie.setMaxAge(30*24*60*60);   //存活期为一个月 30*24*60*60
            userCookie.setPath("/");
            response.addCookie(userCookie);
        }*/

        return ServerResponse.success();
    }

    @RequestMapping("addUser")
    @ResponseBody
    @Log("添加用户")
    public ServerResponse addUser(User user, String[] roleId){
        userService.addUser(user,roleId);
        return ServerResponse.success();
    }

    @RequestMapping("queryUser")
    @ResponseBody
    public DataTableMap queryUser(UserParam user){
        //查询总条数
        Long userCount = userService.queryUserCount(user);
        //分页查询
        List<UserVo> list = userService.queryUserByPage(user);
        DataTableMap map = new DataTableMap(user.getDraw(),userCount,userCount,list);
        return map;
    }

    @RequestMapping("queryUserById")
    @ResponseBody
    public ServerResponse queryUserById(Long id){
        UserVo user = userService.queryUserById(id);
        return ServerResponse.success(user);
    }

    @RequestMapping("updateUser")
    @ResponseBody
    @Log("修改用户")
    public ServerResponse updateUser(User user,String[] roleId){
        userService.updateUser(user,roleId);
        return ServerResponse.success();
    }

    @RequestMapping("updateUserPassword")
    @ResponseBody
    @Log("修改密码")
    public ServerResponse updateUserPassword(UpdatePass up){
        return userService.updateUserPassword(up,request);
    }

    @RequestMapping("updateResetUserPass")
    @ResponseBody
    @Log("重置密码")
    public ServerResponse updateResetUserPass(Long id){
        return userService.updateResetUserPass(id);
    }

    @RequestMapping("forgetPassword")
    @ResponseBody
    public ServerResponse forgetPassword(User user){
        return userService.updateForgetPassword(user);
    }

    @RequestMapping("updateUserStatus")
    @ResponseBody
    @Log("解锁用户")
    public ServerResponse updateUserStatus(Long id){
        return userService.updateUserStatus(id);
    }

    @RequestMapping("deleteUserById")
    @ResponseBody
    @Log("删除用户")
    public ServerResponse deleteUserById(Long id){
        userService.deleteUserById(id);
        return ServerResponse.success();
    }

    @RequestMapping("deleteAll")
    @ResponseBody
    @Log("批量删除用户")
    public ServerResponse deleteAll(String[] str){
        userService.deleteAll(str);
        return ServerResponse.success();
    }

    @RequestMapping("pdfEduce")
    public void pdfEduce(String educeStr,HttpServletResponse response){
        //定义一个字节流数组
        ByteArrayOutputStream byo = new ByteArrayOutputStream();
        //查询到数据集合
        List<UserVo> list = userService.queryUser();
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
        String[] str = educeStr.split(",");
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
        for (UserVo user : list){
            if(ArrayUtils.contains(str,"姓名"))
            table.addCell(FileUtil.createCell(user.getRealName(), keyFont, Element.ALIGN_CENTER));
            if(ArrayUtils.contains(str,"用户"))
            table.addCell(FileUtil.createCell(user.getUserName(), keyFont, Element.ALIGN_CENTER));
            if(ArrayUtils.contains(str,"年龄"))
            table.addCell(FileUtil.createCell(String.valueOf(user.getAge()), keyFont, Element.ALIGN_CENTER));
            if(ArrayUtils.contains(str,"性别"))
            table.addCell(FileUtil.createCell(String.valueOf(user.getSex()), keyFont, Element.ALIGN_CENTER));
            if(ArrayUtils.contains(str,"手机"))
            table.addCell(FileUtil.createCell(user.getPhone(), keyFont, Element.ALIGN_CENTER));
            if(ArrayUtils.contains(str,"邮箱"))
            table.addCell(FileUtil.createCell(user.getEmail(), keyFont, Element.ALIGN_CENTER));
            if(ArrayUtils.contains(str,"薪资"))
            table.addCell(FileUtil.createCell(String.valueOf(user.getPay()), keyFont, Element.ALIGN_CENTER));
            if(ArrayUtils.contains(str,"入职时间"))
            table.addCell(FileUtil.createCell(user.getEntryTime(), keyFont, Element.ALIGN_CENTER));
            if(ArrayUtils.contains(str,"角色"))
            table.addCell(FileUtil.createCell(user.getRoleName(), keyFont, Element.ALIGN_CENTER));
            if(ArrayUtils.contains(str,"用户头像"))
            table.addCell(FileUtil.createCell(user.getImgPath(), keyFont, Element.ALIGN_CENTER));
        }
        //table.addCell(FileUtil.createCell("", keyFont, Element.ALIGN_CENTER));
			/*Image image = Image.getInstance("路径");
			image.setAbsolutePosition(350, 200);
			image.scaleToFit(160, 70);*/
        //table.addCell(Image.getInstance("C:/Users/admin/Pictures/Saved Pictures/10.jpg"));
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

    @RequestMapping("excelEduceUser")
    public void excelEduceUser(UserParam userParam, HttpServletResponse response){
        List<User> list = userService.queryUserByPam(userParam);
        String[] heads = {"真实姓名","用户名","手机","入职时间","薪资","地区"};
        String[] prpos = {"realName","userName","phone","entryTime","pay","regionName"};
        XSSFWorkbook xssDworkbook = ExcelUtil.getXSSDworkbook("用户列表", list, heads, prpos, User.class);
        FileUtil.excelDownload(xssDworkbook,response);
    }

    public Object toStr(Object o){
        if(o == null) return "";
        else return o;
    }
    public int toArrIndex(String s,String[] arr){
        for(int i = 0; i < arr.length; i++){
            if (s.equals(arr[i])) {
                return i;
            }
        }
        return -1;
    }

    @RequestMapping("wordEduce")
    public void wordEduce(String educeStr,HttpServletResponse response) throws IOException, TemplateException {
        Map<String,Object> map = new HashMap();
        List<UserVo> list = userService.queryUser();
        String[] strImgArr = {
                "C:/Users/admin/Pictures/Saved Pictures/dun.jpg",
                "C:/Users/admin/Pictures/Saved Pictures/小贱猫.jpg",
                "C:/Users/admin/Pictures/Saved Pictures/7.jpg"
        };
        List<String> imgList = new ArrayList();
        for(int i=0;i<list.size();i++){
            String imgStr = FileUtil.getImageStr(strImgArr[i]);
            imgList.add(imgStr);
        }
        List<Map<String,Object>> lis = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            Map<String,Object> obj = new HashMap();
            obj.put("realName", list.get(i).getRealName());
            obj.put("userName", list.get(i).getUserName());
            obj.put("age", toStr(list.get(i).getAge()));
            //Integer oo = list.get(i).getSex();
            obj.put("sex", (list.get(i).getSex()==null?"不详":list.get(i).getSex()==1?"男":"女"));
            obj.put("phone", list.get(i).getPhone());
            obj.put("email", list.get(i).getEmail());
            obj.put("pay", toStr(list.get(i).getPay()));
            obj.put("entryTime", list.get(i).getEntryTime());
            obj.put("roleName", toStr(list.get(i).getRoleName()));
            obj.put("imgPath", imgList.get(i));//list.get(i).getImgPath()
            obj.put("img_id", i+1);
            lis.add(obj);
        }
        String[] arr = educeStr.split(",");
        Map mapArr = new HashMap();
        for(int i=0;i<arr.length;i++){
            if("姓名".equals(arr[i])) mapArr.put("a1",arr[i]);
            if("用户".equals(arr[i])) mapArr.put("a2",arr[i]);
            if("年龄".equals(arr[i])) mapArr.put("a3",arr[i]);
            if("性别".equals(arr[i])) mapArr.put("a4",arr[i]);
            if("手机".equals(arr[i])) mapArr.put("a5",arr[i]);
            if("邮箱".equals(arr[i])) mapArr.put("a6",arr[i]);
            if("薪资".equals(arr[i])) mapArr.put("a7",arr[i]);
            if("入职时间".equals(arr[i])) mapArr.put("a8",arr[i]);
            if("角色".equals(arr[i])) mapArr.put("a9",arr[i]);
            if("用户头像".equals(arr[i])) mapArr.put("a0",arr[i]);
        }
        map.put("arr", mapArr);
        map.put("list", lis);
        map.put("imgList", imgList);
        Configuration configuration = new Configuration();
        configuration.setDefaultEncoding("UTF-8");
        configuration.setClassForTemplateLoading(this.getClass(), "/muban");
        Template template = configuration.getTemplate("tupian.ftl");
        File file = new File("D://word测试.docx");
        FileOutputStream fos = new FileOutputStream(file);
        OutputStreamWriter osw = new OutputStreamWriter(fos,"UTF-8");
        template.process(map, osw);
        //关流
        osw.close();
        FileUtil.downloadFile(request, response, file.getPath(), file.getName());
        file.delete();
    }

}
