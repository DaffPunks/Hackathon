package com.kekaton.hackathon.Fragments;

import android.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.kekaton.hackathon.MainActivity;
import com.kekaton.hackathon.R;

public class BaseFragment extends Fragment {

    /**
     * This function should be called in every fragment that needs a toolbar
     * Every fragment has its own toolbar, and this function executes the
     * necessary steps to ensure the toolbar is correctly bound to the main
     * activity, which handles the rest (drawer and options menu)
     * <p>
     * Also, a warning: always bind the toolbar title BEFORE calling this function
     * otherwise, it won't work.
     *
     * @param toolbar The toolbar present in the fragment
     */
    protected void setupToolbarForFragment(Toolbar toolbar) {
        toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        getMainActivity().setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMainActivity().openDrawer();
            }
        });
    }

    protected MainActivity getMainActivity() {
        return ((MainActivity) getActivity());
    }

    protected FragmentManager getMainFragmentManager() {
        return getActivity().getFragmentManager();
    }

}