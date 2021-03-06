package com.nixstudio.moviemax.views.home

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nixstudio.moviemax.R
import com.nixstudio.moviemax.core.domain.model.Combined
import com.nixstudio.moviemax.core.domain.model.Movie
import com.nixstudio.moviemax.core.domain.model.TvShow
import com.nixstudio.moviemax.core.ui.home.HomeMovieAdapter
import com.nixstudio.moviemax.core.ui.home.HomeTrendingAdapter
import com.nixstudio.moviemax.core.ui.home.HomeTvAdapter
import com.nixstudio.moviemax.core.utils.EspressoIdlingResource
import com.nixstudio.moviemax.core.utils.hideKeyboard
import com.nixstudio.moviemax.databinding.FragmentHomeBinding
import com.nixstudio.moviemax.viewmodels.HomeViewModel
import com.nixstudio.moviemax.views.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<HomeViewModel>()
    private lateinit var trendingViewAdapter: HomeTrendingAdapter
    private lateinit var movieViewAdapter: HomeMovieAdapter
    private lateinit var tvViewAdapter: HomeTvAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        lifecycleScope.launchWhenCreated {
            setHasOptionsMenu(true)

            trendingViewAdapter = HomeTrendingAdapter()
            movieViewAdapter = HomeMovieAdapter()
            tvViewAdapter = HomeTvAdapter()
            trendingViewAdapter.notifyDataSetChanged()
            movieViewAdapter.notifyDataSetChanged()
            tvViewAdapter.notifyDataSetChanged()

            binding.rvTrending.apply {
                layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                adapter = trendingViewAdapter
                setHasFixedSize(true)
            }

            binding.rvMovie.apply {
                layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                adapter = movieViewAdapter
                setHasFixedSize(true)
            }

            binding.rvTvshows.apply {
                layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                adapter = tvViewAdapter
                setHasFixedSize(true)
            }

            val searchManager =
                (activity as MainActivity).getSystemService(Context.SEARCH_SERVICE) as SearchManager
            val searchView = binding.svSearchItem

            searchView.setSearchableInfo(searchManager.getSearchableInfo((activity as MainActivity).componentName))
            searchView.setIconifiedByDefault(false)
            searchView.queryHint = resources.getString(R.string.search_hint)

            searchView.clearFocus()

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String?): Boolean {

                    return true
                }

                override fun onQueryTextSubmit(query: String?): Boolean {

                    searchView.clearFocus()

                    if (query != null && query != "" && query != " ") {
                        val toSearchFragment =
                            HomeFragmentDirections.actionHomeFragmentToSearchFragment(query)
                        view?.findNavController()?.navigate(toSearchFragment)
                    } else {
                        Toast.makeText(
                            activity,
                            resources.getString(R.string.query_empty_warning),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    return true
                }
            })

            searchView.setOnCloseListener {
                Log.d("Closed", "Closed")
                true
            }
        }

        hideKeyboard()

        val currentActivity = activity as MainActivity?
        val toolbar = binding.homeToolbar.toolbarHome
        currentActivity?.setSupportActionBar(toolbar)
        currentActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        currentActivity?.setActionBarTitle(" ")

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        EspressoIdlingResource.increment()
        EspressoIdlingResource.increment()
        EspressoIdlingResource.increment()

        lifecycleScope.launch(Dispatchers.Default) {
            binding.seeAllMovies.setOnClickListener(this@HomeFragment)
            binding.seeAllTv.setOnClickListener(this@HomeFragment)

            trendingViewAdapter.setOnItemClickCallback(object :
                HomeTrendingAdapter.OnItemClickCallback {
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

            movieViewAdapter.setOnItemClickCallback(object : HomeMovieAdapter.OnItemClickCallback {
                override fun onItemClicked(data: Movie) {
                    showMovieDetail(data)
                }
            })

            tvViewAdapter.setOnItemClickCallback(object : HomeTvAdapter.OnItemClickCallback {
                override fun onItemClicked(data: TvShow) {
                    showTvDetail(data)
                }
            })

            viewModel.getTrending().collectLatest { item ->
                if (!item.isNullOrEmpty()) {
                    if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow) {
                        //Memberitahukan bahwa tugas sudah selesai dijalankan
                        EspressoIdlingResource.decrement()
                    }
                    withContext(Dispatchers.Main) {
                        trendingViewAdapter.setTrendingData(item.take(7))

                        Handler(Looper.getMainLooper()).postDelayed({
                            binding.rvTrending.visibility = View.VISIBLE
                            binding.rvTrendingShimmer.visibility = View.GONE
                        }, 300)
                    }
                }
            }

            viewModel.getMovies().collectLatest { movieItem ->
                if (!movieItem.isNullOrEmpty()) {
                    if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow) {
                        //Memberitahukan bahwa tugas sudah selesai dijalankan
                        EspressoIdlingResource.decrement()
                    }
                    withContext(Dispatchers.Main) {
                        movieViewAdapter.setMovies(movieItem.take(7))

                        Handler(Looper.getMainLooper()).postDelayed({
                            binding.rvMovie.visibility = View.VISIBLE
                            binding.rvMovieShimmer.visibility = View.GONE
                        }, 300)
                    }
                }
            }

            viewModel.getTvShows().collectLatest { tvItem ->
                if (!tvItem.isNullOrEmpty()) {
                    if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow) {
                        //Memberitahukan bahwa tugas sudah selesai dijalankan
                        EspressoIdlingResource.decrement()
                    }

                    withContext(Dispatchers.Main) {
                        tvViewAdapter.setTv(tvItem.take(7))

                        Handler(Looper.getMainLooper()).postDelayed({
                            binding.rvTvshows.visibility = View.VISIBLE
                            binding.rvTvShimmer.visibility = View.GONE
                        }, 300)
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.setting_menu) {
            val toSettingActivity = HomeFragmentDirections.actionHomeFragmentToSettingsActivity()
            view?.findNavController()?.navigate(toSettingActivity)
        } else if (item.itemId == R.id.favorite) {
            val toFavoriteFragment = HomeFragmentDirections.actionHomeFragmentToFavoriteFragment()
            view?.findNavController()?.navigate(toFavoriteFragment)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showMovieDetail(data: Movie) {
        val toDetailItemActivity =
            HomeFragmentDirections.actionHomeFragmentToItemDetailFragment(data, null)
        view?.findNavController()?.navigate(toDetailItemActivity)
    }

    private fun showTvDetail(data: TvShow) {
        val toDetailItemActivity =
            HomeFragmentDirections.actionHomeFragmentToItemDetailFragment(null, data)
        view?.findNavController()?.navigate(toDetailItemActivity)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.see_all_movies -> {
                val toAllMovie = HomeFragmentDirections.actionHomeFragmentToMovieFragment()
                v.findNavController().navigate(toAllMovie)
            }

            R.id.see_all_tv -> {
                val toAllTv = HomeFragmentDirections.actionHomeFragmentToTvShowsFragment()
                v.findNavController().navigate(toAllTv)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}