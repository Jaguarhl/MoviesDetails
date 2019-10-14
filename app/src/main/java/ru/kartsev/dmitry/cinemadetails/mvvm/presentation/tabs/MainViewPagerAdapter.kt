package ru.kartsev.dmitry.cinemadetails.mvvm.presentation.tabs

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import org.koin.core.KoinComponent

class MainViewPagerAdapter(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT), KoinComponent {

    /** Section: Private Properties. */

    private val fragmentsList = mutableListOf<Fragment>()
    private val titlesList = mutableListOf<String>()

    fun addFragment(fragment: Fragment, title: String) {
        fragmentsList.add(fragment)
        titlesList.add(title)
    }

    override fun getItem(position: Int): Fragment = fragmentsList[position]

    override fun getCount(): Int = fragmentsList.size

    override fun getPageTitle(position: Int): CharSequence? = titlesList[position]
}