package com.characterexploretest.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Actor(@SerializedName("char_id")
                 val char_id: Int,
                 @SerializedName("name")
                 val name: String,
                 @SerializedName("birthday")
                 val birthday: String,
                 @SerializedName("occupation")
                 val occupation: MutableList<String>,
                 @SerializedName("img")
                 val img: String,
                 @SerializedName("status")
                 val status: String,
                 @SerializedName("nickname")
                 val nickname: String,
                 @SerializedName("appearance")
                 val appearance: MutableList<Int>,
                 @SerializedName("portrayed")
                 val portrayed: String,
                 @SerializedName("category")
                 val category: String,
                 @SerializedName("better_call_saul_appearance")
                 val better_call_saul_appearance: MutableList<Int>): Parcelable
