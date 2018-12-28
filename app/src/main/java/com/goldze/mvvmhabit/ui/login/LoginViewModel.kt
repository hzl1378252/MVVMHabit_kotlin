package com.goldze.mvvmhabit.ui.login

import android.app.Application
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.text.TextUtils
import android.view.View

import com.goldze.mvvmhabit.ui.main.DemoActivity

import java.util.concurrent.TimeUnit

import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import me.goldze.mvvmhabit.base.BaseViewModel
import me.goldze.mvvmhabit.binding.command.BindingAction
import me.goldze.mvvmhabit.binding.command.BindingCommand
import me.goldze.mvvmhabit.binding.command.BindingConsumer
import me.goldze.mvvmhabit.utils.RxUtils
import me.goldze.mvvmhabit.utils.ToastUtils

/**
 * Created by goldze on 2017/7/17.
 */

class LoginViewModel(application: Application) : BaseViewModel(application) {
    //用户名的绑定
    var userName = ObservableField("")
    //密码的绑定
    var password = ObservableField("")
    //用户名清除按钮的显示隐藏绑定
    var clearBtnVisibility = ObservableInt()
    //封装一个界面发生改变的观察者
    var uc = UIChangeObservable()

    //清除用户名的点击事件, 逻辑从View层转换到ViewModel层
    var clearUserNameOnClickCommand = BindingCommand<LoginViewModel>(BindingAction { userName.set("") })
    //密码显示开关  (你可以尝试着狂按这个按钮,会发现它有防多次点击的功能)
    var passwordShowSwitchOnClickCommand = BindingCommand<LoginViewModel>(BindingAction {
        //让观察者的数据改变,逻辑从ViewModel层转到View层，在View层的监听则会被调用
        uc.pSwitchObservable.set(!uc.pSwitchObservable.get())
    })
    //用户名输入框焦点改变的回调事件
    var onFocusChangeCommand = BindingCommand(BindingConsumer<Boolean> { hasFocus ->
        if (hasFocus!!) {
            clearBtnVisibility.set(View.VISIBLE)
        } else {
            clearBtnVisibility.set(View.INVISIBLE)
        }
    })
    //登录按钮的点击事件
    var loginOnClickCommand = BindingCommand<LoginViewModel>(BindingAction { login() })

    inner class UIChangeObservable {
        //密码开关观察者
        var pSwitchObservable = ObservableBoolean(false)
    }

    /**
     * 网络模拟一个登陆操作
     */
    private fun login() {
        if (TextUtils.isEmpty(userName.get())) {
            ToastUtils.showShort("请输入账号！")
            return
        }
        if (TextUtils.isEmpty(password.get())) {
            ToastUtils.showShort("请输入密码！")
            return
        }
        //RaJava模拟一个延迟操作
        Observable.just("")
                .delay(3, TimeUnit.SECONDS) //延迟3秒
                .compose(RxUtils.bindToLifecycle(lifecycleProvider))//界面关闭自动取消
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .doOnSubscribe { showDialog() }
                .subscribe {
                    dismissDialog()
                    //进入DemoActivity页面
                    startActivity(DemoActivity::class.java)
                    //关闭页面
                    finish()
                }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
