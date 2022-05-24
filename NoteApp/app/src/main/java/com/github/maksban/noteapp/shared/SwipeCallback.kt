package com.github.maksban.noteapp

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.addOnSwipeCallback(direction: Int, swipedPositionCallback: (Int) -> Unit) {
    val callback = object: ItemTouchHelper.SimpleCallback(0, direction) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val adapterPosition = viewHolder.adapterPosition
            swipedPositionCallback.invoke(adapterPosition)
        }
    }

    ItemTouchHelper(callback).attachToRecyclerView(this)
}