package fi.metropolia.deliverytracker.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import fi.metropolia.deliverytracker.R
import fi.metropolia.deliverytracker.model.Request
import kotlinx.android.synthetic.main.fragment_requests.*

class RequestsFragment : Fragment() {

    // Dummy data needs to be replaced when fetching real data
    private var requestList = arrayListOf(
        Request(
            1, "Not-accepted",
            "2019-07-30T18:09:16",
            "2019-10-20T08:52:42",
            "82 Albany Rd. Milton, MA 02186",
            "Ariana Grande",
            "FRAGILE! Please handle with care"
        ),
        Request(
            1, "Not-accepted",
            "2019-07-30T18:09:16",
            "2019-10-20T08:52:42",
            "82 Albany Rd. Milton, MA 02186",
            "Ariana Grande",
            "FRAGILE! Please handle with care"
        ),
        Request(
            1, "Not-accepted",
            "2019-07-30T18:09:16",
            "2019-10-20T08:52:42",
            "82 Albany Rd. Milton, MA 02186",
            "Ariana Grande",
            "FRAGILE! Please handle with care"
        )
    )
    private val requestAdapter = RequestListAdapter(requestList)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_requests, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requestRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = requestAdapter
        }
    }

}
