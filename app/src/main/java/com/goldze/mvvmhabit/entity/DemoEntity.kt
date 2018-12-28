package com.goldze.mvvmhabit.entity

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by goldze on 2017/7/17.
 */

class DemoEntity (
    var nextPageToken: String,
    var prevPageToken: String,
    var requestCount: Int ,
    var responseCount: Int,
    var totalResults: Int ,
    var items: List<ItemsEntity>
)
    data  class ItemsEntity (
        var detail: String,
        var href: String,
        var id: Int,
        var img: String,
        var name: String,
        var pubDate: String,
        var type: Int
    ) : Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readString(),
                parcel.readString(),
                parcel.readInt(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readInt()) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(detail)
            parcel.writeString(href)
            parcel.writeInt(id)
            parcel.writeString(img)
            parcel.writeString(name)
            parcel.writeString(pubDate)
            parcel.writeInt(type)
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


