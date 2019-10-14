package ru.kartsev.dmitry.cinemadetails.mvvm.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.tabLayout
import kotlinx.android.synthetic.main.activity_main.viewPager
import ru.kartsev.dmitry.cinemadetails.R
import ru.kartsev.dmitry.cinemadetails.mvvm.presentation.tabs.MainViewPagerAdapter
import ru.kartsev.dmitry.cinemadetails.mvvm.presentation.tabs.WatchlistFragment
import ru.kartsev.dmitry.cinemadetails.mvvm.presentation.tabs.NowPlayingFragment
import ru.kartsev.dmitry.cinemadetails.mvvm.presentation.helper.ZoomOutPageTransformer
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    /** Section: Private Properties. */

    private lateinit var viewpagerAdapter: MainViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
    }

    override fun supportFragmentInjector() = dispatchingAndroidInjector
}
