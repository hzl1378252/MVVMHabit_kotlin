package com.goldze.mvvmhabit.ui.viewpager.vm

import me.goldze.mvvmhabit.base.BaseViewModel
import me.goldze.mvvmhabit.base.ItemViewModel
import me.goldze.mvvmhabit.binding.command.BindingAction
import me.goldze.mvvmhabit.binding.command.BindingCommand
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent

/**
 * Created by goldze on 2018/7/18.
 */

class ViewPagerItemViewModel(viewModel: BaseViewModel, var text: String) : ItemViewModel<*>(viewModel) {
    var clickEvent: SingleLiveEvent<String> = SingleLiveEvent()

    var onItemClick = BindingCommand(BindingAction {
        //点击之后将逻辑转到adapter中处理
        clickEvent.value = text
    })
}
