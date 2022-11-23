package roman.bannikov.aston_6_rv.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.javafaker.Faker
import kotlinx.coroutines.launch
import roman.bannikov.aston_6_rv.actions.*
import roman.bannikov.aston_6_rv.domain.ContactDomain
import roman.bannikov.aston_6_rv.listeners.OnCardClickListener
import roman.bannikov.aston_6_rv.model.Contact
import java.util.*

class ContactsListViewModel(
    private val addAllContacts: AddAllContacts,
    private val getAllContacts: GetAllContacts,
    private val searchContact: SearchContact,
    private val deleteContact: DeleteContact,
    private val checkRoom: CheckRoom
) : ViewModel(), OnCardClickListener {

    var listContacts: MutableLiveData<List<Contact>?> = MutableLiveData<List<Contact>?>()
    var alertDialogLiveData = MutableLiveData<Int?>()
    var transferContact = MutableLiveData<Contact?>()


    fun checkRoom() {
        viewModelScope.launch {
            if (checkRoom.execute()) {
                addAllContacts.execute(contactsList = mapToListContactsDomain(generateFakeContacts()))
                listContacts.value = mapToListContact(getAllContacts.execute())
            }
        }
    }


    fun getContactsList() {
        viewModelScope.launch {
            listContacts.value = mapToListContact(getAllContacts.execute())
        }
    }


    fun searchContact(searchRequest: String) {
        viewModelScope.launch {
            listContacts.value =
                mapToListContact(searchContact.execute(searchRequest = searchRequest))
        }
    }


    fun getTransferContact(): LiveData<Contact?> {
        return transferContact
    }


    override fun onCardClick(contact: Contact) {
        transferContact.value = contact
    }


    override fun onCardLongClick(id: Int?): Boolean {
        alertDialogLiveData.value = id
        return true
    }


    fun deleteContact(id: Int?) {
        try {
            viewModelScope.launch {
                deleteContact.execute(id = id)
                getContactsList()
            }
        } catch (e: Exception) {
            Log.d(TAG, "deleteContact: $e")
        }
    }


    private fun mapToListContact(allContacts: List<ContactDomain>?): List<Contact>? {
        return allContacts?.map { mapToContact(it) }
    }


    private fun mapToListContactsDomain(allContacts: List<Contact>): List<ContactDomain> {
        return allContacts.map { mapToContactDomain(it) }
    }


    private fun mapToContact(contactDomain: ContactDomain): Contact {
        return Contact(
            id = contactDomain.id,
            name = contactDomain.name,
            lastname = contactDomain.lastname,
            phoneNumber = contactDomain.phoneNumber
        )
    }


    private fun mapToContactDomain(contact: Contact): ContactDomain {
        return ContactDomain(
            id = contact.id,
            name = contact.name,
            lastname = contact.lastname,
            phoneNumber = contact.phoneNumber
        )
    }


    private fun generateFakeContacts(): List<Contact> {
        val contactsList = mutableListOf<Contact>()
        val faker = Faker(Locale(LOCALE_RU))
        for (i in 1..101) {
            contactsList.add(
                Contact(
                    id = i,
                    name = faker.name().firstName(),
                    lastname = faker.name().lastName(),
                    phoneNumber = faker.phoneNumber().phoneNumber()
                )
            )
        }
        return contactsList
    }

    companion object {
        private const val LOCALE_RU = "ru"
        private val TAG = ContactsListViewModel::class.simpleName
    }
}