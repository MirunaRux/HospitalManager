package com.miruna.hospitalmanager.application.pacient

import android.os.Parcel
import android.os.Parcelable


data class Pacient(
    val id: String,
    val name: String,
    val surname: String,
    val age: String,
    val CNP: String?,
    val dateIn: String,
    val dateEx: String
): Parcelable, Cloneable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )
    override fun writeToParcel(dest: Parcel?, flags: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun describeContents(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object CREATOR : Parcelable.Creator<Pacient> {
        override fun createFromParcel(parcel: Parcel): Pacient {
            return Pacient(parcel)
        }

        override fun newArray(size: Int): Array<Pacient?> {
            return arrayOfNulls(size)
        }
    }
}