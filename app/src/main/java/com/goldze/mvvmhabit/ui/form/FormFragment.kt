package com.goldze.mvvmhabit.ui.form

import android.app.DatePickerDialog
import android.arch.lifecycle.Observer
import android.databinding.Observable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.DatePicker

import com.goldze.mvvmhabit.BR
import com.goldze.mvvmhabit.R
import com.goldze.mvvmhabit.databinding.FragmentFormBinding
import com.goldze.mvvmhabit.entity.FormEntity
import com.goldze.mvvmhabit.ui.base.TitleViewModel

import java.util.Calendar

import me.goldze.mvvmhabit.base.BaseFragment
import me.goldze.mvvmhabit.utils.MaterialDialogUtils

/**
 * Created by goldze on 2017/7/17.
 * 表单提交/编辑界面
 */

class FormFragment : BaseFragment<FragmentFormBinding, FormViewModel>() {

    private lateinit var entity: FormEntity

    override fun initParam() {
        //获取列表传入的实体
        val mBundle = arguments
        if (mBundle != null) {
            entity = mBundle.getParcelable("entity")
        }
    }

    override fun initContentView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): Int {
        return R.layout.fragment_form
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initData() {
        //通过binding拿到toolbar控件, 设置给Activity
        (activity as AppCompatActivity).setSupportActionBar(binding.include.toolbar)
        //View层传参到ViewModel层
        viewModel.setFormEntity(entity)
        //创建TitleViewModel
        val titleViewModel = createViewModel(this, TitleViewModel::class.java)
        viewModel.setTitleViewModel(titleViewModel)
    }

    override fun initViewObservable() {
        //监听日期选择
        viewModel.uc.showDateDialogObservable.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable, propertyId: Int) {
                val calendar = Calendar.getInstance()
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DAY_OF_MONTH)
                val datePickerDialog = DatePickerDialog(context!!, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth -> viewModel.setBir(year, month, dayOfMonth) }, year, month, day)
                datePickerDialog.setMessage("生日选择")
                datePickerDialog.show()
            }
        })
        viewModel.entityJsonLiveData.observe(this, Observer { submitJson -> MaterialDialogUtils.showBasicDialog(context, "提交的json实体数据：\r\n" + submitJson!!).show() })
    }
}
