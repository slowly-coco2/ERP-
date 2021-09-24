package com.bdqn.bus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bus")
public class BusinessController {

    @RequestMapping("/toCustomerManager")
    public String toCustomerManager(){
        return "business/customer/customerManager";
    }

    @RequestMapping("/toProviderManager")
    public String toProviderManager(){
        return "business/customer/providerManager";
    }


    @RequestMapping("/toGoodsTypeManager")
    public String toGoodsTypeManager(){
        return "business/goodsType/goodsTypeManager";
    }

    @RequestMapping("/toGoodsTypeRight")
    public String toGoodsTypeRight(){
        return "business/goodsType/right";
    }

    @RequestMapping("/toGoodsTypeLeft")
    public String toGoodsTypeLeft(){
        return "business/goodsType/left";
    }


    @RequestMapping("/toGoodsRight")
    public String toGoodsRight(){
        return "business/goods/right";
    }

    @RequestMapping("/toGoodsLeft")
    public String toGoodsLeft(){
        return "business/goods/left";
    }


    @RequestMapping("/toGoodsManager")
    public String toGoodsManager(){
        return "business/goods/goodsManager";
    }

    @RequestMapping("/toLeaveBillManager")
    public String toLeaveBillManager(){
        return "business/leavebill/leaveBillManager";
    }

}
