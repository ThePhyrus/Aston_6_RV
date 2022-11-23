package roman.bannikov.aston_6_rv.repository

import roman.bannikov.aston_6_rv.database.ContactEntity
import roman.bannikov.aston_6_rv.database.ContactsDatabase
import roman.bannikov.aston_6_rv.domain.ContactDomain


class ContactRepositoryImpl(
    private val db: ContactsDatabase
) : ContactRepository {

    override suspend fun addContact(contactDomain: ContactDomain) =
        db.getContactDAO().insertContact(mapToContactEntity(contactDomain = contactDomain))

    override suspend fun deleteContactById(id: Int?) =
        db.getContactDAO().deleteContactById(contact_id = id)

    override suspend fun updateContact(contactDomain: ContactDomain) =
        db.getContactDAO().updateContact(mapToContactEntity(contactDomain = contactDomain))

    override suspend fun getContacts(): List<ContactDomain>? =
        mapToListContact(db.getContactDAO().getAllContacts())

    override suspend fun searchContacts(searchQuery: String): List<ContactDomain>? =
        mapToListContact(db.getContactDAO().searchDatabase(searchQuery = searchQuery))

    override suspend fun addAllContacts(contactsList: List<ContactDomain>) =
        db.getContactDAO().insertAllContacts(mapToListContactEntity(contactsList))

    override suspend fun checkRoom(): Boolean =
        db.getContactDAO().getAllContacts().isEmpty()

    private fun mapToContactEntity(contactDomain: ContactDomain): ContactEntity {
        return ContactEntity(
            name = contactDomain.name,
            lastname = contactDomain.lastname,
            phoneNumber = contactDomain.phoneNumber,
            id = contactDomain.id
        )
    }

    private fun mapToContact(contactEntity: ContactEntity): ContactDomain {
        return ContactDomain(
            name = contactEntity.name,
            lastname = contactEntity.lastname,
            phoneNumber = contactEntity.phoneNumber,
            id = contactEntity.id
        )
    }

    private fun mapToListContact(contactEntityList: List<ContactEntity>?): List<ContactDomain>? {
        return (contactEntityList)?.map { mapToContact(it) }
    }

    private fun mapToListContactEntity(contactDomain: List<ContactDomain>): List<ContactEntity> {
        return (contactDomain).map { mapToContactEntity(it) }
    }
}