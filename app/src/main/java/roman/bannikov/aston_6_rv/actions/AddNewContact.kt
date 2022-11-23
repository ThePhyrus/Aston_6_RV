package roman.bannikov.aston_6_rv.actions

import roman.bannikov.aston_6_rv.domain.ContactDomain
import roman.bannikov.aston_6_rv.repository.ContactRepository

class AddNewContact(
    private val contactRepository: ContactRepository
) {

    suspend fun execute(contactDomain: ContactDomain) {
        val firstName = contactDomain.name ?: ""
        val lastName = contactDomain.lastname ?: ""
        val phoneNumber = contactDomain.phoneNumber ?: ""
        contactRepository.addContact(
            contactDomain = ContactDomain(
                name = firstName,
                lastname = lastName,
                phoneNumber = phoneNumber,
                id = null
            )
        )
    }
}