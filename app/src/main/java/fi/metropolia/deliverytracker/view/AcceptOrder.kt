package fi.metropolia.deliverytracker.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import fi.metropolia.deliverytracker.R
import kotlinx.android.synthetic.main.fragment_accept_order.*

class AcceptOrder : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_accept_order, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cancelButton.setOnClickListener {
            val action = AcceptOrderDirections.actionAcceptOrderToRequestDetail()
            Navigation.findNavController(it).navigate(action)
        }
    }

}
