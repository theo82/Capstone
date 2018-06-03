package theo.tziomakas.news;

import android.app.AlarmManager;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import alarm.WidgetBroadcastReceiver;
import theo.tziomakas.news.adapters.SectionsPageAdapter;
import theo.tziomakas.news.widget.UpdateNewsWidgetService;

public class MainActivity extends AppCompatActivity{
    FirebaseAuth mAuth;
    Toolbar toolbar;
    private ViewPager mViewPager;
    private SectionsPageAdapter mSectionsPagerAdapter;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();


        toolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.main_page_toolbar);
        setSupportActionBar(toolbar);


        mViewPager = (ViewPager)findViewById(R.id.main_TabPager);
        mSectionsPagerAdapter = new SectionsPageAdapter(getSupportFragmentManager(), this);

        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(1);
        mTabLayout = (TabLayout)findViewById(R.id.main_tabs);
        //mTabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FF0000"));
        //mTabLayout.setSelectedTabIndicatorHeight((int) (5 * getResources().getDisplayMetrics().density));
        mTabLayout.setTabTextColors(Color.parseColor("#460b1f"), Color.parseColor("#ffffff"));
        mTabLayout.setupWithViewPager(mViewPager);



    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null){
            sendToStart();
        }

        startAlarmManager();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId() == R.id.main_logout_btn){
            FirebaseAuth.getInstance().signOut();
            sendToStart();

        }else if(item.getItemId() == R.id.fav_news_btn){
            Intent favIntent = new Intent(MainActivity.this,FavouriteNewsActivity.class);
            startActivity(favIntent);
        }

        return true;
    }
    public void sendToStart(){
        Intent startIntent = new Intent(MainActivity.this,StartActivity.class);
        startActivity(startIntent);
        finish();
    }

    public void startAlarmManager(){
        Intent i = new Intent(this, WidgetBroadcastReceiver.class);
        i.setAction("android.appwidget.action.APPWIDGET_UPDATE");
        sendBroadcast(i);
    }




}