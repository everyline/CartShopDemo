package com.hyyt.cartshopdemo.bean;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Administrator on 2016/10/21.
 */

public class ShopEntity {

    private boolean success;

    private List<ShoppingCartBean> shoppingCart;

    public static ShopEntity objectFromData(String str) {

        return new Gson().fromJson(str, ShopEntity.class);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<ShoppingCartBean> getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(List<ShoppingCartBean> shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public static class ShoppingCartBean {

        private ShopInfoBean shopInfo;


        private List<ProductListBean> productList;

        public static ShoppingCartBean objectFromData(String str) {

            return new Gson().fromJson(str, ShoppingCartBean.class);
        }

        public ShopInfoBean getShopInfo() {
            return shopInfo;
        }

        public void setShopInfo(ShopInfoBean shopInfo) {
            this.shopInfo = shopInfo;
        }

        public List<ProductListBean> getProductList() {
            return productList;
        }

        public void setProductList(List<ProductListBean> productList) {
            this.productList = productList;
        }

        public static class ShopInfoBean {
            private String shopName;
            private String shopInfoId;
            private boolean isChoosed;

            public boolean isChoosed() {
                return isChoosed;
            }

            public void setChoosed(boolean choosed) {
                isChoosed = choosed;
            }

            public static ShopInfoBean objectFromData(String str) {

                return new Gson().fromJson(str, ShopInfoBean.class);
            }

            public String getShopName() {
                return shopName;
            }

            public void setShopName(String shopName) {
                this.shopName = shopName;
            }

            public String getShopInfoId() {
                return shopInfoId;
            }

            public void setShopInfoId(String shopInfoId) {
                this.shopInfoId = shopInfoId;
            }
            public void toggle() {
                this.isChoosed = !this.isChoosed;
            }
        }

        public static class ProductListBean {
            private int productTypeId;
            private double salesPrice;
            private String productFullName;
            private int storeNumber;
            private int shopCartID;
            private int quantity;
            private int isPutSale;
            private String logoImg;
            private int productId;
            private boolean isChoosed;

            public boolean isChoosed() {
                return isChoosed;
            }

            public void setChoosed(boolean choosed) {
                isChoosed = choosed;
            }

            public static ProductListBean objectFromData(String str) {

                return new Gson().fromJson(str, ProductListBean.class);
            }

            public int getProductTypeId() {
                return productTypeId;
            }

            public void setProductTypeId(int productTypeId) {
                this.productTypeId = productTypeId;
            }

            public double getSalesPrice() {
                return salesPrice;
            }

            public void setSalesPrice(double salesPrice) {
                this.salesPrice = salesPrice;
            }

            public String getProductFullName() {
                return productFullName;
            }

            public void setProductFullName(String productFullName) {
                this.productFullName = productFullName;
            }

            public int getStoreNumber() {
                return storeNumber;
            }

            public void setStoreNumber(int storeNumber) {
                this.storeNumber = storeNumber;
            }

            public int getShopCartID() {
                return shopCartID;
            }

            public void setShopCartID(int shopCartID) {
                this.shopCartID = shopCartID;
            }

            public int getQuantity() {
                return quantity;
            }

            public void setQuantity(int quantity) {
                this.quantity = quantity;
            }

            public int getIsPutSale() {
                return isPutSale;
            }

            public void setIsPutSale(int isPutSale) {
                this.isPutSale = isPutSale;
            }

            public String getLogoImg() {
                return logoImg;
            }

            public void setLogoImg(String logoImg) {
                this.logoImg = logoImg;
            }

            public int getProductId() {
                return productId;
            }

            public void setProductId(int productId) {
                this.productId = productId;
            }
            public void toggle() {
                this.isChoosed = !this.isChoosed;
            }
        }
    }
}
