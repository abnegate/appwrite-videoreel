package io.appwrite.videoreel.show

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import io.appwrite.Client
import io.appwrite.videoreel.R
import io.appwrite.videoreel.content.ContentDetailViewModelFactory
import io.appwrite.videoreel.core.Configuration
import io.appwrite.videoreel.core.Message
import io.appwrite.videoreel.core.hideSoftKeyBoard
import io.appwrite.videoreel.databinding.FragmentShowDetailBinding


class ShowDetailFragment : Fragment() {

    private val args by navArgs<ShowDetailFragmentArgs>()

    private val viewModel by viewModels<ShowDetailViewModel> {
        ContentDetailViewModelFactory(
            client = Client(requireContext())
                .setEndpoint(Configuration.ENDPOINT)
                .setProject(Configuration.PROJECT_ID),
            movieId = null,
            showId = args.showId
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val binding = DataBindingUtil.inflate<FragmentShowDetailBinding>(
            inflater,
            R.layout.fragment_show_detail,
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