package theo.tziomakas.news.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import theo.tziomakas.news.fragments.NewsFragment;


public class SectionsPageAdapter extends FragmentPagerAdapter {
    public SectionsPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                NewsFragment fragment1 = NewsFragment.newInstance("https://newsapi.org/v2/top-headlines?sources=usa-today&apiKey=98b995b151264acdb35e751ff6d22a3c");
                return fragment1;
            case 1:
                NewsFragment fragment2 = NewsFragment.newInstance("https://newsapi.org/v2/top-headlines?sources=bbc-news&apiKey=98b995b151264acdb35e751ff6d22a3c");
                return fragment2;
            case 2:
                NewsFragment fragment3 = NewsFragment.newInstance("https://newsapi.org/v2/top-headlines?sources=al-jazeera-english&apiKey=98b995b151264acdb35e751ff6d22a3c");
                return fragment3;
            case 3:
                NewsFragment fragment4 = NewsFragment.newInstance("https://newsapi.org/v2/top-headlines?sources=daily-mail&apiKey=98b995b151264acdb35e751ff6d22a3c");
                return fragment4;
            case 4:
                NewsFragment fragment5 = NewsFragment.newInstance("https://newsapi.org/v2/top-headlines?sources=abc-news&apiKey=98b995b151264acdb35e751ff6d22a3c");
                return fragment5;
            case 5:
                NewsFragment fragment6 = NewsFragment.newInstance("https://newsapi.org/v2/top-headlines?sources=business-insider&apiKey=98b995b151264acdb35e751ff6d22a3c");
                return fragment6;
            case 6:
                NewsFragment fragment7 = NewsFragment.newInstance("https://newsapi.org/v2/top-headlines?sources=cnn&apiKey=98b995b151264acdb35e751ff6d22a3c");
                return fragment7;
            case 7:
                NewsFragment fragment8 = NewsFragment.newInstance("https://newsapi.org/v2/top-headlines?sources=fox-news&apiKey=98b995b151264acdb35e751ff6d22a3c");
                return fragment8;



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
