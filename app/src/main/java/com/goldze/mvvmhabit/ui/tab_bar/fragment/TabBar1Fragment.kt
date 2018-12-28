package com.goldze.mvvmhabit.ui.tab_bar.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup

import com.goldze.mvvmhabit.BR
import com.goldze.mvvmhabit.R

import me.goldze.mvvmhabit.base.BaseFragment

/**
 * Created by goldze on 2018/7/18.
 */

class TabBar1Fragment : BaseFragment<*, *>() {
    override fun initContentView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): Int {
        return R.layout.fragment_tab_bar_1
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

}
