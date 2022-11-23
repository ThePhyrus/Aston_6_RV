package roman.bannikov.aston_6_rv.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import roman.bannikov.aston_6_rv.R
import roman.bannikov.aston_6_rv.listeners.OnCardClickListener
import roman.bannikov.aston_6_rv.model.Contact
import roman.bannikov.aston_6_rv.viewholders.ContactViewHolder

class ContactAdapter(
    var listener: OnCardClickListener
) : ListAdapter<Contact, ContactViewHolder>(ContactsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.contact_item, parent, false)

        return ContactViewHolder(itemView = itemView, listener = listener)
    }

    override fun onBindViewHolder(holderContacts: ContactViewHolder, position: Int) {
        holderContacts.onBind(getItem(position))
    }

    private class ContactsDiffCallback : DiffUtil.ItemCallback<Contact>() {

        override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem == newItem
        }
    }
}