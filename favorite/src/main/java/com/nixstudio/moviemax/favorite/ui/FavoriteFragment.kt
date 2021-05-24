package com.nixstudio.moviemax.favorite.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.nixstudio.moviemax.core.data.entities.utils.MediaType
import com.nixstudio.moviemax.core.domain.model.Favorite
import com.nixstudio.moviemax.core.domain.model.Movie
import com.nixstudio.moviemax.core.domain.model.TvShow
import com.nixstudio.moviemax.core.utils.EspressoIdlingResource
import com.nixstudio.moviemax.favorite.R
import com.nixstudio.moviemax.favorite.databinding.FavoriteFragmentBinding
import com.nixstudio.moviemax.favorite.module.favoriteModule
import com.nixstudio.moviemax.favorite.viewmodel.FavoriteViewModel
import com.nixstudio.moviemax.views.MainActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteFragment : Fragment() {

    private var _binding: FavoriteFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<FavoriteViewModel>()
    private lateinit var viewAdapter: FavoriteAdapter
    private var isSpinnerInitialized = false

    override fun onCreate(savedInstanceState: Bundle?) {
        loadKoinModules(favoriteModule)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FavoriteFragmentBinding.inflate(inflater, container, false)

        lifecycleScope.launchWhenCreated {
            viewAdapter = FavoriteAdapter()
            viewAdapter.notifyDataSetChanged()

            binding.rvFavorite.apply {
                layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                adapter = viewAdapter
                setHasFixedSize(true)
            }

            viewAdapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback {
                override fun onItemClicked(data: Favorite) {
                    if (data.mediaType == "movie") {
                        val movie = Movie(
                            title = data.title,
                            posterPath = data.posterPath,
                            id = data.itemId,
                        )

                        showMovieDetail(movie)
                    } else {
                        val tvShow = TvShow(
                            posterPath = data.posterPath,
                            id = data.itemId,
                            name = data.title
                        )

                        showTvDetail(tvShow)
                    }
                }
            })
        }

        EspressoIdlingResource.increment()
        lifecycleScope.launch {
            viewModel.getFavorites().collectLatest {
                if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow) {
                    EspressoIdlingResource.decrement()
                }

                viewAdapter.submitData(it)
            }
        }

        binding.sortSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (!isSpinnerInitialized) {
                    isSpinnerInitialized = true
                    return
                }

                lifecycleScope.launch {
                    when (id) {
                        0L -> {
                            viewModel.getFavorites().collectLatest {
                                if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow) {
                                    EspressoIdlingResource.decrement()
                                }

                                viewAdapter.submitData(it)
                            }
                        }
                        1L -> {
                            viewModel.getFavoritesByMediaType(MediaType.MOVIE)
                                .collectLatest {
                                    if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow) {
                                        EspressoIdlingResource.decrement()
                                    }

                                    viewAdapter.submitData(it)
                                }
                        }
                        else -> {
                            viewModel.getFavoritesByMediaType(MediaType.TVSHOW)
                                .collectLatest {
                                    if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow) {
                                        EspressoIdlingResource.decrement()
                                    }

                                    viewAdapter.submitData(it)
                                }
                        }
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                return
            }
        }

        EspressoIdlingResource.increment()
        lifecycleScope.launch {
            viewAdapter.loadStateFlow.collectLatest {
                if (it.refresh is LoadState.NotLoading) {
                    checkIsListEmpty(viewAdapter.itemCount)
                }
            }
        }

        val currentActivity = activity as MainActivity
        val toolbar = binding.homeToolbar.toolbarHome
        currentActivity.setSupportActionBar(toolbar)
        currentActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        currentActivity.setActionBarTitle(resources.getString(R.string.favorite))

        return binding.root
    }

    private fun checkIsListEmpty(count: Int) {
        if (count > 0) {
            Handler(Looper.getMainLooper()).postDelayed({
                binding.rvFavorite.visibility = View.VISIBLE
                binding.view2.visibility = View.VISIBLE
                binding.sortSpinner.visibility = View.VISIBLE
                binding.textView.visibility = View.VISIBLE
                binding.rvFavoriteShimmer.visibility = View.GONE
                binding.emptyFavoritePlaceholder.visibility = View.GONE
                binding.emptyFavoriteInfo.visibility = View.GONE
            }, 500)
        } else {
            Handler(Looper.getMainLooper()).postDelayed({
                binding.textView.visibility = View.VISIBLE
                binding.view2.visibility = View.VISIBLE
                binding.sortSpinner.visibility = View.VISIBLE
                binding.rvFavoriteShimmer.visibility = View.GONE
                binding.rvFavorite.visibility = View.GONE
                binding.emptyFavoritePlaceholder.visibility = View.VISIBLE
                binding.emptyFavoriteInfo.visibility = View.VISIBLE
            }, 500)
        }
    }

    private fun showMovieDetail(data: Movie) {
        val toDetailItemActivity =
            FavoriteFragmentDirections.actionFavoriteFragmentToItemDetailFragment(data, null)
        view?.findNavController()?.navigate(toDetailItemActivity)
    }

    private fun showTvDetail(data: TvShow) {
        val toDetailItemActivity =
            FavoriteFragmentDirections.actionFavoriteFragmentToItemDetailFragment(null, data)
        view?.findNavController()?.navigate(toDetailItemActivity)
    }
}