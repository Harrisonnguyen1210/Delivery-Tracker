package fi.metropolia.deliverytracker.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import fi.metropolia.deliverytracker.R
import fi.metropolia.deliverytracker.databinding.RequestItemBinding
import fi.metropolia.deliverytracker.model.Request

class RequestListAdapter(private val requestList: ArrayList<Request>):
    RecyclerView.Adapter<RequestListAdapter.RequestViewHolder>(), RequestClickListener{

    fun updateRequestList(newRequestList: List<Request>) {
        requestList.clear()
        requestList.addAll(newRequestList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<RequestItemBinding>(inflater, R.layout.request_item, parent, false)
        return RequestViewHolder(view)
    }

    override fun getItemCount(): Int {
        return requestList.size
    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {
        holder.view.request = requestList[position]
        holder.view.requestId = position
        holder.view.listener = this
    }

    override fun onRequestClick(requestId: Int, v: View) {
        val action = RequestsFragmentDirections.actionRequestsFragmentToRequestDetail()
        action.requestId = requestId
        Navigation.findNavController(v).navigate(action)
    }

    class RequestViewHolder(var view: RequestItemBinding): RecyclerView.ViewHolder(view.root)
}