package com.example.androidanimations

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import android.view.MenuItem
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
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
            super.onBackPressed()
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
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun openScreen(frag: Fragment, titleRes: Int) {
        supportActionBar?.title = getString(titleRes)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, frag, "openFrag")
            //.addToBackStack(null)
            .commit()
    }
}
