package alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class WidgetBroadcastReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        //String action = intent.getAction();
        Intent serviceIntent = new Intent(context, NewsService.class);
        context.startService(serviceIntent);
    }
}
