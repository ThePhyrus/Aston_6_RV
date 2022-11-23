package roman.bannikov.aston_6_rv.actions

import roman.bannikov.aston_6_rv.domain.ContactDomain
import roman.bannikov.aston_6_rv.repository.ContactRepository

class AddAllContacts(private val contactRepository: ContactRepository) {

    suspend fun execute(contactsList: List<ContactDomain>) {
        contactRepository.addAllContacts(contactsList)
    }
}