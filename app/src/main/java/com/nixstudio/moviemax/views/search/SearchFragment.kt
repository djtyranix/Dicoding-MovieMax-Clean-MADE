package com.nixstudio.moviemax.views.search

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.nixstudio.moviemax.R
import com.nixstudio.moviemax.core.domain.model.Combined
import com.nixstudio.moviemax.core.domain.model.Movie
import com.nixstudio.moviemax.core.domain.model.TvShow
import com.nixstudio.moviemax.core.ui.search.SearchResultAdapter
import com.nixstudio.moviemax.core.utils.EspressoIdlingResource
import com.nixstudio.moviemax.core.utils.hideKeyboard
import com.nixstudio.moviemax.databinding.SearchFragmentBinding
import com.nixstudio.moviemax.viewmodels.SearchViewModel
import com.nixstudio.moviemax.views.MainActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    private var _binding: SearchFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<SearchViewModel>()
    private lateinit var viewAdapter: SearchResultAdapter
    private val args: SearchFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SearchFragmentBinding.inflate(inflater, container, false)

        lifecycleScope.launchWhenCreated {
            viewAdapter = SearchResultAdapter()
            viewAdapter.notifyDataSetChanged()

            binding.rvSearchResult.apply {
                layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                adapter = viewAdapter
                setHasFixedSize(true)
            }

            viewAdapter.setOnItemClickCallback(object : SearchResultAdapter.OnItemClickCallback {
                override fun onItemClicked(data: Combined) {
                    if (data.mediaType == "movie") {
                        val movie = Movie(
                            overview = data.overview,
                            title = data.title,
                            posterPath = data.posterPath,
                            voteAverage = data.voteAverage,
                            id = data.id,
                        )

                        showMovieDetail(movie)
                    } else {
                        val tvShow = TvShow(
                            overview = data.overview,
                            posterPath = data.posterPath,
                            voteAverage = data.voteAverage,
                            id = data.id,
                            name = data.name
                        )

                        showTvDetail(tvShow)
                    }
                }
            })

            hideKeyboard()
        }

        EspressoIdlingResource.increment()
        lifecycleScope.launch {
            viewModel.getSearchResults(args.query).collectLatest { item ->
                if (!item.isNullOrEmpty()) {
                    if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow) {
                        //Memberitahukan bahwa tugas sudah selesai dijalankan
                        EspressoIdlingResource.decrement()
                    }
                    viewAdapter.setSearchResult(item)

                    Handler(Looper.getMainLooper()).postDelayed({
                        binding.rvSearchResult.visibility = View.VISIBLE
                        binding.rvSearchShimmer.visibility = View.GONE
                    }, 500)
                } else {
                    if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow) {
                        //Memberitahukan bahwa tugas sudah selesai dijalankan
                        EspressoIdlingResource.decrement()
                    }

                    Handler(Looper.getMainLooper()).postDelayed({
                        binding.rvSearchShimmer.visibility = View.GONE
                        binding.rvSearchResult.visibility = View.GONE
                        binding.emptySearchPlaceholder.visibility = View.VISIBLE
                        binding.emptySearchInfo.visibility = View.VISIBLE
                    }, 500)
                }
            }
        }

        val currentActivity = activity as MainActivity
        val toolbar = binding.homeToolbar.toolbarHome
        currentActivity.setSupportActionBar(toolbar)
        currentActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        currentActivity.setActionBarTitle(
            resources.getString(
                R.string.search_result_header,
                args.query
            )
        )

        return binding.root
    }

    private fun showMovieDetail(data: Movie) {
        val toDetailItemActivity =
            SearchFragmentDirections.actionSearchFragmentToItemDetailFragment(data, null)
        view?.findNavController()?.navigate(toDetailItemActivity)
    }

    private fun showTvDetail(data: TvShow) {
        val toDetailItemActivity =
            SearchFragmentDirections.actionSearchFragmentToItemDetailFragment(null, data)
        view?.findNavController()?.navigate(toDetailItemActivity)
    }
}