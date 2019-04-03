package com.miruna.hospitalmanager.application.pacient.file

import android.os.Parcel
import android.os.Parcelable

data class File (
    val id : String,
    val content : String
):Parcelable, Cloneable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    )
    override fun writeToParcel(dest: Parcel?, flags: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun describeContents(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object CREATOR : Parcelable.Creator<File> {
        override fun createFromParcel(parcel: Parcel): File {
            return File(parcel)
        }

        override fun newArray(size: Int): Array<File?> {
            return arrayOfNulls(size)
        }
    }
}