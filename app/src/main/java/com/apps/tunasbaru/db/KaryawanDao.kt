package com.apps.tunasbaru.db

import androidx.room.*
import com.apps.tunasbaru.model.Karyawan

@Dao
interface KaryawanDao {

    @Insert
    fun insert(karyawan: Karyawan)

    @Update
    fun update(karyawan: Karyawan)

    @Delete
    fun delete(karyawan: Karyawan)

    @Query("SELECT * FROM karyawan")
    fun getAll() : List<Karyawan>

    @Query("SELECT * FROM karyawan WHERE id = :id")
    fun getById(id: Int) : List<Karyawan>
}