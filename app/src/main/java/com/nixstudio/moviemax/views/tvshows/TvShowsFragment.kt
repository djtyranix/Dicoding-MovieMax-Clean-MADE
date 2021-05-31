package com.nixstudio.moviemax.views.tvshows

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.nixstudio.moviemax.core.domain.model.TvShow
import com.nixstudio.moviemax.core.ui.tvshow.TvShowsAdapter
import com.nixstudio.moviemax.core.utils.EspressoIdlingResource
import com.nixstudio.moviemax.databinding.TvShowsFragmentBinding
import com.nixstudio.moviemax.viewmodels.TvShowsViewModel
import com.nixstudio.moviemax.views.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class TvShowsFragment : Fragment() {

    private var _binding: TvShowsFragmentBinding? = null
    private val binding get() = _binding
    private val viewModel by viewModel<TvShowsViewModel>()
    private lateinit var viewAdapter: TvShowsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TvShowsFragmentBinding.inflate(inflater, container, false)

        lifecycleScope.launchWhenCreated {
            viewAdapter = TvShowsAdapter()
            viewAdapter.notifyDataSetChanged()

            binding?.rvAllTvShows?.apply {
                layoutManager = GridLayoutManager(activity, 2)
                adapter = viewAdapter
                setHasFixedSize(true)
            }

            viewAdapter.setOnItemClickCallback(object : TvShowsAdapter.OnItemClickCallback {
                override fun onItemClicked(data: TvShow) {
                    showMovieDetail(data)
                }
            })
        }

        val currentActivity = activity as MainActivity
        val toolbar = binding?.homeToolbar?.toolbarHome
        currentActivity.setSupportActionBar(toolbar)
        currentActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        currentActivity.setActionBarTitle("TV Shows")

        return binding?.root as ConstraintLayout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        EspressoIdlingResource.increment()
        lifecycleScope.launch(Dispatchers.Default) {
            viewModel.getTvShows().collectLatest { tvItem ->
                if (!tvItem.isNullOrEmpty()) {
                    if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow) {
                        //Memberitahukan bahwa tugas sudah selesai dijalankan
                        EspressoIdlingResource.decrement()
                    }

                    withContext(Dispatchers.Main) {
                        viewAdapter.setTv(tvItem)
                    }

                    Handler(Looper.getMainLooper()).postDelayed({
                        binding?.rvAllTvShows?.visibility = View.VISIBLE
                        binding?.textView?.visibility = View.VISIBLE
                        binding?.view2?.visibility = View.VISIBLE
                        binding?.tvShimmer?.visibility = View.GONE
                    }, 500)
                }
            }
        }
    }

    private fun showMovieDetail(data: TvShow) {
        val toDetailItemActivity =
            TvShowsFragmentDirections.actionTvShowsFragmentToItemDetailFragment(null, data)
        view?.findNavController()?.navigate(toDetailItemActivity)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}