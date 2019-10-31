package com.fh.shop.api.order.biz;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.fh.shop.api.cart.vo.Cart;
import com.fh.shop.api.cart.vo.CartItem;
import com.fh.shop.api.commodity.mapper.ICommodityMapper;
import com.fh.shop.api.commodity.po.Commodity;
import com.fh.shop.api.commons.ResponseEnum;
import com.fh.shop.api.commons.ServerResponse;
import com.fh.shop.api.order.mapper.IOrderItemMapper;
import com.fh.shop.api.order.mapper.IOrderMapper;
import com.fh.shop.api.order.param.OrderParam;
import com.fh.shop.api.order.po.Order;
import com.fh.shop.api.order.po.OrderItem;
import com.fh.shop.api.paylog.po.PayLog;
import com.fh.shop.api.site.mapper.ISiteMapper;
import com.fh.shop.api.site.po.Site;
import com.fh.shop.api.util.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service("orderService")
@Transactional(rollbackFor = Exception.class)
public class IOrderServiceImpl implements IOrderService {

    @Autowired
    private IOrderMapper orderMapper;

    @Autowired
    private IOrderItemMapper orderItemMapper;

    @Autowired
    private ISiteMapper siteMapper;

    @Autowired
    private ICommodityMapper commodityMapper;

    @Override
    public ServerResponse addOrder(Long id, OrderParam orderParam) {
        //判断该用户购物车中是否有商品
        String cartStr = RedisUtil.hget("Cart", KeyUtil.getCartMemberId(String.valueOf(id)));
        if(null == cartStr) return ServerResponse.error(ResponseEnum.CART_IS_NULL);
        Cart cart = JSONObject.parseObject(cartStr, Cart.class);//购物车
        List<CartItem> list = cart.getList();//商品列表
        if(list.size() == 0) return ServerResponse.error(ResponseEnum.CART_IS_NULL);
        Site site = siteMapper.selectById(orderParam.getSiteId());//地址信息
        Order order = new Order();//订单对象
        String orderId = IdUtil.getId();//订单id(雪花算法)
        order.setId(orderId);//订单id
        order.setMemberId(id);//用户id
        order.setCreateTime(new Date());//创建时间
        order.setPayWay(orderParam.getPayWay());//支付方式
        order.setOrderStatus(SystemCount.ORDER_A);//订单状态
        //收货信息
        order.setConsigneeName(site.getName());//名字
        order.setConsigneeRegion(site.getInfo());//地址
        order.setConsigneePhone(site.getPhone());//手机
        order.setExpressWay(orderParam.getPayWay());//快递方式
        order.setExpressFee(new BigDecimal(12));//快递费
        order.setInvoice(orderParam.getInvoice());//是否需要发票
        List<OrderItem> lt = new ArrayList<>();//订单明细集合
        BigDecimal newPayPrice = new BigDecimal(cart.getPrice());//订单总价
        Long newCount = cart.getCount();//订单总数量
        for (int i=list.size()-1;i>=0;i--) {
            CartItem cartItem = list.get(i);//购物车商品
            //订单详情
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(orderId);//订单id
            orderItem.setItemId(cartItem.getId());//商品id
            orderItem.setMemberId(id);//会员id
            orderItem.setItemName(cartItem.getName());//商品名
            orderItem.setPrice(new BigDecimal(cartItem.getPrice()));//商品单价
            orderItem.setCount(cartItem.getCount());//商品数量
            orderItem.setAllPrice(BigDecimalUtil.mul(cartItem.getPrice(),String.valueOf(cartItem.getCount())));//商品小计 单价*数量
            Commodity commodity = commodityMapper.selectById(cartItem.getId());//商品库存
            if(commodity.getStock() >= cartItem.getCount()){//剩余库存大于等于购买数量
                //减库存
                Long mysqlRowStatus = commodityMapper.updateCommStatus(cartItem.getCount(),cartItem.getId());
                if(mysqlRowStatus == 0){//数据库库存不够未修改
                    //减去当前商品小计和数量
                    newPayPrice = BigDecimalUtil.add(newPayPrice.toString(), "-" + orderItem.getAllPrice().toString());
                    newCount = newCount - orderItem.getCount();
                }else{//库存够
                    lt.add(orderItem);//放入集合批量添加
                    list.remove(cartItem);//移除当前购物车商品，剩余返回到前台
                }
            }else{//减去当前商品小计和数量
                newPayPrice = BigDecimalUtil.add(newPayPrice.toString(), "-" + orderItem.getAllPrice().toString());
                newCount = newCount - orderItem.getCount();
            }
        }
        if(newCount == 0){//空订单
            return ServerResponse.error(ResponseEnum.ALL_COMM_IS_NULL);
        }
        order.setPayPrice(newPayPrice);
        order.setCommCount(newCount);
        orderMapper.insert(order);//添加订单
        orderItemMapper.insertAll(lt);//添加所有订单明细
        //支付日志记录表
        PayLog payLog = new PayLog();
        String payId = IdUtil.getId();//支付id(雪花算法)
        payLog.setOutPayId(payId);
        payLog.setMemberId(id);//用户id
        payLog.setOrderId(orderId);//订单id
        //payLog.setMakeId("");//交易流水号 调用微信接口返回的值
        payLog.setCreateTime(new Date());
        //payLog.setPayTime(new Date());//支付时间
        payLog.setPayMoney(newPayPrice);//支付金额
        payLog.setPayWay(orderParam.getPayWay());//支付方式
        payLog.setPayStatus(10);//支付状态
        String payLogStr = JSONObject.toJSONString(payLog);//将日志插入到redis
        RedisUtil.set(KeyUtil.getPayLogId(String.valueOf(id)),payLogStr);
        //清空购物车
        RedisUtil.hdel("Cart", KeyUtil.getCartMemberId(String.valueOf(id)));
        //将库存不够商品，订单号和实际金额返还到前台
        Map<String,Object> map = new HashMap<>();
        map.put("list", list);
        map.put("orderId", orderId);
        map.put("payPrice", String.valueOf(order.getPayPrice()));
        return ServerResponse.success(map);
    }

}
