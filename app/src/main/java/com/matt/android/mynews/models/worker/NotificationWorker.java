package com.matt.android.mynews.models.worker;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;

import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.matt.android.mynews.R;
import com.matt.android.mynews.controllers.activities.MainActivity;
import com.matt.android.mynews.models.api.NYTStreams;
import com.matt.android.mynews.models.api.NewsObject;
import com.matt.android.mynews.models.utils.Logger;
import com.matt.android.mynews.models.utils.SharedPreferencesManager;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

import static com.matt.android.mynews.models.utils.Constants.ID_WORKER_NOTIFICATION;
import static com.matt.android.mynews.models.utils.Constants.PREF_KEY_NOTIFICATION_URL;
import static com.matt.android.mynews.models.utils.Constants.TAG_WORKER;


public class NotificationWorker extends Worker {

    private int hits;
    private Disposable disposable;
    private String message;

    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        int id = (int) getInputData().getLong(ID_WORKER_NOTIFICATION, 0);

        //Do request
        doNotificationApiRequest();
        //Send related message
        sendNotification(message, id);

        return Result.success();
    }


    private void doNotificationApiRequest() {
        //Get URL from Notification Activity
        SharedPreferencesManager preferences = new SharedPreferencesManager(getApplicationContext());
        String url = preferences.getString(PREF_KEY_NOTIFICATION_URL);

        //Do API request
        disposable = NYTStreams.streamFetchUrl(url).subscribeWith(new DisposableObserver<NewsObject>() {

            @Override
            public void onNext(NewsObject news) {
                hits = news.checkIfResult();
                makeCorrectNotificationMessage(hits);
                Logger.i(hits + "");
            }

            @Override
            public void onError(Throwable e) {
                //Create notification with error message
                hits = -1;
                makeCorrectNotificationMessage(hits);
                Logger.e(e.getMessage());
            }

            @Override
            public void onComplete() {
                Logger.i("notification api request completed" + hits);
            }
        });

    }

    public static void scheduleReminder(String tag) {
        PeriodicWorkRequest.Builder notificationWork = new PeriodicWorkRequest.Builder(NotificationWorker.class, 16, TimeUnit.MINUTES);
        PeriodicWorkRequest request = notificationWork.build();

        WorkManager.getInstance().enqueueUniquePeriodicWork(tag, ExistingPeriodicWorkPolicy.KEEP , request);
    }


    public static void cancelReminder(String tag) {
        WorkManager instance = WorkManager.getInstance();
        instance.cancelAllWorkByTag(tag);
    }

    private void makeCorrectNotificationMessage(int hits){
      if (hits == -1) {
          message = "Error";
      } else {
          message = "There is some new article(s) that might interest you";
      }

    }

    private void sendNotification(String text, int id) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(TAG_WORKER, id);

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

        NotificationManager notificationManager = (NotificationManager)getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default", "Default", NotificationManager.IMPORTANCE_DEFAULT);
            Objects.requireNonNull(notificationManager).createNotificationChannel(channel);
        }

        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), "default")
                //Constant title from string resources
                .setContentTitle(getApplicationContext().getResources().getString(R.string.notification_title))
                .setContentText(text)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true);

        Objects.requireNonNull(notificationManager).notify(id, notification.build());
    }

}
