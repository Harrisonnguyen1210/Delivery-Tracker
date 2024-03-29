package fi.metropolia.deliverytracker.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import fi.metropolia.deliverytracker.R
import kotlinx.android.synthetic.main.fragment_finish.*

/**
 * Fragment to notify user that delivery has finished
 */
class FinishFragment : Fragment() {
    private var requestId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_finish, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            requestId = FinishFragmentArgs.fromBundle(it).requestId
            stepText.text = getString(R.string.step_count_fact, FinishFragmentArgs.fromBundle(it).steps)
        }

        doneButton.setOnClickListener {
            //Navigate back to detail screen with updated status
            val action = FinishFragmentDirections.actionFinishFragmentToRequestDetail()
            action.requestId = requestId
            Navigation.findNavController(it).navigate(action)
        }
    }


}
