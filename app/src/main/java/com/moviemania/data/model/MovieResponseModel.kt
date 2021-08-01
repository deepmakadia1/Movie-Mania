package com.moviemania.data.model


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.moviemania.BuildConfig

@Parcelize
data class MovieResponseModel(
    @SerializedName("page")
    var page: Int = 0,
    @SerializedName("results")
    var results: ArrayList<MovieModel>,
    @SerializedName("total_pages")
    var totalPages: Int = 0,
    @SerializedName("total_results")
    var totalResults: Int = 0
) : Parcelable {

    @Entity(tableName = "movie_list")
    @Parcelize
    data class MovieModel(
        @SerializedName("backdrop_path")
        @ColumnInfo(name = "backdrop_path")
        var backdropPath: String? ,

        @SerializedName("id")
        @ColumnInfo(name = "id")
        @PrimaryKey(autoGenerate = false)
        var id: Int,

        @SerializedName("original_language")
        @ColumnInfo(name = "original_language")
        var originalLanguage: String?,

        @SerializedName("original_title")
        @ColumnInfo(name = "original_title")
        var originalTitle: String?,

        @SerializedName("overview")
        @ColumnInfo(name = "overview")
        var overview: String?,

        @SerializedName("popularity")
        @ColumnInfo(name = "popularity")
        var popularity: Double,

        @SerializedName("poster_path")
        @ColumnInfo(name = "poster_path")
        var posterPath: String?,

        @SerializedName("release_date")
        @ColumnInfo(name = "release_date")
        var releaseDate: String?,

        @SerializedName("title")
        @ColumnInfo(name = "title")
        var title: String?,

        @SerializedName("vote_average")
        @ColumnInfo(name = "vote_average")
        var voteAverage: Double,

        @SerializedName("vote_count")
        @ColumnInfo(name = "vote_count")
        var voteCount: Int,

        @SerializedName("category")
        @ColumnInfo(name = "category")
        var category: String?

    ) : Parcelable{
        fun getFullPosterUrl() : String{
            return BuildConfig.IMAGE_URL + posterPath
        }

        fun getFullBackDropUrl() : String{
            return BuildConfig.IMAGE_URL + backdropPath
        }
    }
}