package roman.bannikov.aston_6_rv.database

import androidx.room.*

@Dao
interface ContactDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllContacts(contactsList: List<ContactEntity>)

    @Query("SELECT * FROM contact_table")
    suspend fun getAllContacts(): List<ContactEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContact(contactEntity: ContactEntity)

    @Update
    suspend fun updateContact(contactEntity: ContactEntity)

    @Query("SELECT * FROM contact_table WHERE id = :contact_id")
    suspend fun getContactById(contact_id: Int?): ContactEntity

    @Query("DELETE FROM contact_table WHERE id = :contact_id")
    suspend fun deleteContactById(contact_id: Int?)

    @Query("SELECT * FROM contact_table WHERE name LIKE :searchQuery OR lastname LIKE :searchQuery OR phone_number LIKE :searchQuery")
    suspend fun searchDatabase(searchQuery: String): List<ContactEntity>?
}