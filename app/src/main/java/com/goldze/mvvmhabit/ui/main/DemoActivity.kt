package com.goldze.mvvmhabit.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.arch.lifecycle.Observer
import android.content.Context
import android.databinding.Observable
import android.os.Bundle

import com.goldze.mvvmhabit.BR
import com.goldze.mvvmhabit.R
import com.goldze.mvvmhabit.databinding.ActivityDemoBinding
import com.tbruyelle.rxpermissions2.RxPermissions

import io.reactivex.functions.Consumer
import me.goldze.mvvmhabit.base.BaseActivity
import me.goldze.mvvmhabit.http.DownLoadManager
import me.goldze.mvvmhabit.http.download.ProgressCallBack
import me.goldze.mvvmhabit.utils.KLog
import me.goldze.mvvmhabit.utils.ToastUtils
import okhttp3.ResponseBody

/**
 * Created by goldze on 2017/7/17.
 */

class DemoActivity : BaseActivity<ActivityDemoBinding, DemoViewModel>() {

    override fun initContentView(savedInstanceState: Bundle): Int {
        return R.layout.activity_demo
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun  initViewObservable() {
        //注册监听相机权限的请求
        viewModel.requestCameraPermissions.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(observable: Observable, i: Int) {
                requestCameraPermissions()
            }
        })
        //注册文件下载的监听
        viewModel.loadUrl.observe(this, Observer { url -> downFile(url) })
    }

    /**
     * 请求相机权限
     */
    @SuppressLint("CheckResult")
    private fun requestCameraPermissions() {
        //请求打开相机权限
        val rxPermissions = RxPermissions(this@DemoActivity)
        rxPermissions.request(Manifest.permission.CAMERA)
                .subscribe {
                    if (it) {
                        ToastUtils.showShort("相机权限已经打开，直接跳入相机")
                    } else {
                        ToastUtils.showShort("权限被拒绝")
                    }
                }
    }

    private fun downFile(url: String?) {
        val destFileDir = application.cacheDir.path
        val destFileName = System.currentTimeMillis().toString() + ".apk"
        val progressDialog = ProgressDialog(this)
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
        progressDialog.setTitle("正在下载...")
        progressDialog.setCancelable(false)
        progressDialog.show()
        DownLoadManager.getInstance().load(url, object : ProgressCallBack<ResponseBody>(destFileDir, destFileName) {
            override fun onStart() {
                super.onStart()
            }

            override fun onCompleted() {
                progressDialog.dismiss()
            }

            override fun onSuccess(responseBody: ResponseBody) {
                ToastUtils.showShort("文件下载完成！")
            }

            override fun progress(progress: Long, total: Long) {
                progressDialog.max = total.toInt()
                progressDialog.progress = progress.toInt()
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
                ToastUtils.showShort("文件下载失败！")
                progressDialog.dismiss()
            }
        })
    }
}
