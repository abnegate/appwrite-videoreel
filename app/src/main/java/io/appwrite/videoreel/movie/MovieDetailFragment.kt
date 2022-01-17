package io.appwrite.videoreel.movie

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.transition.MaterialContainerTransform
import io.appwrite.Client
import io.appwrite.videoreel.R
import io.appwrite.videoreel.content.ContentDetailViewModelFactory
import io.appwrite.videoreel.core.Configuration
import io.appwrite.videoreel.core.Message
import io.appwrite.videoreel.core.hideSoftKeyBoard
import io.appwrite.videoreel.databinding.FragmentMovieDetailBinding

class MovieDetailFragment : Fragment() {

    private val args by navArgs<MovieDetailFragmentArgs>()

    private val viewModel by viewModels<MovieDetailViewModel> {
        ContentDetailViewModelFactory(
            client = Client(requireContext())
                .setEndpoint(Configuration.ENDPOINT)
                .setProject(Configuration.PROJECT_ID),
            movieId = args.movieId
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        sharedElementEnterTransition = MaterialContainerTransform().apply {
//            drawingViewId = R.id.nav_host_fragment
//            duration = 1200.toLong()
//            scrimColor = Color.TRANSPARENT
//
//            val typedArray = requireContext().obtainStyledAttributes(
//                intArrayOf(R.attr.colorSurface)
//            )
//            val colorSet = typedArray.getColor(0, Color.MAGENTA)
//            setAllContainerColors(colorSet)
//            typedArray.recycle()
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val binding = FragmentMovieDetailBinding.inflate(
            inflater,
            container,
            false
        )

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        val view = binding.root
        val pg = view.findViewById<ProgressBar>(R.id.progress)

        viewModel.isBusy.observe(viewLifecycleOwner) { showBusy(it, pg) }
        viewModel.message.observe(viewLifecycleOwner, ::showMessage)

        return view
    }

    private fun showBusy(enabled: Boolean, progress: View) = if (enabled) {
        requireActivity().hideSoftKeyBoard()
        progress.visibility = View.VISIBLE
    } else {
        progress.visibility = View.INVISIBLE
    }

    private fun showMessage(message: Message) = when (message.type) {
        else -> {}
    }
}