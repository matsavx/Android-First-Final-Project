package com.matsavx.firstfinal.fragmentfactory

import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.request.RequestOptions
import com.matsavx.firstfinal.data.source.ProductDataSource
import com.matsavx.firstfinal.interfaces.ProductDetailFragment
import com.matsavx.firstfinal.interfaces.ProductListFragment

class ProductFragmentFactory(
    private val requestOptions: RequestOptions? = null,
    private val productDataSource: ProductDataSource? = null
) : FragmentFactory(){

    override fun instantiate(classLoader: ClassLoader, className: String) =

        when(className){

            ProductListFragment::class.java.name -> {
                if (productDataSource != null) {
                    ProductListFragment(productDataSource)
                } else {
                    super.instantiate(classLoader, className)
                }
            }

            ProductDetailFragment::class.java.name -> {
                if(requestOptions != null
                    && productDataSource != null){
                    ProductDetailFragment(
                        requestOptions,
                        productDataSource
                    )
                }
                else{
                    super.instantiate(classLoader, className)
                }
            }
            else -> {
                super.instantiate(classLoader, className)
            }
        }

}













