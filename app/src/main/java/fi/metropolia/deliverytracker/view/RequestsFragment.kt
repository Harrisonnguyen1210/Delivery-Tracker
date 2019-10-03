package fi.metropolia.deliverytracker.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import fi.metropolia.deliverytracker.R
import fi.metropolia.deliverytracker.viewmodel.RequestViewModel
import kotlinx.android.synthetic.main.fragment_requests.*

class RequestsFragment : Fragment() {

    private val requestAdapter = RequestListAdapter(arrayListOf())
    private lateinit var viewModel: RequestViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_requests, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(RequestViewModel::class.java)
        viewModel.refresh()

        requestRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = requestAdapter
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.requests.observe(this, Observer {
            it?.let {
                requestRecyclerView.visibility = View.VISIBLE
                requestAdapter.updateRequestList(it)
            }
        })

        viewModel.requestsLoadError.observe(this, Observer { isError ->
            isError?.let {
                listError.visibility = if(it) View.VISIBLE else View.GONE
            }
        })

        viewModel.loading.observe(this, Observer { isLoading ->
            isLoading?.let {
                progressBar.visibility = if(it) View.VISIBLE else View.GONE
                if(it) {
                    listError.visibility = View.GONE
                    requestRecyclerView.visibility = View.GONE
                }
            }
        })
    }
}
