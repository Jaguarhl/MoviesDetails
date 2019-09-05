package ru.kartsev.dmitry.cinemadetails.mvvm.view.fragments

import android.view.ContextMenu
import android.view.View
import androidx.fragment.app.Fragment
import org.koin.core.KoinComponent

class HistoryFragment : Fragment(), KoinComponent {
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