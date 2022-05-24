package com.github.maksban.noteapp.extensions

import android.os.Bundle

private val catalogIdKey = "CATALOG_ID"

fun Bundle.putCatalogId(id: Int): Bundle = this.also {
    putInt(catalogIdKey, id)
}

fun Bundle.getCatalogId(): Int {
    return getInt(catalogIdKey)
}