package com.nixstudio.moviemax.core.data.sources.remote

import android.util.Log
import com.nixstudio.moviemax.Secrets
import com.nixstudio.moviemax.core.data.entities.CombinedResultEntity
import com.nixstudio.moviemax.core.data.entities.MovieEntity
import com.nixstudio.moviemax.core.data.entities.TvShowsEntity
import com.nixstudio.moviemax.core.data.sources.remote.api.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteDataSource(private val api: ApiService) {
    private val apiKey = Secrets().getApiKey("com.nixstudio.moviemax")
    private val includeAdult = false
    private val sortBy = "popularity.desc"
    private val appendToResponse = "credits,reviews"

    fun getMoviesFromDiscovery(page: Int): Flow<List<DiscoverMovieResultsItem>> = flow {
        try {
            val response = api.getMovieDiscovery(apiKey, sortBy, includeAdult, page)
            val dataArray = response.results

            if (!dataArray.isNullOrEmpty()) {
                emit(dataArray as List<DiscoverMovieResultsItem>)
            } else {
                emit(ArrayList<DiscoverMovieResultsItem>())
            }
        } catch (e: Exception) {
            logError(e)
        }
    }

    fun getTvShowsFromDiscovery(page: Int): Flow<List<DiscoverTvResultsItem>> = flow {
        try {
            val response = api.getTvDiscovery(apiKey, sortBy, includeAdult, page)
            val dataArray = response.results

            if (!dataArray.isNullOrEmpty()) {
                emit(dataArray as List<DiscoverTvResultsItem>)
            } else {
                emit(ArrayList<DiscoverTvResultsItem>())
            }
        } catch (e: Exception) {
            logError(e)
        }
    }

    fun searchFromString(string: String, page: Int): Flow<List<CombinedResultEntity>> = flow {
        try {
            val response = api.searchWithString(apiKey, string, includeAdult, page)
            val dataArray = response.results

            if (!dataArray.isNullOrEmpty()) {
                emit(dataArray as List<CombinedResultEntity>)
            } else {
                emit(ArrayList<CombinedResultEntity>())
            }
        } catch (e: Exception) {
            logError(e)
        }
    }

    fun getTrendingToday(): Flow<List<CombinedResultEntity>> = flow {
        try {
            val response = api.getTrendingToday(apiKey)
            val dataArray = response.results

            if (!dataArray.isNullOrEmpty()) {
                emit(dataArray as List<CombinedResultEntity>)
            } else {
                emit(ArrayList<CombinedResultEntity>())
            }
        } catch (e: Exception) {
            logError(e)
        }
    }

    fun getMovieById(id: Long): Flow<MovieEntity> = flow {
        try {
            val response = api.getMovieById(id, apiKey, appendToResponse)
            emit(response)
        } catch (e: Exception) {
            logError(e)
        }
    }

    fun getTvShowsById(id: Long): Flow<TvShowsEntity> = flow {
        try {
            val response = api.getTvShowsById(id, apiKey, appendToResponse)
            emit(response)
        } catch (e: Exception) {
            logError(e)
        }
    }

    private fun logError(e: Exception) {
        Log.d("RemoteDataSource", e.toString())
    }
}