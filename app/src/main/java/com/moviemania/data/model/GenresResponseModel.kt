package com.moviemania.data.model


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class GenresResponseModel(
    @SerializedName("genres")
    var genres: ArrayList<Genre>
) : Parcelable {
    @Parcelize
    data class Genre(
        @SerializedName("id")
        var id: Int,
        @SerializedName("name")
        var name: String
    ) : Parcelable
}