package com.orchtech.assem.rxrecap;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    /**
     * Basic Observable, Observer, Subscriber example
     * Observable emits list of animal names
     * source:
     * https://www.androidhive.info/RxJava/android-getting-started-with-reactive-programming/
     * source code:
     */
    private String TAG = "RxRecapTag";
    // 5.
    // Disposable is used and calling disposable.
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1.
        // Create Observable that emits list of animal names.
        Observable<String> animalsObservable = getAnimalsObservable();


        // 2.
        // Create an Observer that listen to Observable.
        Observer<String> animalsObserver = getAnimalsObserver();

        // 4.
        // Make Observer subscribe to Observable so that it can start receiving the data.
        // Here, you can notice two more methods, observeOn() and subscribeOn().
        animalsObservable
                // This tell the Observable to run the task on a background thread.
                .subscribeOn(Schedulers.io())
                // This tells the Observer to receive the data on android UI thread
                // so that you can take any UI related actions.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(animalsObserver);

    }

    // 1.
    // observable
    private Observable<String> getAnimalsObservable() {
        return Observable.fromArray(
                "Ant", "Ape",
                "Bat", "Bee",
                "Bear", "Butterfly",
                "Cat", "Crab",
                "Cod", "Dog",
                "Dove", "Fox",
                "Frog", "Lion");
    }

    // 3.
    // Observer provides the below interface methods to know the the state of Observable
    // Observer
    private Observer<String> getAnimalsObserver() {
        return new Observer<String>() {
            // Will be called when an Observer subscribes to Observable
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe");
                // 5.
                // pass disposable
                disposable = d;
            }

            // Will be called when Observable starts emitting the data.
            @Override
            public void onNext(String s) {
                Log.d(TAG, "Name: " + s);
            }

            // Will be called in case of any error
            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: " + e.getMessage());
            }

            // Will be called when an Observable completes the emission of all the items
            @Override
            public void onComplete() {
                Log.d(TAG, "All items are emitted!");
            }
        };
    }

    // 5.
    // dispose() in onDestroy() will un-subscribe the Observer.
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // don't send events once the activity is destroyed
        disposable.dispose();
    }
}