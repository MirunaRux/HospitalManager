package com.miruna.hospitalmanager.application.drug

import android.os.Parcel
import android.os.Parcelable


data class Drug (
    val id: String,
    val name: String,
    val number:Int
): Parcelable, Cloneable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    )
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeInt(number)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Drug> {
        override fun createFromParcel(parcel: Parcel): Drug {
            return Drug(parcel)
        }

        override fun newArray(size: Int): Array<Drug?> {
            return arrayOfNulls(size)
        }
    }
}