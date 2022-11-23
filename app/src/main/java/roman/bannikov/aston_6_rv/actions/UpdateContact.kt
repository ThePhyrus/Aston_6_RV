package roman.bannikov.aston_6_rv.actions

import roman.bannikov.aston_6_rv.domain.ContactDomain
import roman.bannikov.aston_6_rv.repository.ContactRepository

class UpdateContact(
    private val contactRepository: ContactRepository
) {

    suspend fun execute(contactDomain: ContactDomain) {
        return contactRepository.updateContact(contactDomain = contactDomain)
    }
}