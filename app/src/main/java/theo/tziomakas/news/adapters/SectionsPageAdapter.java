package theo.tziomakas.news.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import theo.tziomakas.news.R;
import theo.tziomakas.news.fragments.NewsFragment;

import static android.content.Context.MODE_PRIVATE;


public class SectionsPageAdapter extends FragmentPagerAdapter {

    private static final String LOG_TAG = SectionsPageAdapter.class.getSimpleName();
    Context mContext;



    String[] sources = {
            "usa-today",
            "bbc-news",
            "al-jazeera-english",
            "daily-mail",
            "abc-news",
            "business-insider",
            "cnn",
            "fox-news",
            "independent",
            "google-news",
            "the-new-york-times",
            "the-huffington-post",
            "national-geographic",
            "the-wall-street-journal"
    };

    public static final String BASE_URL = "http://newsapi.org/v2/top-headlines?sources=";

    public static final String API_KEY = "&apiKey=98b995b151264acdb35e751ff6d22a3c";

    public SectionsPageAdapter(FragmentManager fm,Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {

        String request = BASE_URL + sources[position] + API_KEY;

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = sdf.format(c.getTime());

        PreferenceManager.getDefaultSharedPreferences(mContext)
                .edit().putString("url", request)
                .putString("request_date",strDate)
                .commit();

        Log.v(LOG_TAG,strDate);

        return NewsFragment.newInstance(request);
    }

    @Override
    public int getCount() {
        return 14;
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
            case 8:
                return mContext.getResources().getString(R.string.independent);
            case 9:
                return mContext.getResources().getString(R.string.google_news);
            case 10:
                return mContext.getResources().getString(R.string.the_new_york_times);
            case 11:
                return mContext.getResources().getString(R.string.the_huffington_post);
            case 12:
                return mContext.getResources().getString(R.string.national_geographic);
            case 13:
                return mContext.getResources().getString(R.string.the_wall_street_journal);

            default:
                return null;
        }

    }
}
