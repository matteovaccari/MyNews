package com.matt.android.mynews.models.worker;

import android.content.Context;
import android.support.annotation.NonNull;

import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.matt.android.mynews.models.api.NYTStreams;
import com.matt.android.mynews.models.api.search.NewsObject;
import com.matt.android.mynews.models.utils.Logger;
import com.matt.android.mynews.models.utils.SharedPreferencesManager;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

import static com.matt.android.mynews.models.utils.Constants.PREF_KEY_NAME;
import static com.matt.android.mynews.models.utils.Constants.PREF_KEY_NOTIFICATION_URL;


public class NotificationWorker extends Worker {

    private int hits;
    private Disposable disposable;

    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
            doNotificationApiRequest();
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
                createNotification(hits);
            }

            @Override
            public void onError(Throwable e) {
                //Create notification with error message
                hits = -1;
                createNotification(hits);
                Logger.e(e.getMessage());
            }

            @Override
            public void onComplete() {
                Logger.i("notification api request completed");
            }
        });

    }
    

}
