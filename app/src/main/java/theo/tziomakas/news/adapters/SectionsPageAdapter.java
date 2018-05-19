package theo.tziomakas.news.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import theo.tziomakas.news.fragments.AlJazeeraFragment;
import theo.tziomakas.news.fragments.BBCFragment;
import theo.tziomakas.news.fragments.USATodayFragment;


public class SectionsPageAdapter extends FragmentPagerAdapter {
    public SectionsPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                USATodayFragment cnnFragment = new USATodayFragment();
                return cnnFragment;
            case 1:
                BBCFragment bbcFragment = new BBCFragment();
                return bbcFragment;
            case 2:
                AlJazeeraFragment alJazeeraFragment = new AlJazeeraFragment();
                return alJazeeraFragment;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position){
            case 0:
                return "USA Today";
            case 1:
                return "BCC News";
            case 2:
                return "AlJazeera";
            default:
                return null;
        }

    }
}
