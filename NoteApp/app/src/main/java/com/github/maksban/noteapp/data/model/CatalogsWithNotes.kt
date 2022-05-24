package com.github.maksban.noteapp.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class CatalogsWithNotes(
    @Embedded
    val catalog: Catalog,
    @Relation(
        parentColumn = "id",
        entityColumn = "catalogId"
    )
    val notes: List<Note>
)
