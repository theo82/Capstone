package theo.tziomakas.news.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import theo.tziomakas.news.fragments.NewsFragment;


public class SectionsPageAdapter extends FragmentPagerAdapter {


    String[] sources = {"usa-today","bbc-news","al-jazeera-english","daily-mail","abc-news","business-insider","cnn","fox-news"};

    public static final String BASE_URL = "https://newsapi.org/v2/top-headlines?sources=";

    public static final String API_KEY = "&apiKey=98b995b151264acdb35e751ff6d22a3c";

    public SectionsPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        return NewsFragment.newInstance(BASE_URL + sources[position] + API_KEY);
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
