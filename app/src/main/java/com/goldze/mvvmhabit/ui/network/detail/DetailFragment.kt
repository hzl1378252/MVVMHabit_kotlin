package com.goldze.mvvmhabit.ui.network.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup

import com.goldze.mvvmhabit.BR
import com.goldze.mvvmhabit.R
import com.goldze.mvvmhabit.databinding.FragmentDetailBinding
import com.goldze.mvvmhabit.entity.DemoEntity
import com.goldze.mvvmhabit.entity.ItemsEntity

import me.goldze.mvvmhabit.base.BaseFragment

/**
 * Created by goldze on 2017/7/17.
 * 详情界面
 */

class DetailFragment : BaseFragment<FragmentDetailBinding, DetailViewModel>() {

    private lateinit var  entity:ItemsEntity
    override fun initParam() {
        //获取列表传入的实体
        val mBundle = arguments
        if (mBundle != null) {
            entity = mBundle.getParcelable("entity")
        }
    }

    override fun initContentView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): Int {
        return R.layout.fragment_detail
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initData() {
        viewModel.setDemoEntity(entity)
    }
}
