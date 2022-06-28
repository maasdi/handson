package com.handson.main;

import com.handson.model.Product;
import com.handson.model.Seller;

import java.math.BigDecimal;
import java.util.*;

public class ProductGrouping {

    public static void main(String[] args) {
        // Given list of products
        List<Product> productList = new ArrayList<>();
        Seller sellerA = new Seller(1, "Seller A");
        productList.add(new Product(1, "iPhone 13 Pro", new BigDecimal(5000), sellerA));
        productList.add(new Product(2, "Adidas T-Shirt", new BigDecimal(130), new Seller(2, "Adidas Official Seller")));
        productList.add(new Product(3, "iPhone X", new BigDecimal(2500), sellerA));
        productList.add(new Product(4, "Xiaomi OCOOKER Dual Side", new BigDecimal(175), new Seller(3, "Xiomi Malaysia")));

        List<SellerProduct> sellerProducts = new ArrayList<>();

        for (Product prod : productList) {

            Optional<SellerProduct> sellerProductOptional = sellerProducts.stream().filter(x -> x.seller.getNo() == prod.getSeller().getNo()).findFirst();

            if (sellerProductOptional.isPresent() == true) {
                sellerProductOptional.get().addProduct(prod);
            }
            else {
                SellerProduct sellerProduct = new SellerProduct(prod.getSeller());
                sellerProduct.addProduct(prod);
                sellerProducts.add(sellerProduct);
            }
        }

        sellerProducts.stream().sorted((x, y) -> x.seller.getNo() - y.seller.getNo())
          .forEach(x -> {
              System.out.println(x.seller.getName());
              x.products.forEach(y -> {
                  System.out.println(" - " + y.getName());
              });
          });


        // Do grouping and display the products base on its sellerNo
        // Sample Result:
        /**
         * Seller A
         * 	- iPhone 13 Pro
         * 	- iPhone X
         * Adidas Official Seller
         * 	- Adidas T-Shirt
         * Xiomi Malaysia
         * 	- Xiaomi OCOOKER Dual Side
         */
    }

    static class SellerProduct {

        private Seller seller;

        private PriorityQueue<Product> products;

        public SellerProduct(Seller seller) {
            this.seller = seller;
            this.products = new PriorityQueue<>((x, y) -> x.getNo() - y.getNo());
        }

        public void addProduct(Product product) {
            this.products.add(product);
        }

        public PriorityQueue<Product> getProducts() {
            return this.products;
        }
    }

}
