package ru.kartsev.dmitry.cinemadetails.mvvm.view.pager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_main.*
import ru.kartsev.dmitry.cinemadetails.R
import ru.kartsev.dmitry.cinemadetails.mvvm.view.adapters.pager.MainViewPagerAdapter
import ru.kartsev.dmitry.cinemadetails.mvvm.view.helper.ZoomOutPageTransformer

class MainFragment : Fragment(), OnItemSelectedCallback {
    /** Section: Private Properties. */

    private lateinit var viewModel: MainFragmentViewModel
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
        viewModel = ViewModelProviders.of(this).get(MainFragmentViewModel::class.java)
        viewModel.initializeByDefault()

        initTabs()
    }

    private fun initTabs() {
        tabLayout?.apply {
            viewpagerAdapter = MainViewPagerAdapter(
                childFragmentManager
            ).apply {
                NowPlayingFragment.newInstance().also {
                    addFragment(it, getString(R.string.activity_main_tab_now_playing_title))
                    it.callback = this@MainFragment
                }
                WatchlistFragment.newInstance().also {
                    addFragment(it, getString(R.string.activity_main_tab_watchlist))
                }
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

    override fun onItemSelected(itemId: Int) {
        navController().navigate(MainFragmentDirections.showMovieDetails(itemId))
    }

    private fun navController() = findNavController()
}