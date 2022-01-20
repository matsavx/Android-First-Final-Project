package com.matsavx.firstfinal.data.source

import com.matsavx.firstfinal.data.ProductData
import com.matsavx.firstfinal.data.Product

object ProductRemoteDataSource : ProductDataSource {

    private var PRODUCT_REMOTE_DATA = LinkedHashMap<Int, Product>(ProductData.products.size)

    init {
        for (product in ProductData.products) {
            addProduct(product)
        }
    }

    override fun getProducts(): List<Product> {
        return ArrayList(PRODUCT_REMOTE_DATA.values)
    }

    override fun getProduct(productId: Int): Product? {
        return PRODUCT_REMOTE_DATA[productId]
    }

    private fun addProduct(product: Product) {
        PRODUCT_REMOTE_DATA.put(product.id, product)
    }

}