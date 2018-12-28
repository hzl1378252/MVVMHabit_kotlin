package com.goldze.mvvmhabit.ui.tab_bar.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v4.content.ContextCompat

import com.goldze.mvvmhabit.BR
import com.goldze.mvvmhabit.R
import com.goldze.mvvmhabit.databinding.ActivityTabBarBinding
import com.goldze.mvvmhabit.ui.tab_bar.fragment.TabBar1Fragment
import com.goldze.mvvmhabit.ui.tab_bar.fragment.TabBar2Fragment
import com.goldze.mvvmhabit.ui.tab_bar.fragment.TabBar3Fragment
import com.goldze.mvvmhabit.ui.tab_bar.fragment.TabBar4Fragment

import java.util.ArrayList

import me.goldze.mvvmhabit.base.BaseActivity
import me.goldze.mvvmhabit.base.BaseViewModel
import me.majiajie.pagerbottomtabstrip.NavigationController
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener

/**
 * 底部tab按钮的例子
 * 所有例子仅做参考,理解如何使用才最重要。
 * Created by goldze on 2018/7/18.
 */

class TabBarActivity : BaseActivity<ActivityTabBarBinding, BaseViewModel>() {
    private var mFragments: MutableList<Fragment>? = null

    override fun initContentView(savedInstanceState: Bundle): Int {
        return R.layout.activity_tab_bar
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initData() {
        //初始化Fragment
        initFragment()
        //初始化底部Button
        initBottomTab()
    }

    private fun initFragment() {
        mFragments = ArrayList()
        mFragments!!.add(TabBar1Fragment())
        mFragments!!.add(TabBar2Fragment())
        mFragments!!.add(TabBar3Fragment())
        mFragments!!.add(TabBar4Fragment())
        //默认选中第一个
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.frameLayout, mFragments!![0])
        transaction.commitAllowingStateLoss()
    }

    private fun initBottomTab() {
        val navigationController = binding.pagerBottomTab.material()
                .addItem(R.mipmap.yingyong, "应用")
                .addItem(R.mipmap.huanzhe, "工作")
                .addItem(R.mipmap.xiaoxi_select, "消息")
                .addItem(R.mipmap.wode_select, "我的")
                .setDefaultColor(ContextCompat.getColor(this, R.color.textColorVice))
                .build()
        //底部按钮的点击事件监听
        navigationController.addTabItemSelectedListener(object : OnTabItemSelectedListener {
            override fun onSelected(index: Int, old: Int) {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.frameLayout, mFragments!![index])
                transaction.commitAllowingStateLoss()
            }

            override fun onRepeat(index: Int) {}
        })
    }
}
