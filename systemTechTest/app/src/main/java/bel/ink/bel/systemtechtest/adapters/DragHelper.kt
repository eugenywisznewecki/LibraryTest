package bel.ink.bel.systemtechtest.adapters

import android.graphics.Canvas
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper

class DragHelper(val contract: ActionComplete) : ItemTouchHelper.Callback() {

    override fun getMovementFlags(p0: RecyclerView, p1: RecyclerView.ViewHolder): Int {
        val gradFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        return makeMovementFlags(gradFlags, 0)
    }

    override fun onMove(p0: RecyclerView, p1: RecyclerView.ViewHolder, p2: RecyclerView.ViewHolder): Boolean {
        contract.onViewMoved(p1.adapterPosition, p2.adapterPosition)
        return true
    }

    override fun onSwiped(p0: RecyclerView.ViewHolder, p1: Int) {
    }

    override fun isLongPressDragEnabled() = false

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}

interface ActionComplete {
    fun onViewMoved(oldPosition: Int, newPosition: Int)
}