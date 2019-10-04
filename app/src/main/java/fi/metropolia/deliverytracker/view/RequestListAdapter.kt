package fi.metropolia.deliverytracker.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import fi.metropolia.deliverytracker.R
import fi.metropolia.deliverytracker.model.Request
import kotlinx.android.synthetic.main.request_item.view.*

class RequestListAdapter(private val requestList: ArrayList<Request>):
    RecyclerView.Adapter<RequestListAdapter.RequestViewHolder>(){

    fun updateRequestList(newRequestList: List<Request>) {
        requestList.clear()
        requestList.addAll(newRequestList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.request_item, parent, false)
        return RequestViewHolder(view)
    }

    override fun getItemCount(): Int {
        return requestList.size
    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {
        holder.view.setOnClickListener {
            val action = RequestsFragmentDirections.actionRequestsFragmentToRequestDetail()
            action.requestId = position
            Navigation.findNavController(it).navigate(action)
        }
        holder.view.requestTitle.text = requestList[position].destination
        holder.view.requestCustomer.text = requestList[position].customer
        holder.view.requestDeadline.text = requestList[position].deadline
    }

    class RequestViewHolder(var view: View): RecyclerView.ViewHolder(view)
}