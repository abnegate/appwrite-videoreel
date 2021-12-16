package io.appwrite.videoreel.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import io.appwrite.Client
import io.appwrite.videoreel.R
import io.appwrite.videoreel.core.ClientViewModelFactory
import io.appwrite.videoreel.core.Configuration
import io.appwrite.videoreel.core.Message
import io.appwrite.videoreel.core.hideSoftKeyBoard
import io.appwrite.videoreel.databinding.FragmentFeedBinding
import io.appwrite.videoreel.model.Movie
import io.appwrite.videoreel.model.Show

class FeedFragment : Fragment() {

    private val args by navArgs<FeedFragmentArgs>()

    private val viewModel by viewModels<FeedViewModel> {
        ClientViewModelFactory(
            Client(requireContext())
                .setEndpoint(Configuration.ENDPOINT)
                .setProject(Configuration.PROJECT_ID)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val binding = DataBindingUtil.inflate<FragmentFeedBinding>(
            inflater,
            R.layout.fragment_feed,
            container,
            false
        )

        val adapter = FeedCategoryAdapter()
        binding.categoryRecycler.adapter = adapter
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        val view = binding.root
        val progressBar = view.findViewById<ProgressBar>(R.id.progress)

        viewModel.movies.observe(viewLifecycleOwner) { movies ->
            val genres = movies.flatMap { it.genres }.toHashSet()
            val groups = genres.map { genre ->
                genre to movies.filter { movie ->
                    movie.genres.contains(genre)
                }
            }
            adapter.submitList(groups)
        }
        viewModel.shows.observe(viewLifecycleOwner) {

        }
        viewModel.isBusy.observe(viewLifecycleOwner) { showBusy(it, progressBar) }
        viewModel.message.observe(viewLifecycleOwner, ::showMessage)

        viewModel.getMovies()
        viewModel.getShows()

        return view
    }

    private fun showBusy(enabled: Boolean, progress: View) = if (enabled) {
        requireActivity().hideSoftKeyBoard()
        progress.visibility = View.VISIBLE
    } else {
        progress.visibility = View.INVISIBLE
    }

    private fun navigateToMovieDetail(movie: Movie) {
        findNavController().navigate(
            FeedFragmentDirections.feedToMovieDetailAction(movie.id)
        )
    }

    private fun navigateToShowDetail(show: Show) {
        findNavController().navigate(
            FeedFragmentDirections.feedToShowDetailAction(show.id)
        )
    }

    private fun showMessage(message: Message) = when (message.type) {
        else -> {}
    }
}