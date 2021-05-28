package com.nixstudio.moviemax.core.utils

import androidx.paging.PagingData
import androidx.paging.map
import com.nixstudio.moviemax.core.data.entities.CombinedResultEntity
import com.nixstudio.moviemax.core.data.entities.FavoriteEntity
import com.nixstudio.moviemax.core.data.entities.MovieEntity
import com.nixstudio.moviemax.core.data.entities.TvShowsEntity
import com.nixstudio.moviemax.core.data.entities.utils.GenresItem
import com.nixstudio.moviemax.core.data.entities.utils.credits.CastItem
import com.nixstudio.moviemax.core.data.entities.utils.credits.Credits
import com.nixstudio.moviemax.core.data.entities.utils.reviews.ReviewsItem
import com.nixstudio.moviemax.core.data.entities.utils.reviews.ReviewsResponse
import com.nixstudio.moviemax.core.data.sources.remote.DiscoverMovieResultsItem
import com.nixstudio.moviemax.core.data.sources.remote.DiscoverTvResultsItem
import com.nixstudio.moviemax.core.domain.model.*

object DataMapper {
    fun mapMovieEntityToMovie(it: MovieEntity) = Movie(
        id = it.id,
        title = it.title,
        backdropPath = it.backdropPath,
        reviews = mapReviewToReviewList(it.reviews),
        credits = mapCreditsToCastList(it.credits),
        genres = mapGenresItemListToGenreList(it.genres),
        popularity = it.popularity,
        overview = it.overview,
        runtime = it.runtime,
        posterPath = it.posterPath,
        releaseDate = it.releaseDate,
        voteAverage = it.voteAverage,
    )

    fun mapTvShowsEntityToTvShow(it: TvShowsEntity) = TvShow(
        id = it.id,
        numberOfEpisodes = it.numberOfEpisodes,
        backdropPath = it.backdropPath,
        reviews = mapReviewToReviewList(it.reviews),
        credits = mapCreditsToCastList(it.credits),
        genres = mapGenresItemListToGenreList(it.genres),
        popularity = it.popularity,
        numberOfSeasons = it.numberOfSeasons,
        firstAirDate = it.firstAirDate,
        overview = it.overview,
        posterPath = it.posterPath,
        voteAverage = it.voteAverage,
        name = it.name,
        lastAirDate = it.lastAirDate,
    )

    fun mapCombinedEntityListToCombinedList(input: List<CombinedResultEntity>) = input.map {
        Combined(
            overview = it.overview,
            title = it.title,
            posterPath = it.posterPath,
            mediaType = it.mediaType,
            voteAverage = it.voteAverage,
            id = it.id,
            name = it.name
        )
    }

    fun mapDiscoverMovieListToMovieList(input: List<DiscoverMovieResultsItem>) = input.map {
        Movie(
            id = it.id,
            title = it.title,
            overview = it.overview,
            posterPath = it.posterPath,
        )
    }

    fun mapDiscoverTvListToTvShowList(input: List<DiscoverTvResultsItem>) = input.map {
        TvShow(
            id = it.id,
            name = it.name,
            overview = it.overview,
            posterPath = it.posterPath,
        )
    }

    fun mapFavoriteEntityListToFavoriteList(input: PagingData<FavoriteEntity>) = input.map {
        Favorite(
            itemId = it.itemId,
            mediaType = it.mediaType,
            title = it.title,
            posterPath = it.posterPath,
            createdAt = it.createdAt
        )
    }

    fun mapFavoriteToFavoriteEntity(input: Favorite) = FavoriteEntity(
        itemId = input.itemId,
        mediaType = input.mediaType,
        title = input.title,
        posterPath = input.posterPath,
        createdAt = input.createdAt
    )

    private fun mapReviewsItemToReviewList(input: List<ReviewsItem?>?): List<Review> {
        return if (!input.isNullOrEmpty()) {
            input.map {
                Review(
                    id = it?.id,
                    content = it?.content,
                    avatarPath = it?.authorDetails?.avatarPath,
                    name = it?.author,
                    rating = it?.authorDetails?.rating
                )
            }
        } else {
            return ArrayList()
        }
    }

    private fun mapReviewToReviewList(input: ReviewsResponse.Reviews?): List<Review> =
        mapReviewsItemToReviewList(
            input?.results
        )

    private fun mapCastItemToCast(input: CastItem) = Cast(
        castId = input.castId,
        id = input.id,
        name = input.name,
        profilePath = input.profilePath
    )

    private fun mapCreditsToCastList(input: Credits?): List<Cast> {
        val castList = ArrayList<Cast>()
        if (input != null) {
            if (input.cast != null) {
                for (cast in input.cast) {
                    if (cast != null) {
                        castList.add(mapCastItemToCast(cast))
                    }
                }
            }
        }

        return castList
    }

    private fun mapGenresItemListToGenreList(input: List<GenresItem?>?): List<Genre> {
        return if (!input.isNullOrEmpty()) {
            input.map {
                Genre(
                    id = it?.id,
                    name = it?.name
                )
            }
        } else {
            ArrayList()
        }
    }
}