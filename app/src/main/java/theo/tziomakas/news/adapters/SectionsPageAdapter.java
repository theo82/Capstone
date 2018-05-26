package theo.tziomakas.news.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import theo.tziomakas.news.R;
import theo.tziomakas.news.fragments.NewsFragment;


public class SectionsPageAdapter extends FragmentPagerAdapter {

    Context mContext;



    String[] sources = {"usa-today","bbc-news","al-jazeera-english","daily-mail","abc-news","business-insider","cnn","fox-news"};

    public static final String BASE_URL = "http://newsapi.org/v2/top-headlines?sources=";

    public static final String API_KEY = "&apiKey=98b995b151264acdb35e751ff6d22a3c";

    public SectionsPageAdapter(FragmentManager fm,Context context) {
        super(fm);
        mContext = context;
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
                return mContext.getResources().getString(R.string.usa_today);
            case 1:
                return mContext.getResources().getString(R.string.bbc_news);
            case 2:
                return mContext.getResources().getString(R.string.aljazeera);
            case 3:
                return mContext.getResources().getString(R.string.daily_mail);
            case 4:
                return mContext.getResources().getString(R.string.abc_news);
            case 5:
                return mContext.getResources().getString(R.string.business_insider);
            case 6:
                return mContext.getResources().getString(R.string.cnn);
            case 7:
                return mContext.getResources().getString(R.string.fox_news);
            default:
                return null;
        }

    }
}
