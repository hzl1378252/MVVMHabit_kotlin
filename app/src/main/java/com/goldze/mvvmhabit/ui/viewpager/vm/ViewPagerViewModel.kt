package com.goldze.mvvmhabit.ui.viewpager.vm

import android.app.Application
import android.databinding.ObservableArrayList
import android.databinding.ObservableList

import com.goldze.mvvmhabit.BR
import com.goldze.mvvmhabit.R
import com.goldze.mvvmhabit.ui.viewpager.adapter.ViewPagerBindingAdapter

import me.goldze.mvvmhabit.base.BaseViewModel
import me.goldze.mvvmhabit.binding.command.BindingCommand
import me.goldze.mvvmhabit.binding.command.BindingConsumer
import me.goldze.mvvmhabit.utils.ToastUtils
import me.tatarka.bindingcollectionadapter2.BindingViewPagerAdapter
import me.tatarka.bindingcollectionadapter2.ItemBinding

/**
 * 所有例子仅做参考,千万不要把它当成一种标准,毕竟主打的不是例子,业务场景繁多,理解如何使用才最重要。
 * Created by goldze on 2018/7/18.
 */

class ViewPagerViewModel(application: Application) : BaseViewModel(application) {

    //给ViewPager添加ObservableList
    var items: ObservableList<ViewPagerItemViewModel> = ObservableArrayList()
    //给ViewPager添加ItemBinding
    var itemBinding = ItemBinding.of<ViewPagerItemViewModel>(BR.viewModel, R.layout.item_viewpager)
    //给ViewPager添加PageTitle
    val pageTitles: BindingViewPagerAdapter.PageTitles<ViewPagerItemViewModel> = BindingViewPagerAdapter.PageTitles { position, item -> "条目$position" }
    //给ViewPager添加Adpter，使用自定义的Adapter继承BindingViewPagerAdapter，重写onBindBinding方法
    val adapter = ViewPagerBindingAdapter()
    //ViewPager切换监听
    var onPageSelectedCommand = BindingCommand(BindingConsumer<Int> { index -> ToastUtils.showShort("ViewPager切换：" + index!!) })

    fun addPage() {
        items.clear()
        //模拟3个ViewPager页面
        for (i in 1..3) {
            val itemViewModel = ViewPagerItemViewModel(this, "第" + i + "个页面")
            items.add(itemViewModel)
        }
    }
}
