package com.goldze.mvvmhabit.ui.network

import android.arch.lifecycle.Observer
import android.databinding.Observable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup

import com.afollestad.materialdialogs.DialogAction
import com.afollestad.materialdialogs.MaterialDialog
import com.goldze.mvvmhabit.BR
import com.goldze.mvvmhabit.R
import com.goldze.mvvmhabit.databinding.FragmentNetworkBinding

import me.goldze.mvvmhabit.base.BaseFragment
import me.goldze.mvvmhabit.utils.MaterialDialogUtils
import me.goldze.mvvmhabit.utils.ToastUtils

/**
 * Created by goldze on 2017/7/17.
 * 网络请求列表界面
 */

class NetWorkFragment : BaseFragment<FragmentNetworkBinding, NetWorkViewModel>() {
    override fun initContentView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): Int {
        return R.layout.fragment_network
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initData() {
        //请求网络数据
        viewModel.requestNetWork()
    }

    override fun initViewObservable() {
        //监听下拉刷新完成
        viewModel.uc.finishRefreshing.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(observable: Observable, i: Int) {
                //结束刷新
                binding.twinklingRefreshLayout.finishRefreshing()
            }
        })
        //监听上拉加载完成
        viewModel.uc.finishLoadmore.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(observable: Observable, i: Int) {
                //结束刷新
                binding.twinklingRefreshLayout.finishLoadmore()
            }
        })
        //监听删除条目
        viewModel.deleteItemLiveData.observe(this, Observer { netWorkItemViewModel ->
            val index = netWorkItemViewModel?.let { viewModel.getPosition(it) }
            //删除选择对话框
            MaterialDialogUtils.showBasicDialog(context, "提示", "是否删除【" + netWorkItemViewModel?.entity?.get().getName() + "】？ 列表索引值：" + index)
                    .onNegative { dialog, which ->
                        ToastUtils.showShort("取消")
                    }.onPositive {
                        dialog, which ->
                        netWorkItemViewModel?.let { viewModel.deleteItem(it) }
                    }.show()
        })
    }
}
