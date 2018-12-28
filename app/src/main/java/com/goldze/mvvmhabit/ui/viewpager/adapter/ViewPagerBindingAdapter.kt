package com.goldze.mvvmhabit.ui.viewpager.adapter

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.databinding.ViewDataBinding
import android.view.ViewGroup

import com.goldze.mvvmhabit.databinding.ItemViewpagerBinding
import com.goldze.mvvmhabit.ui.viewpager.vm.ViewPagerItemViewModel

import me.goldze.mvvmhabit.utils.ToastUtils
import me.tatarka.bindingcollectionadapter2.BindingViewPagerAdapter

/**
 * Created by goldze on 2018/6/21.
 */

class ViewPagerBindingAdapter : BindingViewPagerAdapter<ViewPagerItemViewModel>() {

    override fun onBindBinding(binding: ViewDataBinding, variableId: Int, layoutRes: Int, position: Int, item: ViewPagerItemViewModel) {
        super.onBindBinding(binding, variableId, layoutRes, position, item)
        //这里可以强转成ViewPagerItemViewModel对应的ViewDataBinding，
        val _binding = binding as ItemViewpagerBinding
        item.clickEvent.observe(_binding.root.context as LifecycleOwner, Observer { ToastUtils.showShort("position：$position") })
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        super.destroyItem(container, position, `object`)
    }
}
