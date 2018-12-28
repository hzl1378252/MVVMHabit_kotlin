package com.goldze.mvvmhabit.ui.base

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.view.View

import me.goldze.mvvmhabit.base.AppManager
import me.goldze.mvvmhabit.binding.command.BindingAction
import me.goldze.mvvmhabit.binding.command.BindingCommand
import me.goldze.mvvmhabit.utils.ToastUtils

/**
 * 对应include标题的ViewModel
 * 所有例子仅做参考,业务多种多样,可能我这里写的例子和你的需求不同，理解如何使用才最重要。
 * Toolbar的封装方式有很多种，具体封装需根据项目实际业务和习惯来编写
 * Created by goldze on 2018/7/26.
 */

class TitleViewModel(application: Application) : AndroidViewModel(application) {
    var titleText = ObservableField("")
    var rightText = ObservableField("")
    var rightTextVisibility = ObservableInt(View.GONE)

    //点击返回
    var backOnClickCommand = BindingCommand<TitleViewModel>(BindingAction {
        //统一处理返回事件
        AppManager.getAppManager().currentActivity().finish()
    })
    //右边文字点击事件
    var rightTextOnClickCommand = BindingCommand<TitleViewModel>(BindingAction {
        //统一处理右上角按钮事件。
    })
}
