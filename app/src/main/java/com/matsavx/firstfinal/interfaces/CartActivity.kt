package com.matsavx.firstfinal.interfaces

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.matsavx.firstfinal.R
import com.matsavx.firstfinal.data.Product
import com.matsavx.firstfinal.interfaces.adapter.CartProductListAdapter
import com.matsavx.firstfinal.util.SharedPreferenceUtil
import com.matsavx.firstfinal.util.TopSpacingItemDecoration
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.fragment_product_list.*

class CartActivity : AppCompatActivity() {

    lateinit var listAdapter: CartProductListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        init()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@CartActivity)
            removeItemDecoration(TopSpacingItemDecoration(30))
            addItemDecoration(TopSpacingItemDecoration(30))
            listAdapter = CartProductListAdapter()
            adapter = listAdapter
        }
        var arrayList: ArrayList<Product> = SharedPreferenceUtil.getArrayList(this)
        var map = arrayList.groupBy { it -> it.quantity }
        listAdapter.submitList(arrayList)
    }

    private fun init() {
        buttonBuy.setOnClickListener(View.OnClickListener {
            SharedPreferenceUtil.clear(this@CartActivity)
            recyclerView.visibility = View.GONE
            buttonBuy.visibility = View.GONE
            textViewSuccess.visibility = View.VISIBLE
        })
    }
}
