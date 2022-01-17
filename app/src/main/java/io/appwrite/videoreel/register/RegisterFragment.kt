package io.appwrite.videoreel.register

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
import io.appwrite.videoreel.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private val viewModel by viewModels<RegisterViewModel> {
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
        val binding = FragmentRegisterBinding.inflate(
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
        viewModel.session.observe(viewLifecycleOwner, ::navigateToFeed)
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

    private fun navigateToFeed(session: Session) {
        findNavController().navigate(
            RegisterFragmentDirections.registerToFeedAction(session.userId, session.id)
        )
    }

    private fun showMessage(message: Message) = when (message.type) {
        Messages.USER_NAME_INVALID -> {
            Snackbar.make(requireView(), R.string.user_name_invalid, Snackbar.LENGTH_LONG).show()
        }
        Messages.USER_PASSWORD_INVALID -> {
            Snackbar.make(requireView(), R.string.user_password_invalid, Snackbar.LENGTH_LONG)
                .show()
        }
        Messages.CREATE_SESSION_FAILED -> {
            Snackbar.make(requireView(), R.string.create_session_failed, Snackbar.LENGTH_LONG)
                .show()
        }
        else -> {
        }
    }
}