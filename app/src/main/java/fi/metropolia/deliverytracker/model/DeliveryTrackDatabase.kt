package fi.metropolia.deliverytracker.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Request::class, User::class], version = 1)
abstract class DeliveryTrackDatabase: RoomDatabase() {
    abstract fun requestDao(): RequestDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile private var instance: DeliveryTrackDatabase? = null
        private val LOCK = Any()

        //Whenever the RequestDatabase singleton is invoked with the context, we will get an instance if it is already created, or we will synchronize
        // to allow one thread to access this block below at a time
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) { // if instance is null -> synchronized is returned, else returns instance
            //Either return the instance or build the database, assign it to instance and return it
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            DeliveryTrackDatabase::class.java,
            "DeliveryTracker.db"
        ).build()
    }
}