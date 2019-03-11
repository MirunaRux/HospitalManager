package com.miruna.hospitalmanager.application.pacient

import android.os.Parcel
import android.os.Parcelable


data class Pacient(
    val id: Int,
    val name: String,
    val surname: String,
    val age: Int,
    val CNP: String?,
    val dateIn: String,
    val dateEx: String) : Parcelable, Cloneable{

    public override fun clone(): Any {
        val parcel = Parcel.obtain()
        this.writeToParcel(parcel, 0)
        val bytes = parcel.marshall()

        val parcel2 = Parcel.obtain()
        parcel2.unmarshall(bytes, 0, bytes.size)
        parcel2.setDataPosition(0)
        return Pacient.createFromParcel(parcel2)
    }

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
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