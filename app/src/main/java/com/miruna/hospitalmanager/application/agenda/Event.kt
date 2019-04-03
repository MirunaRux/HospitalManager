package com.miruna.hospitalmanager.application.agenda

import android.os.Parcel
import android.os.Parcelable

data class Event (
    var id : String,
    var name : String,
    var location : String,
    var pacientName : String,
    var doctorUsername: String
): Parcelable, Cloneable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(location)
        parcel.writeString(pacientName)
        parcel.writeString(doctorUsername)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Event> {
        override fun createFromParcel(parcel: Parcel): Event {
            return Event(parcel)
        }

        override fun newArray(size: Int): Array<Event?> {
            return arrayOfNulls(size)
        }
    }
}
