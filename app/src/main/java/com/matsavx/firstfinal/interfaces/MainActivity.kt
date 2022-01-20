package com.matsavx.firstfinal.interfaces

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.request.RequestOptions
import com.matsavx.firstfinal.R
import com.matsavx.firstfinal.data.source.ProductDataSource
import com.matsavx.firstfinal.data.source.ProductRemoteDataSource
import com.matsavx.firstfinal.fragmentfactory.ProductFragmentFactory
import kotlinx.android.synthetic.main.activity_main.*
import com.matsavx.firstfinal.util.SharedPreferenceUtil
import kotlinx.android.synthetic.main.toolbar.*


class MainActivity : AppCompatActivity(), UICommunicationListener {

    private lateinit var requestOptions: RequestOptions
    private lateinit var productDataSource: ProductDataSource
    override fun onCreate(savedInstanceState: Bundle?) {
        initDependencies()
        supportFragmentManager.fragmentFactory = ProductFragmentFactory(
            requestOptions,
            productDataSource
        )
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        cartCount();
        init()
    }

    fun cartCount() {
        val arrayList = SharedPreferenceUtil.getArrayList(this.applicationContext)
        if (arrayList.size > 0) {
            textViewCount.visibility = View.VISIBLE
            textViewCount.text = arrayList.size.toString()
        } else {
            textViewCount.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        cartCount()
    }
    
    private fun init() {
        if (supportFragmentManager.fragments.size == 0) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ProductListFragment::class.java, null)
                .commit()
        }

        relativeLayoutCart.setOnClickListener(View.OnClickListener {
            val intent: Intent = Intent(this@MainActivity, CartActivity::class.java)
            startActivity(intent)
        })
    }

    private fun initDependencies() {
        if (!::requestOptions.isInitialized) {
            requestOptions = RequestOptions
                .placeholderOf(R.drawable.default_image)
                .error(R.drawable.default_image)
        }
        if (!::productDataSource.isInitialized) {
            productDataSource = ProductRemoteDataSource
        }
    }

    override fun loading(isLoading: Boolean) {
        if (isLoading)
            progress_bar.visibility = View.VISIBLE
        else
            progress_bar.visibility = View.INVISIBLE
    }


}
