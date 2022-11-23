package roman.bannikov.aston_6_rv.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact_table")
data class ContactEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "name")
    val name: String?,
    @ColumnInfo(name = "lastname")
    val lastname: String?,
    @ColumnInfo(name = "phone_number")
    val phoneNumber: String?

)