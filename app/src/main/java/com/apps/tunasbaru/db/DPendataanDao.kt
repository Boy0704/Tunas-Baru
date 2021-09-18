package com.apps.tunasbaru.db

import androidx.room.*
import com.apps.tunasbaru.model.DetailPendataan

@Dao
interface DPendataanDao {

    @Insert
    fun insert(dpendataan: DetailPendataan)

    @Update
    fun update(dpendataan: DetailPendataan)

    @Delete
    fun delete(dpendataan: DetailPendataan)

    @Query("SELECT * FROM detail_pendataan")
    fun getAll() : List<DetailPendataan>

    @Query("SELECT * FROM detail_pendataan WHERE id = :id")
    fun getById(id: Int) : List<DetailPendataan>

    @Query("SELECT * FROM detail_pendataan WHERE id_header= :idHeader AND urutX = :urutX AND urutY = :urutY")
    fun getXY(idHeader: Int,urutX: Int, urutY: Int) : List<DetailPendataan>
}