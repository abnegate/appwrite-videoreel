package io.appwrite.almostnetflix.consume

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import io.appwrite.almostnetflix.R

class ConsumeContentFragment : Fragment() {

    companion object {
        fun newInstance() = ConsumeContentFragment()
    }

    private lateinit var viewModel: ConsumeContentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_consume_content, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ConsumeContentViewModel::class.java)
        // TODO: Use the ViewModel
    }

}