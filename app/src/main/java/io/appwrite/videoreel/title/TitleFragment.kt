package io.appwrite.videoreel.title

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.constraintlayout.widget.Group
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import io.appwrite.Client
import io.appwrite.models.Session
import io.appwrite.videoreel.R
import io.appwrite.videoreel.core.*
import io.appwrite.videoreel.databinding.FragmentTitleBinding

class TitleFragment : Fragment() {

    private val viewModel by viewModels<TitleViewModel> {
        ClientViewModelFactory(
            Client(requireContext())
                .setEndpoint(Configuration.ENDPOINT)
                .setProject(Configuration.PROJECT_ID)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val binding = FragmentTitleBinding.inflate(
            inflater,
            container,
            false
        )

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        val view = binding.root
        val inputs = view.findViewById<Group>(R.id.inputs)
        val pg = view.findViewById<ProgressBar>(R.id.progress)

        viewModel.isBusy.observe(viewLifecycleOwner) { showBusy(it, pg, inputs) }
        viewModel.message.observe(viewLifecycleOwner, ::showMessage)

        return view
    }

    private fun showBusy(enabled: Boolean, progress: View, inputs: Group) = if (enabled) {
        requireActivity().hideSoftKeyBoard()
        progress.visibility = View.VISIBLE
        inputs.visibility = View.INVISIBLE
    } else {
        progress.visibility = View.INVISIBLE
        inputs.visibility = View.VISIBLE
    }

    private fun navigateToLogin() {
        findNavController().navigate(
            TitleFragmentDirections.titleToLoginAction()
        )
    }

    private fun navigateToRegister() {
        findNavController().navigate(
            TitleFragmentDirections.titleToRegisterAction()
        )
    }

    private fun showMessage(message: Message) = when (message.type) {
        else -> {}
    }
}