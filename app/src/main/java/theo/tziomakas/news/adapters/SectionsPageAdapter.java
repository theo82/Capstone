package theo.tziomakas.news.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import theo.tziomakas.news.fragments.NewsFragment;


public class SectionsPageAdapter extends FragmentPagerAdapter {

    NewsFragment fragment;

    public SectionsPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                fragment = NewsFragment.newInstance("https://newsapi.org/v2/top-headlines?sources=usa-today&apiKey=98b995b151264acdb35e751ff6d22a3c");
                return fragment;
            case 1:
               fragment = NewsFragment.newInstance("https://newsapi.org/v2/top-headlines?sources=bbc-news&apiKey=98b995b151264acdb35e751ff6d22a3c");
                return fragment;
            case 2:
                fragment = NewsFragment.newInstance("https://newsapi.org/v2/top-headlines?sources=al-jazeera-english&apiKey=98b995b151264acdb35e751ff6d22a3c");
                return fragment;
            case 3:
                fragment = NewsFragment.newInstance("https://newsapi.org/v2/top-headlines?sources=daily-mail&apiKey=98b995b151264acdb35e751ff6d22a3c");
                return fragment;
            case 4:
                fragment = NewsFragment.newInstance("https://newsapi.org/v2/top-headlines?sources=abc-news&apiKey=98b995b151264acdb35e751ff6d22a3c");
                return fragment;
            case 5:
                fragment= NewsFragment.newInstance("https://newsapi.org/v2/top-headlines?sources=business-insider&apiKey=98b995b151264acdb35e751ff6d22a3c");
                return fragment;
            case 6:
                fragment = NewsFragment.newInstance("https://newsapi.org/v2/top-headlines?sources=cnn&apiKey=98b995b151264acdb35e751ff6d22a3c");
                return fragment;
            case 7:
                fragment = NewsFragment.newInstance("https://newsapi.org/v2/top-headlines?sources=fox-news&apiKey=98b995b151264acdb35e751ff6d22a3c");
                return fragment;



            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 8;
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
            case 3:
                return "Daily Mail";
            case 4:
                return "ABC News";
            case 5:
                return "Business Insider";
            case 6:
                return "CNN";
            case 7:
                return "Fox News";
            default:
                return null;
        }

    }
}
