package roman.bannikov.aston_6_rv.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import roman.bannikov.aston_6_rv.actions.*
import roman.bannikov.aston_6_rv.database.ContactsDatabase
import roman.bannikov.aston_6_rv.repository.ContactRepositoryImpl

class ContactsListViewModelFactory(
    context: Context
) : ViewModelProvider.Factory {

    private val db by lazy {
        ContactsDatabase(context = context)
    }

    private val contactRepository by lazy {
        ContactRepositoryImpl(db = db)
    }

    private val deleteContact by lazy {
        DeleteContact(contactRepository = contactRepository)
    }

    private val getAllContacts by lazy {
        GetAllContacts(contactRepository = contactRepository)
    }

    private val searchContact by lazy {
        SearchContact(contactRepository = contactRepository)
    }

    private val addAllContacts by lazy {
        AddAllContacts(contactRepository = contactRepository)
    }

    private val checkRoom by lazy {
        CheckRoom(contactRepository = contactRepository)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ContactsListViewModel(
            getAllContacts = getAllContacts,
            deleteContact = deleteContact,
            searchContact = searchContact,
            addAllContacts = addAllContacts,
            checkRoom = checkRoom
        ) as T
    }
}