package fi.metropolia.deliverytracker.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import fi.metropolia.deliverytracker.R
import fi.metropolia.deliverytracker.databinding.FragmentRequestDetailBinding
import fi.metropolia.deliverytracker.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.fragment_login.view.*
import kotlinx.android.synthetic.main.fragment_request_detail.*

class DetailFragment : Fragment() {

    private lateinit var viewModel: DetailViewModel
    private lateinit var dataBinding: FragmentRequestDetailBinding
    private var requestId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_request_detail, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            requestId = DetailFragmentArgs.fromBundle(it).requestId
        }
        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        viewModel.fetch(requestId)
        observeViewModel()
        acceptButton.setOnClickListener {
            if(acceptButton.text == "Start delivery") {
                val action = DetailFragmentDirections.actionRequestDetailToGoogleMapFragment()
                action.detination = dataBinding.request!!.destination
                Navigation.findNavController(it).navigate(action)
            } else {
                val action = DetailFragmentDirections.actionRequestDetailToAcceptOrder()
                action.requestId = requestId
                Navigation.findNavController(it).navigate(action)
            }
        }
    }

    private fun observeViewModel() {
        viewModel.requestDetail.observe(this, Observer {
            it?.let {
                dataBinding.request = it
            }
        })

        viewModel.startDeliveryState.observe(this, Observer {
            it?.let {
                when (it) {
                    0 -> {
                        dataBinding.startDeliverState = "Accept"
                        dataBinding.acceptButtonState = true
                    }
                    1 -> {
                        dataBinding.startDeliverState = "Start delivery"
                        dataBinding.acceptButtonState = true
                    }
                    else -> {
                        dataBinding.startDeliverState = "Already delivered"
                        dataBinding.acceptButtonState = false
                    }
                }
            }
        })
    }
}
