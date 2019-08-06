package ru.kartsev.dmitry.cinemadetails.mvvm.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.tabLayout
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.activity_main.viewPager
import ru.kartsev.dmitry.cinemadetails.R
import ru.kartsev.dmitry.cinemadetails.mvvm.view.adapters.pager.MainViewPagerAdapter
import ru.kartsev.dmitry.cinemadetails.mvvm.view.fragments.WatchlistFragment
import ru.kartsev.dmitry.cinemadetails.mvvm.view.fragments.NowPlayingFragment
import ru.kartsev.dmitry.cinemadetails.mvvm.view.helper.ZoomOutPageTransformer

class MainActivity : AppCompatActivity() {
    /** Section: Private Properties. */

    private lateinit var viewpagerAdapter: MainViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        initTabs()
    }

    private fun initTabs() {
        tabLayout?.apply {
            viewpagerAdapter = MainViewPagerAdapter(supportFragmentManager).apply {
                addFragment(NowPlayingFragment(), getString(R.string.activity_main_tab_now_playing_title))
                addFragment(WatchlistFragment(), getString(R.string.activity_main_tab_watchlist))
            }

            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
                override fun onTabReselected(tab: TabLayout.Tab?) {
                    viewPager.currentItem = tab?.position ?: 0
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabSelected(tab: TabLayout.Tab?) {
                }
            })
        }

        viewPager?.apply {
            adapter = viewpagerAdapter
            currentItem = 0
            setPageTransformer(true, ZoomOutPageTransformer())
        }

        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onBackPressed() {
        if (viewPager == null) return

        if (viewPager.currentItem == 0) {
            super.onBackPressed()
        } else {
            viewPager.currentItem = viewPager.currentItem - 1
        }
    }
}
