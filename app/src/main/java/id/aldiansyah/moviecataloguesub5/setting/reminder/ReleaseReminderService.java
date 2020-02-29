package id.aldiansyah.moviecataloguesub5.setting.reminder;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import id.aldiansyah.moviecataloguesub5.MainActivity;
import id.aldiansyah.moviecataloguesub5.R;
import id.aldiansyah.moviecataloguesub5.models.ModelMovies;
import id.aldiansyah.moviecataloguesub5.models.MoviesData;
import id.aldiansyah.moviecataloguesub5.rest.Client;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class ReleaseReminderService extends JobService {

    private static int id = 0;
    private ArrayList<MoviesData> mData = new ArrayList<>();

    private void getData(final JobParameters jobParameters) {

        final Date nowDate = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(nowDate);

        Call<ModelMovies> call = Client.getInstanceRetrofit().getMovieRelease(date, date);
        call.enqueue(new Callback<ModelMovies>() {
            @Override
            public void onResponse(Call<ModelMovies> call, Response<ModelMovies> response) {
                if (response.isSuccessful()) {
                    ModelMovies responseData = response.body();
                    try {
                        if (responseData != null) {
                            mData = responseData.getResultMovies();
                        }
                        for (int i = 0; i < mData.size(); i++) {
                            String title = mData.get(i).getTitle();
                            String overview = mData.get(i).getOverview();
                            notification(getApplication(), title, overview , id + i);
                            jobFinished(jobParameters, false);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        jobFinished(jobParameters, true);
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelMovies> call, Throwable t) {
                Log.e("failure", "failure");
            }
        });
    }

    private void notification(Context context, String title, String message, int id) {
        String CHANNEL_ID = "Channel";
        String CHANNEL_NAME = "Release Reminder";

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle()
                .addLine(mData.get(id).getTitle())
                .setBigContentTitle(title)
                .setSummaryText("Film Release");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_new_releases)
                .setContentTitle(title)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setGroupSummary(true)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setStyle(inboxStyle)
                .setSound(sound)
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});

            builder.setChannelId(CHANNEL_ID);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        Notification notification = builder.build();
        if (notificationManager != null) {
            notificationManager.notify(id, notification);
        }
    }

    @Override
    public boolean onStartJob(JobParameters job) {
        getData(job);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return true;
    }
}
