package com.example.escape9

import android.os.Parcel
import android.os.Parcelable

class Item() : Parcelable {
    var region: String? = null
    var cafe: String? = null
    var theme: String? = null
    var type: String? = null
    var diff: String? = null
    var recom: String? = null
    var url: String? = null
    var poster: String? = null

    constructor(parcel: Parcel) : this() {
        region = parcel.readString()
        cafe = parcel.readString()
        theme = parcel.readString()
        type = parcel.readString()
        diff = parcel.readString()
        recom = parcel.readString()
        url = parcel.readString()
        poster = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(region)
        parcel.writeString(cafe)
        parcel.writeString(theme)
        parcel.writeString(type)
        parcel.writeString(diff)
        parcel.writeString(recom)
        parcel.writeString(url)
        parcel.writeString(poster)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Item> {
        override fun createFromParcel(parcel: Parcel): Item {
            return Item(parcel)
        }

        override fun newArray(size: Int): Array<Item?> {
            return arrayOfNulls(size)
        }
    }
}