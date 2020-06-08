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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1.
        // Create Observable that emits list of animal names.
        // Here just() operator is used to emit few animal names.
        Observable<String> animalsObservable =
                Observable.just("Ant", "Bee", "Cat", "Dog", "Fox");


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

    // 3.
    // Observer provides the below interface methods to know the the state of Observable
    private Observer<String> getAnimalsObserver() {
        return new Observer<String>() {
            // Will be called when an Observer subscribes to Observable
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe");
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
}