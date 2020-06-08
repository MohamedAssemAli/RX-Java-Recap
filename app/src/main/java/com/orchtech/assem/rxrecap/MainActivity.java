package com.orchtech.assem.rxrecap;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
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
    // 7.
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

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

        // 7.
        //  animalsObserverDisposable and animalsObserverAllCapsDisposable subscribed to same Observable.
        //  The both observers receives the same data but the data changes as different operators are applied on the stream.
        DisposableObserver<String> animalsObserverDisposable = getAnimalsObserverDisposable();
        DisposableObserver<String> animalsObserverAllCapsDisposable = getAnimalsAllCapsObserverDisposable();


        // 4.
        // Make Observer subscribe to Observable so that it can start receiving the data.
        // Here, you can notice two more methods, observeOn() and subscribeOn().
//        animalsObservable
//                // This tell the Observable to run the task on a background thread.
//                .subscribeOn(Schedulers.io())
//                // This tells the Observer to receive the data on android UI thread
//                // so that you can take any UI related actions.
//                .observeOn(AndroidSchedulers.mainThread())
//                // 6.
//                // Adding filters
//                //  filter() operator is used to filter out the emitted data
//                .filter(new Predicate<String>() {
//                    @Override
//                    public boolean test(String s) {
//                        return s.toLowerCase().startsWith("b");
//                    }
//                })
//                .subscribe(animalsObserver);


        /**
         * filter() is used to filter out the animal names starting with `b`
         * */
        compositeDisposable.add(
                animalsObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .filter(new Predicate<String>() {
                            @Override
                            public boolean test(String s) throws Exception {
                                return s.toLowerCase().startsWith("b");
                            }
                        })
                        .subscribeWith(animalsObserverDisposable)
        );

        /**
         * filter() is used to filter out the animal names starting with 'c'
         * map() is used to transform all the characters to UPPER case
         * */
        compositeDisposable.add(
                animalsObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .filter(new Predicate<String>() {
                            @Override
                            public boolean test(String s) throws Exception {
                                return s.toLowerCase().startsWith("c");
                            }
                        })
                        .map(new Function<String, String>() {
                            @Override
                            public String apply(String s) {
                                return s.toUpperCase();
                            }
                        })
                        .subscribeWith(animalsObserverAllCapsDisposable));
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

    // 7.
    private DisposableObserver<String> getAnimalsObserverDisposable() {
        return new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                Log.d(TAG, "Name: " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "All items are emitted!");
            }
        };
    }

    // 7.
    private DisposableObserver<String> getAnimalsAllCapsObserverDisposable() {
        return new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                Log.d(TAG, "Name: " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "All items are emitted!");
            }
        };
    }

    // 5.
    // dispose() in onDestroy() will un-subscribe the Observer.
    // 7.
    // CompositeDisposable: Can maintain list of subscriptions in a pool and can dispose them all at once
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // don't send events once the activity is destroyed
        disposable.dispose();

        // 7.
        compositeDisposable.clear();
    }
}
