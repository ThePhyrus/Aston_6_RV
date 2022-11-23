package roman.bannikov.aston_6_rv.actions

import roman.bannikov.aston_6_rv.domain.ContactDomain
import roman.bannikov.aston_6_rv.repository.ContactRepository

class SearchContact(
    private val contactRepository: ContactRepository
) {

    suspend fun execute(searchRequest: String): List<ContactDomain>? {
        return contactRepository.searchContacts(searchQuery = searchRequest)
    }
}