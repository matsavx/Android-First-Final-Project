package com.matsavx.firstfinal.data.source

import com.matsavx.firstfinal.data.Product

interface ProductDataSource {

    fun getProduct(productId: Int): Product?

    fun getProducts(): List<Product>
}