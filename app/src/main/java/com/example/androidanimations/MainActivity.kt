package com.example.androidanimations

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.androidanimations.activitytransition.ActivityTransitionFragment
import com.example.androidanimations.circle.CircleActivity
import com.example.androidanimations.fragmenttransitition.FragmentTransitionFragment
import com.example.androidanimations.lottie.LottieFragment
import com.example.androidanimations.transitionframework.TransitionFrameworkFragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbarView)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)
        navView.setCheckedItem(R.id.nav_anim_list)
        onNavigationItemSelected(navView.menu.findItem(R.id.nav_anim_list))
    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            //if ((supportFragmentManager.fragments.last() as? OnBackPressedListener)?.onBackPressed() != true) {
                super.onBackPressed()
           // }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_anim_list -> openScreen(AnimationListFragment(), R.string.anim_list)
            R.id.nav_gallery -> openScreen(AnimationsFragment(), R.string.animations)
            R.id.nav_slideshow -> openScreen(ObjectAnimatorFragment(), R.string.animator)
            R.id.nav_tools -> openScreen(VectorAnimationFragment(), R.string.vector)
            R.id.nav_share -> openScreen(LottieFragment(), R.string.lottie)
            R.id.keyframes -> openScreen(KeyframesFragment(), R.string.keyframes)
            R.id.physic -> openScreen(PhysicFragment(), R.string.physics)
            R.id.layoutTransitions -> openScreen(LayoutChangesFragment(), R.string.layTrans)
            R.id.transitionFramework -> openScreen(TransitionFrameworkFragment(), R.string.transFramework)
            R.id.fragmentTransition -> openScreen(FragmentTransitionFragment(), R.string.fragTrans)
            R.id.activityTransition -> openScreen(ActivityTransitionFragment(), R.string.actTrans)
            R.id.circle -> //openScreen(CircleFragment(), R.string.circle)
            {
                startActivity(Intent(this, CircleActivity::class.java))
            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun openScreen(frag: Fragment, titleRes: Int) {
        supportActionBar?.title = getString(titleRes)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, frag, "openFrag")
            .commit()
    }
}
