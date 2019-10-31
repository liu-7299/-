package com.fh.shop.api.wxpay.biz;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fh.shop.api.commons.ServerResponse;
import com.fh.shop.api.order.mapper.IOrderMapper;
import com.fh.shop.api.order.po.Order;
import com.fh.shop.api.paylog.mapper.IPayLogMapper;
import com.fh.shop.api.paylog.po.PayLog;
import com.fh.shop.api.util.*;
import com.fh.shop.api.wxpayutil.util.MyConfig;
import com.fh.shop.api.wxpayutil.util.WXPay;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service("wxPayService")
public class IWXPayServiceImpl implements IWXPayService {

    @Autowired
    private IPayLogMapper payLogMapper;

    @Autowired
    private IOrderMapper orderMapper;

    @Override
    public ServerResponse createCodeUrl(Long id) {
        String payLogStr = RedisUtil.get(KeyUtil.getPayLogId(id.toString()));
        if(StringUtils.isEmpty(payLogStr)) return ServerResponse.error();
        PayLog payLog = JSONObject.parseObject(payLogStr, PayLog.class);
        int i = BigDecimalUtil.mul(String.valueOf(payLog.getPayMoney()),"100").intValue();//金额
        MyConfig config = new MyConfig();
        WXPay wxpay = null;
        try {
            wxpay = new WXPay(config);
            Map<String, String> data = new HashMap<>();
            data.put("body", "测试-A");
            String s = DateUtil.dateToString(DateUtils.addMinutes(new Date(), 5), DateUtil.YYYYMMDDHHMMSS);
            data.put("time_expire", s);
            data.put("out_trade_no", payLog.getOutPayId());
            data.put("total_fee", i + "");
            data.put("spbill_create_ip", "123.12.12.123");
            data.put("notify_url", "http://www.example.com/wxpayutil/notify");
            data.put("trade_type", "NATIVE");  // 此处指定为扫码支付
            Map<String, String> resp = wxpay.unifiedOrder(data);
            String return_code = resp.get("return_code");
            String return_msg = resp.get("return_msg");
            if(!return_code.equalsIgnoreCase("SUCCESS")){
                return ServerResponse.error(3000,return_msg);
            }
            String result_code = resp.get("result_code");
            String err_code_des = resp.get("err_code_des");
            if(!result_code.equalsIgnoreCase("SUCCESS")){
                return ServerResponse.error(3001,err_code_des);
            }
            String code_url = resp.get("code_url");
            return ServerResponse.success(code_url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ServerResponse queryCodeUrl(Long id) {
        String payLogStr = RedisUtil.get(KeyUtil.getPayLogId(id.toString()));
        if(StringUtils.isEmpty(payLogStr)) return ServerResponse.error();
        PayLog payLog = JSONObject.parseObject(payLogStr, PayLog.class);
        MyConfig config = new MyConfig();
        WXPay wxpay = null;
        try {
            wxpay = new WXPay(config);
            Map<String, String> data = new HashMap<>();
            data.put("out_trade_no", payLog.getOutPayId());
            int i = 0;
            while (true){
                Map<String, String> resp = wxpay.orderQuery(data);
                String return_code = resp.get("return_code");
                String return_msg = resp.get("return_msg");
                if(!return_code.equalsIgnoreCase("SUCCESS")){
                    return ServerResponse.error(3002,return_msg);
                }
                String result_code = resp.get("result_code");
                String err_code_des = resp.get("err_code_des");
                if(!result_code.equalsIgnoreCase("SUCCESS")){
                    return ServerResponse.error(3003,err_code_des);
                }
                String trade_state = resp.get("trade_state");
                String trade_state_desc = resp.get("trade_state_desc");
                if(trade_state.equalsIgnoreCase("SUCCESS")){
                    Order order = new Order();
                    order.setOrderStatus(SystemCount.ORDER_B);//订单待发货
                    order.setPayTime(new Date());//支付时间
                    UpdateWrapper<Order> wrapper = new UpdateWrapper<>();
                    wrapper.eq("id",payLog.getOrderId());//订单id为当前支付订单id的修改
                    orderMapper.update(order,wrapper);
                    payLog.setMakeId("aaa");//交易流水号
                    payLog.setPayTime(new Date());//支付时间
                    payLog.setPayStatus(20);//支付状态
                    payLogMapper.insert(payLog);//插入数据库
                    RedisUtil.del(KeyUtil.getPayLogId(String.valueOf(id)));//删除redis key
                    return ServerResponse.success(trade_state_desc);
                }
                Thread.sleep(1000 * 2);
                if(i++ > 100){
                    return ServerResponse.error(3004,"支付超时");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error(3005, "支付错误");
        }
    }

}
