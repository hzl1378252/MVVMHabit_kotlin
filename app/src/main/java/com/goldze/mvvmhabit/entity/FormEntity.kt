package com.goldze.mvvmhabit.entity

import android.databinding.BaseObservable
import android.os.Parcel
import android.os.Parcelable

/**
 * Created by goldze on 2017/7/17.
 */

class FormEntity  (//添加默認參數 不需要的时候可以不传入
    var id: String?="",
    var name: String?="",
    var sex: String?="",
    var bir: String?="",
    var hobby: String?="",
    var marry: Boolean?=false
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readValue(Boolean::class.java.classLoader) as? Boolean) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(sex)
        parcel.writeString(bir)
        parcel.writeString(hobby)
        parcel.writeValue(marry)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FormEntity> {
        override fun createFromParcel(parcel: Parcel): FormEntity {
            return FormEntity(parcel)
        }

        override fun newArray(size: Int): Array<FormEntity?> {
            return arrayOfNulls(size)
        }
    }
}