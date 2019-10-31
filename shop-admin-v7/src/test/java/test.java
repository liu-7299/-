import com.aliyun.oss.OSSClient;
import com.fh.admin.biz.region.IRegionService;
import com.fh.admin.biz.user.IUserService;
import com.fh.admin.commons.ServerResponse;
import com.fh.admin.po.Region;
import com.fh.admin.util.FileUtil;
import com.fh.admin.util.OSSUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.*;
import java.util.List;
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-common.xml"})
public class test extends AbstractJUnit4SpringContextTests {

    @Autowired
    private IRegionService regionService;

    @Test
    public void test1(){
        /*ServerResponse serverResponse = regionService.queryRegionsById(120000);
        List<Region> data = (List<Region>) serverResponse.getData();
        System.out.println(data.get(0).getName());*/
        /*Long a = 120000l;
        Integer b = 120000;
        Long c = Long.valueOf(b);
        System.out.println(a.equals(c));*/
    }

    private static final Logger LOG = LoggerFactory.getLogger(OSSUtil.class);

    private OSSUtil ossunit = null;

    private OSSClient client = null;

    private String bucketName = "liu-7299";//根据

    /*@Before
    public void initUnit(){
        ossunit = new OSSUtil();
        client = OSSUtil.getOSSClient();
    }*/

    @Test
    public void shangchuanOSS(){
        /*String flilePathName = "C:\\Users\\admin\\Pictures\\Saved Pictures\\12.gif";//你的文件地址
        File file = new File(flilePathName);
        InputStream is;
        try {
            is = new FileInputStream(file);
            String fileName = OSSUtil.uploadObject2OSS(is,file.length(),file.getName());
            System.out.println(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/
    }
    @Test
    public void xiazaiOSS(){
        //OSSUtil.deleteFile("");//文件在oss服务器上的全路径
        /*String path = "gen/img/5164437c-3491-469a-916d-b5080c320343.gif";
        InputStream is = OSSUtil.getOSS2InputStream(path);
        String gethz = FileUtil.gethz(path);
        String s = UUID.randomUUID().toString();
        BufferedInputStream in=null;
        BufferedOutputStream out=null;
        in=new BufferedInputStream(is);
        try {
            out=new BufferedOutputStream(new FileOutputStream("D:\\"+s+gethz));
            byte[] b = new byte[1024];
            try {
                int len = in.read(b);
                while (len != -1) {
                    out.write(b, 0, len);
                    len = in.read(b);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                if(null != out){
                    out.close();
                }
                if(null != in){
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
    }

    @Test
    public void sss(){

    }

}
