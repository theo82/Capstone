package alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import java.util.Calendar;

import theo.tziomakas.news.widget.NewsAppWidgetProvider;

public class AppWidgetAlarm
{
    private final int ALARM_ID = 0;
    private final int INTERVAL_MILLIS = 10000;

    private Context mContext;


    public AppWidgetAlarm(Context context)
    {
        mContext = context;
    }


    public void startAlarm()
    {
        Calendar c = Calendar.getInstance();

        c.add(Calendar.MINUTE, 2);

        long afterTwoMinutes = c.getTimeInMillis();

        Intent alarmIntent = new Intent(NewsAppWidgetProvider.ACTION_AUTO_UPDATE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, ALARM_ID, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, afterTwoMinutes, pendingIntent);

    }


    public void stopAlarm()
    {
        Intent alarmIntent = new Intent(NewsAppWidgetProvider.ACTION_AUTO_UPDATE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, ALARM_ID, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }


}
