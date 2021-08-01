package com.moviemania.ui.activity.mainActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviemania.data.DataRepository
import com.moviemania.data.model.GenresResponseModel
import com.moviemania.util.ViewTypeEnum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(private val dataRepository: DataRepository):ViewModel(){

    private val _viewTypeMutableData: MutableLiveData<ViewTypeEnum> = MutableLiveData()

    val viewTypeLiveData: LiveData<ViewTypeEnum>
        get() = _viewTypeMutableData

    private val _genreMutableData: MutableLiveData<ArrayList<GenresResponseModel.Genre>> = MutableLiveData()

    val genreLiveData: LiveData<ArrayList<GenresResponseModel.Genre>>
        get() = _genreMutableData

    fun changeView(viewType:ViewTypeEnum){

        _viewTypeMutableData.postValue(viewType)

    }

    fun getGenres(map:HashMap<String,Any>){
        var genresResponseModel: GenresResponseModel
        viewModelScope.launch {
            genresResponseModel = dataRepository.getGenres(map)

            if (!genresResponseModel.genres.isNullOrEmpty()){
                _genreMutableData.value = genresResponseModel.genres
            }
        }

    }

}