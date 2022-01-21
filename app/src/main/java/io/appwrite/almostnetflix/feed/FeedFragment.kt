package io.appwrite.almostnetflix.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.transition.MaterialElevationScale
import io.appwrite.almostnetflix.R
import io.appwrite.almostnetflix.core.ClientViewModelFactory
import io.appwrite.almostnetflix.core.Configuration
import io.appwrite.almostnetflix.core.Message
import io.appwrite.almostnetflix.core.hideSoftKeyBoard
import io.appwrite.almostnetflix.databinding.FragmentFeedBinding
import io.appwrite.almostnetflix.model.Movie

class FeedFragment : Fragment() {

    private val args by navArgs<FeedFragmentArgs>()

    private val viewModel by viewModels<FeedViewModel> {
        ClientViewModelFactory(Configuration.client)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val binding = FragmentFeedBinding.inflate(
            inflater,
            container,
            false
        )

        val adapter = FeedCategoryAdapter(viewModel::contentSelected)

        binding.categoryRecycler.adapter = adapter
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        val view = binding.root
        val progressBar = view.findViewById<ProgressBar>(R.id.progress)

        viewModel.moviesByCategory.observe(viewLifecycleOwner) {
            adapter.submitList(it.entries.toList())
        }
        viewModel.selectedMovie.observe(viewLifecycleOwner) {
            navigateToMovieDetail(it.first, it.second)
        }
        viewModel.isBusy.observe(viewLifecycleOwner) {
            showBusy(it, progressBar)
        }
        viewModel.message.observe(viewLifecycleOwner, ::showMessage)

        viewModel.getMovies()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
    }

    private fun showBusy(enabled: Boolean, progress: View) = if (enabled) {
        requireActivity().hideSoftKeyBoard()
        progress.visibility = View.VISIBLE
    } else {
        progress.visibility = View.INVISIBLE
    }

    private fun navigateToMovieDetail(view: View, movie: Movie) {
        val detailTransitionName = getString(R.string.content_card_detail_transition_name)

        exitTransition = MaterialElevationScale(false).apply {
            duration = 1000.toLong()
        }
        reenterTransition = MaterialElevationScale(true).apply {
            duration = 1000.toLong()
        }

        findNavController().navigate(
            FeedFragmentDirections.feedToMovieDetailAction(movie.id),
            FragmentNavigatorExtras(view to detailTransitionName)
        )
    }

    private fun showMessage(message: Message) = when (message.type) {
        else -> {}
    }
}