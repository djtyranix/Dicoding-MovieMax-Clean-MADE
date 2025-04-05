package com.nixstudio.moviemax.core.data.sources.remote.api

import com.nixstudio.moviemax.core.data.entities.MovieEntity
import com.nixstudio.moviemax.core.data.entities.TvShowsEntity
import com.nixstudio.moviemax.core.data.sources.remote.DiscoverMovieResponse
import com.nixstudio.moviemax.core.data.sources.remote.DiscoverTvResponse
import com.nixstudio.moviemax.core.data.sources.remote.SearchResponse
import com.nixstudio.moviemax.core.data.sources.remote.TrendingResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("discover/movie")
    suspend fun getMovieDiscovery(
        @Query("api_key") api_key: String,
        @Query("sort_by") sort_by: String,
        @Query("include_adult") include_adult: Boolean,
        @Query("page") page: Int
    ): DiscoverMovieResponse

    @GET("discover/tv")
    suspend fun getTvDiscovery(
        @Query("api_key") api_key: String,
        @Query("sort_by") sort_by: String,
        @Query("include_adult") include_adult: Boolean,
        @Query("page") page: Int
    ): DiscoverTvResponse

    @GET("trending/all/day")
    suspend fun getTrendingToday(
        @Query("api_key") api_key: String
    ): TrendingResponse

    @GET("search/multi")
    suspend fun searchWithString(
        @Query("api_key") api_key: String,
        @Query("query") query: String,
        @Query("include_adult") include_adult: Boolean,
        @Query("page") page: Int
    ): SearchResponse

    @GET("movie/{id}")
    suspend fun getMovieById(
        @Path("id") id: Long,
        @Query("api_key") api_key: String,
        @Query("append_to_response") append_to_response: String
    ): MovieEntity

    @GET("tv/{id}")
    suspend fun getTvShowsById(
        @Path("id") id: Long,
        @Query("api_key") api_key: String,
        @Query("append_to_response") append_to_response: String
    ): TvShowsEntity
}