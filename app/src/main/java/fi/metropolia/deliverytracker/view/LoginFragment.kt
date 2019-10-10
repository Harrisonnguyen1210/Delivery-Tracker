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
import fi.metropolia.deliverytracker.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * Fragment screen for login user
 */
class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        logInButton.setOnClickListener {
            if (userNameText.text.isNotEmpty() && passwordText.text.isNotEmpty()) {
                val user = User(userNameText.text.toString(), passwordText.text.toString())
                viewModel.loginUser(user)
            }
        }
        registerAction.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.loginState.observe(this, Observer {
            it?.let {
                if(it) {
                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRequestsFragment())
                }
            }
        })

        viewModel.errorState.observe(this, Observer {
            it?.let {
                if(it) errorText.visibility = View.VISIBLE
                else errorText.visibility = View.GONE
            }
        })
    }
}
