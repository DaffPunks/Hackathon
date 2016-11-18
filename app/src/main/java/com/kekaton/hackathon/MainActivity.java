package com.kekaton.hackathon;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.kekaton.hackathon.API.VKApiCall;
import com.kekaton.hackathon.Activity.LoginActivity;
import com.kekaton.hackathon.Fragments.MainFragment;
import com.kekaton.hackathon.Fragments.ProfileFragment;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.squareup.picasso.Picasso;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONArray;
import org.json.JSONException;

public class MainActivity extends AppCompatActivity {

    private static final int PROFILE_ID = 1;
    private static final int FEED_ID = 2;
    private static final int EXIT_ID = 0;

    FragmentManager fragmentManager;
    SmoothActionBarDrawerToggle mDrawerToggle;
    DrawerLayout mDrawerLayout;

    private Drawer mDrawer;
    private AccountHeader mHeader;

    private String firstName;
    private String lastName;
    private String photoMax;
    private int uid;
    private String sex;

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

        fragmentManager = getSupportFragmentManager();

        if (savedInstanceState == null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.main_activity_container, MainFragment.newInstance(), "fragment_main")
                    .commit();
        }

        loadName();
        handleDrawer(savedInstanceState);
    }

    private void handleDrawer(Bundle savedInstanceState) {
        final Activity activity = this;

        DrawerImageLoader.init(new AbstractDrawerImageLoader() {
            @Override
            public void set(ImageView imageView, Uri uri, Drawable placeholder) {
                Picasso.with(imageView.getContext()).load(uri).fit().placeholder(placeholder).into(imageView);
            }

            @Override
            public void cancel(ImageView imageView) {
                Picasso.with(imageView.getContext()).cancelRequest(imageView);
            }

        });

        // Setup account header
        mHeader = new AccountHeaderBuilder()
                .withActivity(activity)
                .withHeaderBackground(R.drawable.bg)
                .addProfiles(
                        new ProfileDrawerItem().withName(firstName + " " + lastName).withIcon(photoMax)
                )
                .withSelectionListEnabled(false)
                .withProfileImagesClickable(false)
                .build();

        // Setup drawer
        mDrawer = new DrawerBuilder()
                .withActivity(activity)
                .withAccountHeader(mHeader)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Профиль")
                                .withIcon(R.drawable.ic_star_circle_black_24dp).withIconTintingEnabled(true).withIdentifier(PROFILE_ID),

                        new PrimaryDrawerItem().withName("Лента")
                                .withIcon(R.drawable.ic_newspaper_black_24dp).withIconTintingEnabled(true).withIdentifier(FEED_ID),

                        new DividerDrawerItem(),

                        new PrimaryDrawerItem().withName("Выйти")
                                .withIcon(R.drawable.ic_checkbox_blank_circle_black_24dp).withIconTintingEnabled(true).withIdentifier(EXIT_ID)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                        switch ((int)drawerItem.getIdentifier()) {
                            default:
                            case PROFILE_ID:
                                mDrawerToggle.runWhenIdle(new Runnable() {
                                    @Override
                                    public void run() {
                                        fragmentManager
                                                .beginTransaction()
                                                .replace(R.id.main_activity_container, ProfileFragment.newInstance(), "fragment_main")
                                                .commit();

                                    }
                                });
                                break;
                            case FEED_ID:
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
                            case EXIT_ID:
                                mDrawerToggle.runWhenIdle(new Runnable() {
                                    @Override
                                    public void run() {
                                        SharedPreferences sPref = getSharedPreferences("mysettings", MODE_PRIVATE);
                                        sPref.edit().clear().apply();

                                        VKSdk.logout();

                                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
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
        mDrawer.setSelection(-1);

    }

    private void loadName() {
        SharedPreferences sPref = getSharedPreferences("mysettings", MODE_PRIVATE);
        firstName = sPref.getString("first_name", "mo");
        lastName = sPref.getString("last_name", "noe");
        photoMax = sPref.getString("photoMax", "");
        sex = sPref.getString("sex", "x");
        uid = sPref.getInt("uid", 0);
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

    VKApiCall vkApi = new VKApiCall(this);
    SharedPreferences sPref;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {

                vkApi.getUser(new VKRequest.VKRequestListener() {
                    @Override
                    public void onComplete(VKResponse response) {

                        int id = 0;
                        String first_name = "No";
                        String last_name = "Name";
                        String sex = "Sex";
                        String photoMax = "";

                        try {
                            JSONArray jsonResponse = response.json.getJSONArray("response");
                            first_name = jsonResponse.optJSONObject(0).getString("first_name");
                            last_name = jsonResponse.optJSONObject(0).getString("last_name");
                            sex = jsonResponse.optJSONObject(0).getString("sex");
                            photoMax = jsonResponse.optJSONObject(0).getString("photo_max");
                            id = jsonResponse.optJSONObject(0).getInt("id");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        SharedPreferences sPref = getSharedPreferences("mysettings", MODE_PRIVATE);
                        SharedPreferences.Editor ed = sPref.edit();
                        ed.putString("first_name", first_name);
                        ed.putString("last_name", last_name);
                        ed.putString("sex", sex);
                        ed.putString("photoMax", photoMax);
                        ed.putInt("id", id);
                        ed.apply();

                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                });


            }

            @Override
            public void onError(VKError error) {
                //Error login
            }

        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
