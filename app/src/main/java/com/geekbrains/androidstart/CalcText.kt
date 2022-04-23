package com.geekbrains.androidstart

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CalcText(
    var text: String?
) : Parcelable