package com.menethil.tinymall.admin.dao;

import com.menethil.tinymall.db.domain.TinymallGoods;
import com.menethil.tinymall.db.domain.TinymallGoodsAttribute;
import com.menethil.tinymall.db.domain.TinymallGoodsSpecification;
import com.menethil.tinymall.db.domain.TinymallProduct;

public class GoodsAllinone {
    TinymallGoods goods;
    TinymallGoodsSpecification[] specifications;
    TinymallGoodsAttribute[] attributes;
    // 这里采用 Product 再转换到 TinymallProduct
    TinymallProduct[] products;

    public TinymallGoods getGoods() {
        return goods;
    }

    public TinymallProduct[] getProducts() {
        return products;
    }

    public void setProducts(TinymallProduct[] products) {
        this.products = products;
    }

    public void setGoods(TinymallGoods goods) {
        this.goods = goods;
    }

    public TinymallGoodsSpecification[] getSpecifications() {
        return specifications;
    }

    public void setSpecifications(TinymallGoodsSpecification[] specifications) {
        this.specifications = specifications;
    }

    public TinymallGoodsAttribute[] getAttributes() {
        return attributes;
    }

    public void setAttributes(TinymallGoodsAttribute[] attributes) {
        this.attributes = attributes;
    }

}
