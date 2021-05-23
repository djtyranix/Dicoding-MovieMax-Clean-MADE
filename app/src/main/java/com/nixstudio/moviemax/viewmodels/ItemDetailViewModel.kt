package com.nixstudio.moviemax.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.hadilq.liveevent.LiveEvent
import com.nixstudio.moviemax.domain.model.Movie
import com.nixstudio.moviemax.domain.model.TvShow
import com.nixstudio.moviemax.domain.usecase.MovieMaxUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ItemDetailViewModel(private val useCase: MovieMaxUseCase) : ViewModel() {

    private var _isBackdropLoading = MutableLiveData<Boolean>()
    private var _isPosterLoading = MutableLiveData<Boolean>()
    private var isUserExist = LiveEvent<Boolean>()

    fun setBackdropLoadingState(state: Boolean) {
        _isBackdropLoading.postValue(state)
    }

    fun setPosterLoadingState(state: Boolean) {
        _isPosterLoading.postValue(state)
    }

    fun stopLoading() {
        _isBackdropLoading.postValue(false)
        _isPosterLoading.postValue(false)
    }

    fun getCurrentMovie(id: Long): Flow<Movie> = useCase.getMovieById(id)

    fun getCurrentTvShows(id: Long): Flow<TvShow> = useCase.getTvShowsById(id)

    fun getLoadingState(): LiveData<Boolean> {
        return MediatorLiveData<Boolean>().apply {
            var isBackdropLoading: Boolean? = null
            var isPosterLoading: Boolean? = null

            fun update() {
                val localIsBackgroundLoading = isBackdropLoading
                val localIsPosterLoading = isPosterLoading
                if (localIsBackgroundLoading == localIsPosterLoading) {
                    this.value = localIsBackgroundLoading
                }
            }

            addSource(_isPosterLoading) {
                isPosterLoading = it
                update()
            }

            addSource(_isBackdropLoading) {
                isBackdropLoading = it
                update()
            }
        }
    }

    fun addFavorite(
        movie: Movie? = null,
        tvShow: TvShow? = null
    ) {
        viewModelScope.launch(Dispatchers.Default) {
            useCase.addFavorite(movie, tvShow)
        }
    }

    fun removeFavorite(
        movie: Movie? = null,
        tvShow: TvShow? = null
    ) {
        viewModelScope.launch(Dispatchers.Default) {
            useCase.removeFavorite(movie, tvShow)
        }
    }

    fun checkIfProfileFavorited(id: Long) {
        viewModelScope.launch(Dispatchers.Main) {
            val isExist = withContext(Dispatchers.Default) {
                try {
                    val count = useCase.checkIfFavoriteExist(id)

                    Log.d("count", count.toString())

                    if (count > 0) {
                        Log.d("return", true.toString())
                        return@withContext (true)
                    } else {
                        Log.d("return", false.toString())
                        return@withContext (false)
                    }
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            } as Boolean

            isUserExist.postValue(isExist)
        }
    }

    fun checkIsFavorited(): LiveData<Boolean> = isUserExist
}