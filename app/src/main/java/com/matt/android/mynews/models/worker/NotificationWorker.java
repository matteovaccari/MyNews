package com.matt.android.mynews.models.worker;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;

import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
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
import static com.matt.android.mynews.models.utils.Constants.PREF_KEY_NOTIFICATION_MESSAGE;
import static com.matt.android.mynews.models.utils.Constants.PREF_KEY_NOTIFICATION_URL;
import static com.matt.android.mynews.models.utils.Constants.TAG_WORKER;


public class NotificationWorker extends Worker {

    private int hits;
    private Disposable disposable;
    private String message;
    SharedPreferencesManager preferences;

    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        //Instantiate preferences
        preferences = new SharedPreferencesManager(getApplicationContext());
        //Do request
        doNotificationApiRequest();
        //Send related message
        sendNotification();
        return Result.success();
    }


    private void doNotificationApiRequest() {
        //Get URL from Notification Activity
        String url = preferences.getString(PREF_KEY_NOTIFICATION_URL);

        //Do API request
        disposable = NYTStreams.streamFetchUrl(url).subscribeWith(new DisposableObserver<NewsObject>() {

            @Override
            public void onNext(NewsObject news) {
                hits = news.checkIfResult();
                message = makeCorrectNotificationMessage(hits);
                preferences.putString(PREF_KEY_NOTIFICATION_MESSAGE, message);
                Logger.i(hits + "");
                Logger.e(preferences.getString(PREF_KEY_NOTIFICATION_MESSAGE) + "MESSAGE");

            }

            @Override
            public void onError(Throwable e) {
                //Create notification with error message
                hits = -1;
                message = makeCorrectNotificationMessage(hits);
                Logger.e(e.getMessage());
            }

            @Override
            public void onComplete() {
                Logger.i("notification api request completed, hits :" + hits);
                Logger.e(preferences.getString(PREF_KEY_NOTIFICATION_MESSAGE) + "MESSAGE");

            }
        });

    }

    public static void scheduleReminder(String tag) {
        PeriodicWorkRequest.Builder notificationWork = new PeriodicWorkRequest.Builder(NotificationWorker.class,
                30, TimeUnit.MINUTES, 15, TimeUnit.MINUTES)
               //Set network connected required to periodicWorkRequest
                .setConstraints(new Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED).build());
        PeriodicWorkRequest request = notificationWork.build();

        WorkManager.getInstance().enqueueUniquePeriodicWork(tag, ExistingPeriodicWorkPolicy.REPLACE , request);
    }


    public static void cancelReminder(String tag) {
        WorkManager instance = WorkManager.getInstance();
        instance.cancelAllWorkByTag(tag);
    }

    private String makeCorrectNotificationMessage(int hits){
      if (hits == -1) {
          return "Error";
      } else {
          return "There is some new article(s) that might interest you";
      }

    }

    private void sendNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getApplicationContext().getResources().getString(R.string.notification_title))
                .setContentText(preferences.getString(PREF_KEY_NOTIFICATION_MESSAGE));

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
        builder.setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager)getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(ID_WORKER_NOTIFICATION, builder.build());
    }

}
