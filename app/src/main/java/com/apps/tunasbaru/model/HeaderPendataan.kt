package com.apps.tunasbaru.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "header_pendataan")
@Parcelize
data class HeaderPendataan (
    @PrimaryKey(autoGenerate = true)@ColumnInfo(name = "id") var id: Int = 0,
    @ColumnInfo(name = "divisi") var divisi: String = "",
    @ColumnInfo(name = "block") var block: String = "",
    @ColumnInfo(name = "luas") var luas: String = "",
    @ColumnInfo(name = "tahun_tanam") var tahun_tanam: String = "",
    @ColumnInfo(name = "pelaksanaan_sensus") var pelaksanaan_sensus: String = "",
) : Parcelable {
}