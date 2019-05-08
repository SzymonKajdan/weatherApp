package com.SzymonKajdan.weatherapp.BaseFragments;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.SzymonKajdan.weatherapp.Navigations.NavigationListener;

public class BaseFragment extends Fragment {
    NavigationListener navigationListener;

    public NavigationListener getNavigationInteractions() {

        return navigationListener;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        navigationListener = null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NavigationListener) {
            navigationListener = (NavigationListener) context;
        }
    }
}