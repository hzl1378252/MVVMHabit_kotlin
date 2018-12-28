package com.goldze.mvvmhabit.ui.main

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableBoolean
import android.os.Bundle

import com.goldze.mvvmhabit.entity.FormEntity
import com.goldze.mvvmhabit.ui.form.FormFragment
import com.goldze.mvvmhabit.ui.network.NetWorkFragment
import com.goldze.mvvmhabit.ui.tab_bar.activity.TabBarActivity
import com.goldze.mvvmhabit.ui.viewpager.activity.ViewPagerActivity

import me.goldze.mvvmhabit.base.BaseViewModel
import me.goldze.mvvmhabit.binding.command.BindingAction
import me.goldze.mvvmhabit.binding.command.BindingCommand

/**
 * Created by goldze on 2017/7/17.
 */

class DemoViewModel(application: Application) : BaseViewModel(application) {
    //使用Observable
    var requestCameraPermissions = ObservableBoolean(false)
    //使用LiveData
    var loadUrl: MutableLiveData<String> = MutableLiveData()

    //网络访问点击事件
    var netWorkClick = BindingCommand<DemoViewModel>(BindingAction { startContainerActivity(NetWorkFragment::class.java.canonicalName) })
    //进入TabBarActivity
    var startTabBarClick = BindingCommand<DemoViewModel>(BindingAction { startActivity(TabBarActivity::class.java) })
    //ViewPager绑定
    var viewPagerBindingClick = BindingCommand<DemoViewModel>(BindingAction { startActivity(ViewPagerActivity::class.java) })
    //表单提交点击事件
    var formSbmClick = BindingCommand<DemoViewModel>(BindingAction { startContainerActivity(FormFragment::class.java.canonicalName) })
    //表单修改点击事件
    var formModifyClick = BindingCommand<DemoViewModel>(BindingAction {
        //模拟一个修改的实体数据
        val entity :FormEntity= FormEntity("12345678","goldze","1","xxxx年xx月xx日",true)
//        entity.id = "12345678"
//        entity.name = "goldze"
//        entity.sex = "1"
//        entity.bir = "xxxx年xx月xx日"
//        entity.marry = true
        //传入实体数据
        val mBundle = Bundle()
        mBundle.putParcelable("entity", entity)
        startContainerActivity(FormFragment::class.java.canonicalName, mBundle)
    })
    //权限申请
    var permissionsClick = BindingCommand<DemoViewModel>(BindingAction { requestCameraPermissions.set(!requestCameraPermissions.get()) })

    //全局异常捕获
    var exceptionClick = BindingCommand<DemoViewModel>(BindingAction {
        //伪造一个异常
        Integer.parseInt("goldze")
    })
    //文件下载
    var fileDownLoadClick = BindingCommand<DemoViewModel>(BindingAction { loadUrl.value = "http://gdown.baidu.com/data/wisegame/a2cd8828b227b9f9/neihanduanzi_692.apk" })
}
