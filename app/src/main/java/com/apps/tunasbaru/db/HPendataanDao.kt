package com.apps.tunasbaru.db

import androidx.room.*
import com.apps.tunasbaru.model.HeaderPendataan

@Dao
interface HPendataanDao {

    @Insert
    fun insert(hpendataan: HeaderPendataan)

    @Update
    fun update(hpendataan: HeaderPendataan)

    @Delete
    fun delete(hpendataan: HeaderPendataan)

    @Query("SELECT * FROM header_pendataan")
    fun getAll() : List<HeaderPendataan>

    @Query("SELECT * FROM header_pendataan WHERE id = :id")
    fun getById(id: Int) : List<HeaderPendataan>

    @Query("SELECT * FROM header_pendataan ORDER BY id DESC LIMIT 1")
    fun getId() : List<HeaderPendataan>
}