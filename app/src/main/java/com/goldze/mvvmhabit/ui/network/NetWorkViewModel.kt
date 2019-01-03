package com.goldze.mvvmhabit.ui.network

import android.annotation.SuppressLint
import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableArrayList
import android.databinding.ObservableBoolean
import android.databinding.ObservableList

import com.goldze.mvvmhabit.BR
import com.goldze.mvvmhabit.R
import com.goldze.mvvmhabit.entity.DemoEntity
import com.goldze.mvvmhabit.entity.ItemsEntity
import com.goldze.mvvmhabit.service.DemoApiService
import com.goldze.mvvmhabit.utils.RetrofitClient

import java.util.concurrent.TimeUnit

import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import me.goldze.mvvmhabit.base.BaseViewModel
import me.goldze.mvvmhabit.binding.command.BindingAction
import me.goldze.mvvmhabit.binding.command.BindingCommand
import me.goldze.mvvmhabit.http.BaseResponse
import me.goldze.mvvmhabit.http.ResponseThrowable
import me.goldze.mvvmhabit.utils.RxUtils
import me.goldze.mvvmhabit.utils.ToastUtils
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter
import me.tatarka.bindingcollectionadapter2.ItemBinding

/**
 * Created by goldze on 2017/7/17.
 */

class NetWorkViewModel(application: Application) : BaseViewModel(application) {
    private var itemIndex = 0
    var deleteItemLiveData: MutableLiveData<NetWorkItemViewModel> = MutableLiveData()
    //封装一个界面发生改变的观察者
    var uc = UIChangeObservable()

    //给RecyclerView添加ObservableList
    var observableList: ObservableList<NetWorkItemViewModel>? = ObservableArrayList()
    //给RecyclerView添加ItemBinding
    var itemBinding = ItemBinding.of<NetWorkItemViewModel>(BR.viewModel, R.layout.item_network)
    //RecyclerView多布局写法
    //    public ItemBinding<Object> itemBinding = ItemBinding.of(new OnItemBind<Object>() {
    //        @Override
    //        public void onItemBind(ItemBinding itemBinding, int position, Object item) {
    //            if (position == 0) {
    //                //设置头布局
    //                itemBinding.set(BR.viewModel, R.layout.head_netword);
    //            } else {
    //                itemBinding.set(BR.viewModel, R.layout.item_network);
    //            }
    //        }
    //    });
    //给RecyclerView添加Adpter，请使用自定义的Adapter继承BindingRecyclerViewAdapter，重写onBindBinding方法
    val adapter = BindingRecyclerViewAdapter<NetWorkItemViewModel>()
    //下拉刷新
    var onRefreshCommand = BindingCommand<NetWorkViewModel>(BindingAction {
        ToastUtils.showShort("下拉刷新")
        requestNetWork()
    })
    //上拉加载
    var onLoadMoreCommand = BindingCommand<NetWorkViewModel>(BindingAction {
        if (itemIndex > 50) {
            ToastUtils.showLong("兄dei，你太无聊啦~崩是不可能的~")
            uc.finishLoadmore.set(!uc.finishLoadmore.get())
            return@BindingAction
        }
        //模拟网络上拉加载更多
        Observable.just("")
                .delay(3, TimeUnit.SECONDS) //延迟3秒
                .compose(RxUtils.bindToLifecycle(lifecycleProvider))//界面关闭自动取消
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .doOnSubscribe { ToastUtils.showShort("上拉加载") }
                .subscribe {
                    //刷新完成收回
                    uc.finishLoadmore.set(!uc.finishLoadmore.get())
                    //模拟一部分假数据
                    for (i in 0..9) {
                        val item = ItemsEntity(1, "模拟条目" + itemIndex++)
                        val itemViewModel = NetWorkItemViewModel(this@NetWorkViewModel, item)
                        //双向绑定动态添加Item
                        observableList!!.add(itemViewModel)
                    }
                }
    })

    inner class UIChangeObservable {
        //下拉刷新完成
        var finishRefreshing = ObservableBoolean(false)
        //上拉加载完成
        var finishLoadmore = ObservableBoolean(false)
    }

    /**
     * 网络请求方法，在ViewModel中调用，Retrofit+RxJava充当Repository，即可视为Model层
     */
    @SuppressLint("CheckResult")
    fun requestNetWork() {
        RetrofitClient.instance.create(DemoApiService::class.java)
                .demoGet()
                .compose(RxUtils.bindToLifecycle(lifecycleProvider)) //请求与View周期同步
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .doOnSubscribe {
                    showDialog("正在请求...")
                }
                .subscribe(object : Consumer<BaseResponse<DemoEntity>> {
                    override fun accept(t: BaseResponse<DemoEntity>?) {
                        itemIndex = 0
                        //清除列表
                        observableList?.clear()
                        t?.run {
                            if (code == 1) {
                                //将实体赋给LiveData
                                result?.items?.forEach {
                                    val itemViewModel = com.goldze.mvvmhabit.ui.network.NetWorkItemViewModel(this@NetWorkViewModel, it)
                                    //双向绑定动态添加Item
                                    observableList!!.add(itemViewModel)
                                }

                            } else {
                                //code错误时也可以定义Observable回调到View层去处理
                                me.goldze.mvvmhabit.utils.ToastUtils.showShort("数据错误")
                            }
                        }
                    }

                },object :Consumer<ResponseThrowable> {
                    override fun accept(t: ResponseThrowable?) {
                        dismissDialog()
                        //请求刷新完成收回
                        uc.finishRefreshing.set(!uc.finishRefreshing.get())
                        ToastUtils.showShort(t?.message)
                        t?.printStackTrace()
                    }
                })
    }

    /**
     * 删除条目
     *
     * @param netWorkItemViewModel
     */
    fun deleteItem(netWorkItemViewModel: NetWorkItemViewModel) {
        //点击确定，在 observableList 绑定中删除，界面立即刷新
        observableList!!.remove(netWorkItemViewModel)
    }

    /**
     * 获取条目下标
     *
     * @param netWorkItemViewModel
     * @return
     */
    fun getPosition(netWorkItemViewModel: NetWorkItemViewModel): Int {
        return observableList!!.indexOf(netWorkItemViewModel)
    }

    override fun onDestroy() {
        super.onDestroy()
        observableList!!.clear()
        observableList = null
    }
}
