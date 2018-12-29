package com.goldze.mvvmhabit.entity

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by goldze on 2017/7/17.
 */

class DemoEntity(
        var nextPageToken: String,
        var prevPageToken: String,
        var requestCount: Int,
        var responseCount: Int,
        var totalResults: Int,
        var items: List<ItemsEntity>
)

data class ItemsEntity(
        var id: Int,
        var name: String,
        var img: String?="",
        var detail: String?="",
        var href: String?="",
        var pubDate: String="",
        var type: Int?=0
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readValue(Int::class.java.classLoader) as? Int) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(img)
        parcel.writeString(detail)
        parcel.writeString(href)
        parcel.writeString(pubDate)
        parcel.writeValue(type)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ItemsEntity> {
        override fun createFromParcel(parcel: Parcel): ItemsEntity {
            return ItemsEntity(parcel)
        }

        override fun newArray(size: Int): Array<ItemsEntity?> {
            return arrayOfNulls(size)
        }
    }
}