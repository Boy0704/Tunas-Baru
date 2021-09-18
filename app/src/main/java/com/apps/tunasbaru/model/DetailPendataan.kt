package com.apps.tunasbaru.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "detail_pendataan")
@Parcelize
data class DetailPendataan (
    @PrimaryKey(autoGenerate = true)@ColumnInfo(name = "id") var id: Int = 0,
    @ColumnInfo(name = "urutX") var urutX: Int = 0,
    @ColumnInfo(name = "nilai") var nilai: Int = 0,
    @ColumnInfo(name = "urutY") var urutY: Int = 0,
    @ColumnInfo(name = "id_header") var idHeader: Int = 0
) : Parcelable {
}