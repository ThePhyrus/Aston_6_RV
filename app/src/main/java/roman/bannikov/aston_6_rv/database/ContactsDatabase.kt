package roman.bannikov.aston_6_rv.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [ContactEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ContactsDatabase : RoomDatabase() {

    abstract fun getContactDAO(): ContactDAO

    companion object {
        @Volatile
        private var instance: ContactsDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context = context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ContactsDatabase::class.java,
                "ContactsDB.bd"
            ).build()
    }
}