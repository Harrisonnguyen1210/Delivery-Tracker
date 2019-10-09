package fi.metropolia.deliverytracker.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import fi.metropolia.deliverytracker.R
import fi.metropolia.deliverytracker.model.User
import fi.metropolia.deliverytracker.viewmodel.RegisterViewModel
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : Fragment() {

    private lateinit var viewModel: RegisterViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(RegisterViewModel::class.java)

        registerButton.setOnClickListener {
            if(userNameText.text.isNotEmpty() && passwordText.text.isNotEmpty()) {
                viewModel.registerUser(User(userNameText.text.toString(), passwordText.text.toString()))
            }
        }
        loginAction.setOnClickListener {
            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.registerState.observe(this, Observer {
            it?.let {
                if(it) {
                    findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToRequestsFragment())
                }
            }
        })

        viewModel.errorState.observe(this, Observer {
            it?.let {
                if(it) {
                    errorText.visibility = View.VISIBLE
                }
            }
        })
    }
}
