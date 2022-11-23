package roman.bannikov.aston_6_rv.actions

import roman.bannikov.aston_6_rv.repository.ContactRepository

class DeleteContact(
    private val contactRepository: ContactRepository
) {

    suspend fun execute(id: Int?) {
        contactRepository.deleteContactById(id = id)
    }
}