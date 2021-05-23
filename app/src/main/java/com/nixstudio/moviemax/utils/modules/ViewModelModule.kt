package com.nixstudio.moviemax.utils.modules

import com.nixstudio.moviemax.viewmodels.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        HomeViewModel(get())
    }

    viewModel {
        SearchViewModel(get())
    }

    viewModel {
        MovieViewModel(get())
    }

    viewModel {
        TvShowsViewModel(get())
    }

    viewModel {
        ItemDetailViewModel(get())
    }

    viewModel {
        FavoriteViewModel(get())
    }
}