package id.aldiansyah.moviecataloguesub5.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import id.aldiansyah.moviecataloguesub5.R;
import id.aldiansyah.moviecataloguesub5.fragment.FavoriteFragment;
import id.aldiansyah.moviecataloguesub5.fragment.FavoriteMoviesFragment;
import id.aldiansyah.moviecataloguesub5.fragment.FavoriteTvShowFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private final FavoriteFragment mContext;

    public SectionsPagerAdapter(FavoriteFragment context, FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mContext = context;
    }

    @StringRes
    private final int[] TAB_TITLES = new int[]{
            R.string.text_tab_fav_movies,
            R.string.text_tab_fav_tvShow
    };

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new FavoriteMoviesFragment();
                break;
            case 1:
                fragment = new FavoriteTvShowFragment();
                break;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
