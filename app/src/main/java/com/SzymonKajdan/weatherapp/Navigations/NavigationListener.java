package com.SzymonKajdan.weatherapp.Navigations;

import android.support.v4.app.Fragment;

public interface NavigationListener {
    void changeFragment(Fragment fragment,Boolean addToBackStack);
}
