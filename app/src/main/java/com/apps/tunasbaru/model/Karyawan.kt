package com.apps.tunasbaru.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "karyawan")
@Parcelize
data class Karyawan (
    @PrimaryKey(autoGenerate = true)@ColumnInfo(name = "id") var id: Int = 0,
    @ColumnInfo(name = "nama") var nama: String = "",
    @ColumnInfo(name = "no_induk") var no_induk: String = "",
    @ColumnInfo(name = "estate") var estate: String = "",
) : Parcelable {
}