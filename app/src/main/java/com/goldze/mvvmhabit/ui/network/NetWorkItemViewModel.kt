package com.goldze.mvvmhabit.ui.network

import android.databinding.ObservableField
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.content.ContextCompat

import com.goldze.mvvmhabit.R
import com.goldze.mvvmhabit.entity.DemoEntity
import com.goldze.mvvmhabit.entity.ItemsEntity
import com.goldze.mvvmhabit.ui.network.detail.DetailFragment

import me.goldze.mvvmhabit.base.ItemViewModel
import me.goldze.mvvmhabit.binding.command.BindingAction
import me.goldze.mvvmhabit.binding.command.BindingCommand
import me.goldze.mvvmhabit.utils.ToastUtils

/**
 * Created by goldze on 2017/7/17.
 */

class NetWorkItemViewModel(viewModel: NetWorkViewModel, entity: ItemsEntity) : ItemViewModel<NetWorkViewModel>(viewModel) {
    var entity = ObservableField<ItemsEntity>()
    var drawableImg: Drawable? = null

    //条目的点击事件
    var itemClick = BindingCommand<NetWorkItemViewModel>(BindingAction {
        //这里可以通过一个标识,做出判断，已达到跳入不同界面的逻辑
        if (entity.id === -1) {
            ToastUtils.showShort(entity.name)
        } else {
            //跳转到详情界面,传入条目的实体对象
            val mBundle = Bundle()
            mBundle.putParcelable("entity", entity)
            viewModel.startContainerActivity(DetailFragment::class.java.canonicalName, mBundle)
        }
    })
    //条目的长按事件
    var itemLongClick = BindingCommand<NetWorkItemViewModel>(BindingAction {
        //以前是使用Messenger发送事件，在NetWorkViewModel中完成删除逻辑
        //            Messenger.getDefault().send(NetWorkItemViewModel.this, NetWorkViewModel.TOKEN_NETWORKVIEWMODEL_DELTE_ITEM);
        //现在ItemViewModel中存在ViewModel引用，可以直接拿到LiveData去做删除
        viewModel.deleteItemLiveData.value = this@NetWorkItemViewModel
    })

    init {
        this.entity.set(entity)
        //ImageView的占位图片，可以解决RecyclerView中图片错误问题
        drawableImg = ContextCompat.getDrawable(viewModel.getApplication(), R.mipmap.ic_launcher)
    }
    //    /**
    //     * 可以在xml中使用binding:currentView="@{viewModel.titleTextView}" 拿到这个控件的引用, 但是强烈不推荐这样做，避免内存泄漏
    //     **/
    //    private TextView tv;
    //    //将标题TextView控件回调到ViewModel中
    //    public BindingCommand<TextView> titleTextView = new BindingCommand(new BindingConsumer<TextView>() {
    //        @Override
    //        public void call(TextView tv) {
    //            NetWorkItemViewModel.this.tv = tv;
    //        }
    //    });
}

