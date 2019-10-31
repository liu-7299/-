package com.fh.shop.api.cart.biz;

import com.alibaba.fastjson.JSONObject;
import com.fh.shop.api.cart.vo.Cart;
import com.fh.shop.api.cart.vo.CartItem;
import com.fh.shop.api.commodity.mapper.ICommodityMapper;
import com.fh.shop.api.commodity.po.Commodity;
import com.fh.shop.api.commons.ResponseEnum;
import com.fh.shop.api.commons.ServerResponse;
import com.fh.shop.api.util.BigDecimalUtil;
import com.fh.shop.api.util.KeyUtil;
import com.fh.shop.api.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service("cartService")
@Transactional(rollbackFor = Exception.class)
public class ICartServiceImpl implements ICartService {

    @Autowired
    private ICommodityMapper commodityMapper;

    @Override
    public ServerResponse addCart(Long id, Long commId, Long count) {
        Commodity commodity = commodityMapper.selectById(commId);
        if(null == commodity){//商品不存在
            return ServerResponse.error(ResponseEnum.COMM_IS_NO_EXIET);
        }
        if(commodity.getStatus() != 1){//商品未上架
            return ServerResponse.error(ResponseEnum.COMM_IS_NO_PUT);
        }
        //当前商品小计
        BigDecimal zprice = BigDecimalUtil.mul(commodity.getPrice().toString(),String.valueOf(count));
        //从redis中取出json串
        String cartStr = RedisUtil.hget("Cart", KeyUtil.getCartMemberId(String.valueOf(id)));
        Cart cart = null;//购物车
        if(StringUtils.isEmpty(cartStr)) cart = new Cart();//用户没有购物车
        if(StringUtils.isNotEmpty(cartStr)) cart = JSONObject.parseObject(cartStr, Cart.class);//用户有购物车
        //取出商品集合 从商品集合取出查看当前商品是否存在
        List<CartItem> list = cart.getList();
        CartItem cartItem = getCartItem(commId, list);
        if(cartItem != null){//购物车中有该商品 //list.remove(cartItem);//数组中移除
            getCartItem(commodity, cartItem, count, zprice); //list.add(cartItem);//修改后添加该商品
        }
        if(cartItem == null){//购物车中没有该商品
            cartItem = new CartItem();//没有则new商品对象并赋值
            getCartItem(commodity, cartItem, count, zprice);
            list.add(cartItem);//添加到用户购物车
        }
        //购物车商品集合中商品至少有一个
        if(cartItem.getCount() < 1) return ServerResponse.error(ResponseEnum.COMM_IS_NO_ERROR);
        //将购物车数据修改
        cart.setCount(cart.getCount() + count);//原总数量加当前数量
        BigDecimal add = BigDecimalUtil.add(cart.getPrice(), zprice.toString());
        cart.setPrice(add.toString());//原总计加当前小计
        cart.setList(list);//将购物车中集合重新赋值
        cartStr = JSONObject.toJSONString(cart);//转为json串
        RedisUtil.hset("Cart", KeyUtil.getCartMemberId(String.valueOf(id)), cartStr);
        return ServerResponse.success();
    }

    @Override
    @Transactional(readOnly = true)
    public ServerResponse queryCart(Long id) {
        String cartStr = RedisUtil.hget("Cart", KeyUtil.getCartMemberId(String.valueOf(id)));
        if(StringUtils.isEmpty(cartStr)){
            return ServerResponse.error(ResponseEnum.CART_IS_NULL);
        }
        Cart cart = JSONObject.parseObject(cartStr, Cart.class);
        return ServerResponse.success(cart);
    }

    @Override
    public ServerResponse deleteCart(Long id, String[] commId) {
        if(commId.length == 0) return ServerResponse.error();
        String cartStr = RedisUtil.hget("Cart", KeyUtil.getCartMemberId(String.valueOf(id)));
        Cart cart = JSONObject.parseObject(cartStr, Cart.class);
        List<CartItem> list = cart.getList();
        for (String s : commId) {
            CartItem cartItem = getCartItem(Long.valueOf(s), list);
            if(null != cartItem){
                cart.setCount(cart.getCount() - cartItem.getCount());//减去当前商品数量
                BigDecimal add = BigDecimalUtil.add(cart.getPrice(), "-" + cartItem.getZprice());
                cart.setPrice(String.valueOf(add));
                list.remove(cartItem);
            }
        }
        cart.setList(list);
        //转为json串
        cartStr = JSONObject.toJSONString(cart);
        RedisUtil.hset("Cart", KeyUtil.getCartMemberId(String.valueOf(id)), cartStr);
        return ServerResponse.success();
    }

    private static CartItem getCartItem(Long commId, List<CartItem> list){
        for (CartItem item : list) {
            if(commId == item.getId()){
                return item;
            }
        }
        return null;
    }
    private void getCartItem(Commodity commodity, CartItem cartItem, Long count, BigDecimal zprice) {
        count = cartItem.getCount() + count;//有商品则商品原数量加当前数量
        BigDecimal newprice = BigDecimalUtil.add(cartItem.getZprice(),zprice.toString());
        cartItem.setId(commodity.getId());//有商品则商品原小计加当前小计
        cartItem.setName(commodity.getName());
        cartItem.setPrice(commodity.getPrice().toString());
        cartItem.setImgPath(commodity.getImgPath());
        cartItem.setCount(count);
        cartItem.setZprice(newprice.toString());
    }

}
