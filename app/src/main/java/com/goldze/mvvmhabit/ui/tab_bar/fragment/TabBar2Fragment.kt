package com.goldze.mvvmhabit.ui.tab_bar.fragment

import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup

import com.goldze.mvvmhabit.BR
import com.goldze.mvvmhabit.R

import me.goldze.mvvmhabit.base.BaseFragment
import me.goldze.mvvmhabit.base.BaseViewModel

/**
 * Created by goldze on 2018/7/18.
 */

class TabBar2Fragment : BaseFragment<ViewDataBinding, BaseViewModel>() {
    override fun initContentView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): Int {
        return R.layout.fragment_tab_bar_2
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

}
