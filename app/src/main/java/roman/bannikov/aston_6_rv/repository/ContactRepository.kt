package roman.bannikov.aston_6_rv.repository

import roman.bannikov.aston_6_rv.domain.ContactDomain


interface ContactRepository {

    suspend fun addContact(contactDomain: ContactDomain)

    suspend fun deleteContactById(id: Int?)

    suspend fun updateContact(contactDomain: ContactDomain)

    suspend fun getContacts(): List<ContactDomain>?

    suspend fun searchContacts(searchQuery: String): List<ContactDomain>?

    suspend fun addAllContacts(contactsList: List<ContactDomain>)

    suspend fun checkRoom(): Boolean
}