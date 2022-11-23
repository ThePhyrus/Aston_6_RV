package roman.bannikov.aston_6_rv.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import roman.bannikov.aston_6_rv.actions.AddNewContact
import roman.bannikov.aston_6_rv.actions.UpdateContact
import roman.bannikov.aston_6_rv.database.ContactsDatabase
import roman.bannikov.aston_6_rv.repository.ContactRepositoryImpl

class ContactDetailsViewModelFactory(
    context: Context
) : ViewModelProvider.Factory {

    private val db by lazy {
        ContactsDatabase(context = context)
    }

    private val contactRepository by lazy {
        ContactRepositoryImpl(db = db)
    }

    private val updateContact by lazy {
        UpdateContact(contactRepository = contactRepository)
    }

    private val addNewContact by lazy {
        AddNewContact(contactRepository = contactRepository)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ContactDetailsViewModel(
            addNewContact = addNewContact,
            updateContact = updateContact
        ) as T
    }
}