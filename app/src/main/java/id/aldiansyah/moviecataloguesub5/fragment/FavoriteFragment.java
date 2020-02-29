package id.aldiansyah.moviecataloguesub5.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import id.aldiansyah.moviecataloguesub5.R;
import id.aldiansyah.moviecataloguesub5.adapter.SectionsPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {

    public FavoriteFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getChildFragmentManager());
        ViewPager viewPager = view.findViewById(R.id.viewPager_fav);
        viewPager.setAdapter(sectionsPagerAdapter);

        Fragment mFragment = new Fragment();
        if (mFragment.isAdded()) {
            return;
        }

        TabLayout tabs = view.findViewById(R.id.tabLayout_fav);
        tabs.setupWithViewPager(viewPager);
    }
}
