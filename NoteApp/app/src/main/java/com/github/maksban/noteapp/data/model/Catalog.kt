package com.github.maksban.noteapp.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "catalog_table")
data class Catalog(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String?,
    //val note: List<Note>?
): Parcelable {
    override fun toString(): String {
        return title ?: ""
    }
}
