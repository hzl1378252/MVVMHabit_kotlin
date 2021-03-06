package com.goldze.mvvmhabit.ui.network.detail

import android.app.Application
import android.databinding.ObservableField

import com.goldze.mvvmhabit.entity.DemoEntity
import com.goldze.mvvmhabit.entity.ItemsEntity

import me.goldze.mvvmhabit.base.BaseViewModel

/**
 * Created by goldze on 2017/7/17.
 */

class DetailViewModel(application: Application) : BaseViewModel(application) {
    var entity : ObservableField<ItemsEntity>? = ObservableField()
    //    var entity  = ObservableField<ItemsEntity>()
    fun setDemoEntity(entity: ItemsEntity) {
        this.entity?.set(entity)
    }

    override fun onDestroy() {
        super.onDestroy()
        entity
        entity=null//会提示此值不可为null  ？可为null

    }
}
