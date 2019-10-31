package com.fh.shop.api.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.api.commodity.mapper.ICommodityMapper;
import com.fh.shop.api.commodity.po.Commodity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@EnableScheduling
@Component
public class TimingSendEmail {
    /**
     * CRON表达式    含义
     * "0 0 12 * * ?"    每天中午十二点触发
     * "0 15 10 ? * *"    每天早上10：15触发
     * "0 15 10 * * ?"    每天早上10：15触发
     * "0 15 10 * * ? *"    每天早上10：15触发
     * "0 15 10 * * ? 2005"    2005年的每天早上10：15触发
     * "0 * 14 * * ?"    每天从下午2点开始到2点59分每分钟一次触发
     * "0 0/5 14 * * ?"    每天从下午2点开始到2：55分结束每5分钟一次触发
     * "0 0/5 14,18 * * ?"    每天的下午2点至2：55和6点至6点55分两个时间段内每5分钟一次触发
     * "0 0-5 14 * * ?"    每天14:00至14:05每分钟一次触发
     * "0 10,44 14 ? 3 WED"    三月的每周三的14：10和14：44触发
     * "0 15 10 ? * MON-FRI"    每个周一、周二、周三、周四、周五的10：15触发
     */
    //@Scheduled(cron="0 0 1 * * ?")   //每天凌晨一点执行一次
    //@Scheduled(cron="0 0 2 * * ?")   //每天凌晨两点执行一次
    //@Scheduled(cron = "0 0 3 1 * ?")   //每个月1号凌晨三点执行一次
    //@Scheduled(cron="*/10 * * * * ?")   //每1分钟执行一次
    private void test(){
        System.out.println("当前时间：" + new Date().getTime());
    }

    @Autowired
    private ICommodityMapper commodityMapper;

    @Scheduled(cron="0 0 2 * * ?")//0  0 24 * * ?
    private void sendEmail(){
        QueryWrapper<Commodity> wrapper = new QueryWrapper<>();
        wrapper.lt("stock",6);//小于
        List<Commodity> comms = commodityMapper.selectList(wrapper);
        if(null == comms){
            System.out.println("===========");
            return;
        }
        StringBuilder str = new StringBuilder();
        for (Commodity comm : comms) {
            str.append("商品名：");
            str.append(comm.getName());
            str.append(" 商品价格：");
            str.append(comm.getPrice());
            str.append(" 商品库存：");
            str.append(comm.getStock());
            str.append("<br/>");
        }
        System.out.println("库存不足，自动发送邮件");//532028476
        EmailUtil.sendmail("532028476@qq.com","别名","库存不足",str.toString());
    }

}
