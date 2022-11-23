package roman.bannikov.aston_6_rv.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import roman.bannikov.aston_6_rv.actions.AddNewContact
import roman.bannikov.aston_6_rv.actions.UpdateContact
import roman.bannikov.aston_6_rv.domain.ContactDomain
import roman.bannikov.aston_6_rv.model.Contact

class ContactDetailsViewModel(
    private val addNewContact: AddNewContact,
    private val updateContact: UpdateContact,
) : ViewModel() {

    fun save(firstName: String?, lastName: String?, phone: String?, id: Int?) {

        try {
            viewModelScope.launch {
                val contact = Contact(
                    name = firstName,
                    lastname = lastName,
                    phoneNumber = phone,
                    id = id
                )
                addNewContact.execute(contactDomain = mapToContactDomain(contact = contact))
            }
        } catch (e: Exception) {
        }
    }

    fun update(firstName: String?, lastName: String?, phone: String?, id: Int?) {
        val contact = Contact(
            name = firstName,
            lastname = lastName,
            phoneNumber = phone,
            id = id
        )
        try {
            viewModelScope.launch {
                updateContact.execute(mapToContactDomain(contact))
            }
        } catch (e: Exception) {
        }
    }

    private fun mapToContactDomain(contact: Contact): ContactDomain {
        return ContactDomain(
            name = contact.name,
            lastname = contact.lastname,
            phoneNumber = contact.phoneNumber,
            id = contact.id
        )
    }
}