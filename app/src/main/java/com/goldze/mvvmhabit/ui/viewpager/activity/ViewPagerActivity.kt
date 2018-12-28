package com.goldze.mvvmhabit.ui.viewpager.activity

import android.os.Bundle
import android.support.design.widget.TabLayout

import com.goldze.mvvmhabit.BR
import com.goldze.mvvmhabit.R
import com.goldze.mvvmhabit.databinding.FragmentViewpagerBinding
import com.goldze.mvvmhabit.ui.viewpager.vm.ViewPagerViewModel

import me.goldze.mvvmhabit.base.BaseActivity

/**
 * ViewPager绑定的例子, 更多绑定方式，请参考 https://github.com/evant/binding-collection-adapter
 * 所有例子仅做参考,千万不要把它当成一种标准,毕竟主打的不是例子,业务场景繁多,理解如何使用才最重要。
 * Created by goldze on 2018/7/18.
 */

class ViewPagerActivity : BaseActivity<FragmentViewpagerBinding, ViewPagerViewModel>() {

    override fun initContentView(savedInstanceState: Bundle): Int {
        return R.layout.fragment_viewpager
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }


    override fun initData() {
        // 使用 TabLayout 和 ViewPager 相关联
        binding.tabs.setupWithViewPager(binding.viewPager)
        binding.viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tabs))
    }

    override fun initViewObservable() {
        viewModel.addPage()
    }
}
