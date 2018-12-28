package com.goldze.mvvmhabit.ui.form

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableBoolean
import android.text.TextUtils
import android.view.View

import com.goldze.mvvmhabit.entity.FormEntity
import com.goldze.mvvmhabit.entity.SpinnerItemData
import com.goldze.mvvmhabit.ui.base.TitleViewModel
import com.google.gson.Gson

import java.util.ArrayList

import me.goldze.mvvmhabit.base.BaseViewModel
import me.goldze.mvvmhabit.binding.command.BindingAction
import me.goldze.mvvmhabit.binding.command.BindingCommand
import me.goldze.mvvmhabit.binding.command.BindingConsumer
import me.goldze.mvvmhabit.binding.viewadapter.spinner.IKeyAndValue
import me.goldze.mvvmhabit.utils.ToastUtils

/**
 * Created by goldze on 2017/7/17.
 */

class FormViewModel(application: Application) : BaseViewModel(application) {
    var entity: FormEntity? = null

    var sexItemDatas: MutableList<IKeyAndValue>? = null
    var entityJsonLiveData = MutableLiveData<String>()
    //封装一个界面发生改变的观察者
    var uc: UIChangeObservable? = null

    //include绑定一个通用的TitleViewModel
    lateinit var titleViewModel: TitleViewModel

    //性别选择的监听
    var onSexSelectorCommand = BindingCommand(BindingConsumer<IKeyAndValue> { iKeyAndValue -> entity!!.sex = iKeyAndValue.value })
    //生日选择的监听
    var onBirClickCommand = BindingCommand<FormViewModel>(BindingAction {
        //回调到view层(Fragment)中显示日期对话框
        uc!!.showDateDialogObservable.set(!uc!!.showDateDialogObservable.get())
    })
    //是否已婚Switch点状态改变回调
    var onMarryCheckedChangeCommand = BindingCommand(BindingConsumer<Boolean> { isChecked -> entity!!.marry = isChecked })
    //提交按钮点击事件
    var onCmtClickCommand = BindingCommand<FormViewModel>(BindingAction {
        val submitJson = Gson().toJson(entity)
        entityJsonLiveData.setValue(submitJson)
    })

    inner class UIChangeObservable {
        //显示日期对话框
        var showDateDialogObservable: ObservableBoolean = ObservableBoolean(false)

    }

    override fun onCreate() {
        super.onCreate()
        uc = UIChangeObservable()
        //sexItemDatas 一般可以从本地Sqlite数据库中取出数据字典对象集合，让该对象实现IKeyAndValue接口
        sexItemDatas = ArrayList()
        sexItemDatas!!.add(SpinnerItemData("男", "1"))
        sexItemDatas!!.add(SpinnerItemData("女", "2"))
    }

    fun setTitle(titleViewModel: TitleViewModel) {//因为kt语言会帮你自动生成属性的get set方法  所以不能直接命名setTitleViewModel
        this.titleViewModel = titleViewModel
        //初始化标题栏
        titleViewModel.rightTextVisibility.set(View.VISIBLE)
        titleViewModel.rightText.set("更多")
        if (TextUtils.isEmpty(entity!!.id)) {
            //ID为空是新增
            titleViewModel.titleText.set("表单提交")
        } else {
            //ID不为空是修改
            titleViewModel.titleText.set("表单编辑")
        }
        //右边文字的点击事件
        titleViewModel.rightTextOnClickCommand = BindingCommand(BindingAction { ToastUtils.showShort("更多") })
    }

    fun setFormEntity(entity: FormEntity) {
        this.entity = entity
    }

    fun setBir(year: Int, month: Int, dayOfMonth: Int) {
        //设置数据到实体中，自动刷新界面
        entity!!.bir = year.toString() + "年" + (month + 1) + "月" + dayOfMonth + "日"
        //刷新实体,驱动界面更新
        entity!!.notifyChange()
    }

    override fun onDestroy() {
        super.onDestroy()
        entity = null
        uc = null
        sexItemDatas!!.clear()
        sexItemDatas = null
    }
}
