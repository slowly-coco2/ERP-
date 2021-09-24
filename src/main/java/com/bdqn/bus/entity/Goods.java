package com.bdqn.bus.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author KazuGin
 * @since 2021-09-23
 */
@TableName("bus_goods")
public class Goods implements Serializable {

    private static final long serialVersionUID=1L;

    //供应商名称
    @TableField(exist = false)
    private String providerName;

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    /**
     * 商品编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 商品名称
     */
    private String goodsname;

    /**
     * 商品产地
     */
    private String produceplace;

    /**
     * 商品规格
     */
    private String size;

    /**
     * 包装
     */
    private String goodspackage;

    /**
     * 生产批号
     */
    private String productcode;

    /**
     * 批准文号
     */
    private String promitcode;

    /**
     * 商品描述
     */
    private String description;

    /**
     * 商品售价
     */
    private Double price;

    /**
     * 商品数量
     */
    private Integer number;

    /**
     * 预警数量
     */
    private Integer dangernum;

    /**
     * 商品图片
     */
    private String goodsimg;

    /**
     * 商品分类编号
     */
    private Integer typeid;

    /**
     * 供应商编号
     */
    private Integer providerid;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public String getProduceplace() {
        return produceplace;
    }

    public void setProduceplace(String produceplace) {
        this.produceplace = produceplace;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getGoodspackage() {
        return goodspackage;
    }

    public void setGoodspackage(String goodspackage) {
        this.goodspackage = goodspackage;
    }

    public String getProductcode() {
        return productcode;
    }

    public void setProductcode(String productcode) {
        this.productcode = productcode;
    }

    public String getPromitcode() {
        return promitcode;
    }

    public void setPromitcode(String promitcode) {
        this.promitcode = promitcode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getDangernum() {
        return dangernum;
    }

    public void setDangernum(Integer dangernum) {
        this.dangernum = dangernum;
    }

    public String getGoodsimg() {
        return goodsimg;
    }

    public void setGoodsimg(String goodsimg) {
        this.goodsimg = goodsimg;
    }

    public Integer getTypeid() {
        return typeid;
    }

    public void setTypeid(Integer typeid) {
        this.typeid = typeid;
    }

    public Integer getProviderid() {
        return providerid;
    }

    public void setProviderid(Integer providerid) {
        this.providerid = providerid;
    }

    @Override
    public String toString() {
        return "Goods{" +
        "id=" + id +
        ", goodsname=" + goodsname +
        ", produceplace=" + produceplace +
        ", size=" + size +
        ", goodspackage=" + goodspackage +
        ", productcode=" + productcode +
        ", promitcode=" + promitcode +
        ", description=" + description +
        ", price=" + price +
        ", number=" + number +
        ", dangernum=" + dangernum +
        ", goodsimg=" + goodsimg +
        ", typeid=" + typeid +
        ", providerid=" + providerid +
        "}";
    }
}
