package roman.bannikov.aston_6_rv.actions

import roman.bannikov.aston_6_rv.repository.ContactRepository

class CheckRoom(
    private val contactRepository: ContactRepository
) {

    suspend fun execute(): Boolean {
        return contactRepository.checkRoom()
    }
}