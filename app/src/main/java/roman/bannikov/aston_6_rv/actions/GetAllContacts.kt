package roman.bannikov.aston_6_rv.actions

import roman.bannikov.aston_6_rv.domain.ContactDomain
import roman.bannikov.aston_6_rv.repository.ContactRepository

class GetAllContacts(
    private val contactRepository: ContactRepository
) {

    suspend fun execute(): List<ContactDomain>? {
        return contactRepository.getContacts()
    }
}