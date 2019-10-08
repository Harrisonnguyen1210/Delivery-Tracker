package fi.metropolia.deliverytracker.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: User): Long?

    @Query("SELECT * FROM User WHERE userName = :userName AND password = :password")
    suspend fun loginUser(userName: String, password: String): User?

    @Query("DELETE FROM User")
    suspend fun deleteAllUsers()
}