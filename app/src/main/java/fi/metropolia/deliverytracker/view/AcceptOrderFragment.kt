package fi.metropolia.deliverytracker.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import fi.metropolia.deliverytracker.R
import fi.metropolia.deliverytracker.model.DeliveryTrackDatabase
import fi.metropolia.deliverytracker.model.RequestDao
import kotlinx.android.synthetic.main.fragment_accept_order.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class AcceptOrderFragment : Fragment(), CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main
    private lateinit var requestDao: RequestDao
    private var requestId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_accept_order, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requestDao = DeliveryTrackDatabase(context!!).requestDao()
        arguments?.let {
            requestId = AcceptOrderFragmentArgs.fromBundle(it).requestId
        }

        acceptButton.setOnClickListener {
            launch {
                requestDao.acceptRequest("Accepted", "delivery", requestId)
                Navigation.findNavController(it).navigateUp()
            }
        }
        cancelButton.setOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }
    }
}
