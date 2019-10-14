package ru.kartsev.dmitry.cinemadetails.mvvm.presentation.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import dagger.android.DispatchingAndroidInjector
import kotlinx.android.synthetic.main.fragment_main.*
import ru.kartsev.dmitry.cinemadetails.R
import ru.kartsev.dmitry.cinemadetails.common.di.Injectable
import ru.kartsev.dmitry.cinemadetails.mvvm.presentation.helper.ZoomOutPageTransformer
import javax.inject.Inject

class StartFragment : Fragment(), Injectable {
    @Inject
    lateinit var childFragmentInjector: DispatchingAndroidInjector<Fragment>

    /** Section: Private Properties. */

    private lateinit var viewpagerAdapter: MainViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTabs()
    }

    private fun initTabs() {
        tabLayout?.apply {
            viewpagerAdapter = MainViewPagerAdapter(
                childFragmentManager
            ).apply {
                addFragment(NowPlayingFragment.newInstance(), getString(R.string.activity_main_tab_now_playing_title))
                addFragment(WatchlistFragment.newInstance(), getString(R.string.activity_main_tab_watchlist))
            }

            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
                override fun onTabReselected(tab: TabLayout.Tab?) {
                    viewPager?.currentItem = tab?.position ?: 0
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
}