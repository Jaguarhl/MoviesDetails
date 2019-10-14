package ru.kartsev.dmitry.cinemadetails.mvvm.presentation.fragments

import android.view.ContextMenu
import android.view.View
import androidx.fragment.app.Fragment
import ru.kartsev.dmitry.cinemadetails.common.di.Injectable

class HistoryFragment : Fragment(), Injectable {
    /** Section: Static functions. */

    companion object {
        fun newInstance(): HistoryFragment = HistoryFragment()
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
    }
}