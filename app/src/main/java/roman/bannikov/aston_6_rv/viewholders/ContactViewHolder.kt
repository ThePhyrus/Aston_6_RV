package roman.bannikov.aston_6_rv.viewholders


import android.app.AlertDialog
import android.content.DialogInterface
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import roman.bannikov.aston_6_rv.R
import roman.bannikov.aston_6_rv.databinding.ContactItemBinding
import roman.bannikov.aston_6_rv.listeners.OnCardClickListener
import roman.bannikov.aston_6_rv.model.Contact


class ContactViewHolder(
    itemView: View,
    private val listener: OnCardClickListener,
) : RecyclerView.ViewHolder(itemView) {

    private val binding = ContactItemBinding.bind(itemView)

    fun onBind(contact: Contact) {
        binding.tvNamePlaceholder.text = contact.name
        binding.tvLastnamePlaceholder.text = contact.lastname
        binding.tvPhoneNumberPlaceholder.text = contact.phoneNumber

        binding.cvContactPlaceholder.setOnClickListener {
            listener.onCardClick(contact = contact)
        }
        binding.cvContactPlaceholder.setOnLongClickListener {
            listener.onCardLongClick(id = contact.id)
        }

        CoroutineScope(context = Dispatchers.IO).launch {
            binding.ivContactImage.load("$BASE_URL${(contact.id?.times(1))}") {
                crossfade(true)
                placeholder(R.drawable.ic_image_placeholder)
                transformations(CircleCropTransformation())
            }
        }
    }

    companion object {
        private val TAG = ContactViewHolder::class.simpleName
        private const val BASE_URL = "https://picsum.photos/200/300?random"
    }


}