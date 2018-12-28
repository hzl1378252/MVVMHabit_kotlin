package com.goldze.mvvmhabit.binding.twinklingrefreshlayout


import android.databinding.BindingAdapter

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout

import me.goldze.mvvmhabit.binding.command.BindingCommand


/**
 * Created by goldze on 2017/6/16.
 * TwinklingRefreshLayout列表刷新的绑定适配器
 */
object ViewAdapter {

    @BindingAdapter(value = ["onRefreshCommand", "onLoadMoreCommand"], requireAll = false)
    fun onRefreshAndLoadMoreCommand(layout: TwinklingRefreshLayout, onRefreshCommand: BindingCommand<*>?, onLoadMoreCommand: BindingCommand<*>?) {
        layout.setOnRefreshListener(object : RefreshListenerAdapter() {
            override fun onRefresh(refreshLayout: TwinklingRefreshLayout?) {
                super.onRefresh(refreshLayout)
                onRefreshCommand?.execute()
            }

            override fun onLoadMore(refreshLayout: TwinklingRefreshLayout?) {
                super.onLoadMore(refreshLayout)
                onLoadMoreCommand?.execute()
            }
        })
    }
}
