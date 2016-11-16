package com.kekaton.hackathon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.kekaton.hackathon.Activity.LoginActivity;
import com.kekaton.hackathon.Fragments.MainFragment;
import com.kekaton.hackathon.Fragments.ProfileFragment;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

public class MainActivity extends AppCompatActivity {

    private static final int FIRST_ID = 1;
    private static final int SECOND_ID = 2;

    FragmentManager fragmentManager;
    SmoothActionBarDrawerToggle mDrawerToggle;
    DrawerLayout mDrawerLayout;

    AppCompatActivity activity;

    private Drawer mDrawer;
    private AccountHeader mHeader;

    public void openDrawer() {
        mDrawer.openDrawer();
    }

    public void closeDrawer() {
        mDrawer.closeDrawer();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = this;

        fragmentManager = getSupportFragmentManager();

        if (savedInstanceState == null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.main_activity_container, MainFragment.newInstance(), "fragment_main")
                    .commit();
        }

        handleDrawer(savedInstanceState);
    }

    private void handleDrawer(Bundle savedInstanceState) {
        final Activity activity = this;
        // Setup account header

        mHeader = new AccountHeaderBuilder()
                .withActivity(activity)
                .withHeaderBackground(R.drawable.bg)
                .addProfiles(
                        new ProfileDrawerItem().withName("Aleksandr Daff")
                )
                .build();

        // Setup drawer
        mDrawer = new DrawerBuilder()
                .withActivity(activity)
                .withAccountHeader(mHeader)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Профиль")
                                .withIcon(R.drawable.ic_star_circle_black_24dp).withIconTintingEnabled(true).withIdentifier(FIRST_ID),

                        new PrimaryDrawerItem().withName("Бибан")
                                .withIcon(R.drawable.ic_newspaper_black_24dp).withIconTintingEnabled(true).withIdentifier(SECOND_ID)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                        switch (drawerItem.getIdentifier()) {
                            default:
                            case FIRST_ID:
                                startActivity(new Intent(activity, LoginActivity.class));
                                break;
                            case SECOND_ID:
                                mDrawerToggle.runWhenIdle(new Runnable() {
                                    @Override
                                    public void run() {
                                        fragmentManager
                                                .beginTransaction()
                                                .replace(R.id.main_activity_container, MainFragment.newInstance(), "fragment_main")
                                                .commit();

                                    }
                                });
                                break;
                        }
                        mDrawerLayout.closeDrawers();
                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .build();

        mDrawerLayout = mDrawer.getDrawerLayout();
        mDrawerToggle = new SmoothActionBarDrawerToggle(this, mDrawerLayout, null, R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawer.setSelection(SECOND_ID);

    }

    private class SmoothActionBarDrawerToggle extends ActionBarDrawerToggle {

        private Runnable runnable;

        public SmoothActionBarDrawerToggle(Activity activity, DrawerLayout drawerLayout, Toolbar toolbar, int openDrawerContentDescRes, int closeDrawerContentDescRes) {
            super(activity, drawerLayout, toolbar, openDrawerContentDescRes, closeDrawerContentDescRes);
        }

        @Override
        public void onDrawerStateChanged(int newState) {
            super.onDrawerStateChanged(newState);
            if (runnable != null && newState == DrawerLayout.STATE_IDLE) {
                runnable.run();
                runnable = null;
            }
        }

        public void runWhenIdle(Runnable runnable) {
            this.runnable = runnable;
        }
    }
}
