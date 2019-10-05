package fi.metropolia.deliverytracker.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import fi.metropolia.deliverytracker.R
import fi.metropolia.deliverytracker.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.fragment_request_detail.*

class DetailFragment : Fragment() {

    private lateinit var viewModel: DetailViewModel
    private var requestId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_request_detail, container, false)
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
            val action = DetailFragmentDirections.actionRequestDetailToAcceptOrder()
            Navigation.findNavController(it).navigate(action)
        }
    }

    private fun observeViewModel() {
        viewModel.requestDetail.observe(this, Observer {
            it?.let {
                customerText.text = it.customer
                startingDateText.text = it.startingDate
                deadlineText.text = it.deadline
                infoText.text = it.info
                statusText.text = it.status
                addressText.text = it.destination
            }
        })
    }
}
