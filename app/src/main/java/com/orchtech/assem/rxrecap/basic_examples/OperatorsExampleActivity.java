package com.orchtech.assem.rxrecap.basic_examples;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.orchtech.assem.rxrecap.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class OperatorsExampleActivity extends AppCompatActivity {

    /*
     Operators introduction
     source:
     https://www.androidhive.info/RxJava/rxjava-operators-introduction/
     */
    private String TAG = OperatorsExampleActivity.class.getSimpleName();
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operators_example);

        // 1.
//        basicExample();
//
//        // 2. Just()
//        justExample();
//
//        // 3. From()
//        fromExample();
//
//        // 4. Range()
//        rangeExample();
//
//        // 5. Repeat()
//        repeatExample();
//
//        // 6. Buffer()
//        bufferExample();
//
//        // 7. Math
//        mathExample();
//
//        // 8. Concat()
//        concatExample();
//
//        // 9. Merge()
//        mergeExample();
//
//        // 10. Map()
//        mapExample();

        // 11. FlatMap()
        flatOrContactMapExample();
    }

    // 1.
    // Instead of writing the array of numbers manually, you can do the same using range(1, 20) operator as below.
    // range(): Range operator generates the numbers from 1 to 20
    // filter(): Filters the numbers by applying a condition onto each number
    // map(): Map transform the data from Integer to String by appending the string at the end
    // In the operator chain, filter() will be executed first and map() takes the result from filter and performs it’s job
    private void basicExample() {
        Observable.range(1, 20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) {
                        return integer % 2 == 0;
                    }
                })
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) {
                        return integer + " is even number";
                    }
                })
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(String s) {
                        Log.d(TAG, "onNext: " + s);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "All numbers emitted!");
                    }
                });
    }


    // Just, From, Range and Repeat
    // https://www.androidhive.info/RxJava/rxjava-operators-just-range-from-repeat/

    // 2.
    // Just() operator takes a list of arguments and converts the items into Observable items.
    // It takes arguments between one to ten (But the official document says one to nine 🙁 , may be it’s language specific).
    // just() – Makes only 1 emission. .just(new Integer[]{1, 2, 3}) makes one emission with Observer callback as onNext(Integer[] integers)
    private void justExample() {
        // Let’s consider the below example.
        // Here an Observable is created using just() from a series of integers.
        // The limitation of just() is, you can’t pass more than 10 arguments.
        Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "onNext: " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        // The below example creates an Observable from an array.
        // Here you should notice that the array is emitted as single item instead of individual numbers.
        //The Observer emits the array onNext(Integer[] integers) so you will always have 1 emission irrespective of length of the array.

        Integer[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        Observable.just(numbers)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer[]>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer[] integers) {
                        Log.d(TAG, "onNext: " + integers.length);

                        // you might have to loop through the array
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    // 3.
    // Unlike just, From() creates an Observable from set of items using an Iterable, which means each item is emitted one at a time
    // Notice that we are using fromArray() operator as in RxJava2 we have don’t have from().
    // There is also fromCallable(), fromFuture(), fromIterable() and fromPublisher() operators available.
    // fromArray() – Makes N emissions. .fromArray(new Integer[]{1, 2, 3}) makes three emission with Observer callback as onNext(Integer integer)
    private void fromExample() {
        Integer[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        Observable.fromArray(numbers)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "onNext: " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    // 4.
    // Range() creates an Observable from a sequence of generated integers.
    // The function generates sequence of integers by taking starting number and length.
    // So, the above same examples can be modified as Observable.range(1, 10).
    private void rangeExample() {
        Observable.range(1, 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "onNext: " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    // 5.
    // Repeat() creates an Observable that emits an item or series of items repeatedly.
    // You can also pass an argument to limit the number of repetitions.
    //The below example repeats the emission of integers from 1-4 three times using repeat(3).
    private void repeatExample() {
        Observable
                .range(1, 4)
                .repeat(3)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "Subscribed");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "onNext: " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "Completed");
                    }
                });
    }

    /*
     Buffer, Debounce
     https://www.androidhive.info/RxJava/rxjava-operators-buffer-debounce/
     */

    // 6.
    // Buffer gathers items emitted by an Observable into batches and emit the batch instead of emitting one item at a time.
    //Below, we have an Observable that emits integers from 1-9. When buffer(3) is used, it emits 3 integers at a time.
    private void bufferExample() {
        Observable.just(1, 2, 3, 4,
                5, 6, 7, 8, 9).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .buffer(3)
                .subscribe(new Observer<List<Integer>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Integer> integers) {
                        Log.d(TAG, "onNext");
                        for (Integer integer : integers) {
                            Log.d(TAG, "Item: " + integer);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "All items emitted!");
                    }
                });
    }

    /*
     Max, Min, Sum, Average, Count & Reduce
     https://www.androidhive.info/RxJava/mathematical-operators-rxjava/
     */

    // 7.
    // Max, Min, Sum, Average, Count & Reduce
    // it has conflict with rx-java2
    private void mathExample() {
//        Integer[] numbers = {5, 101, 404, 22, 3, 1024, 65};
//
//        Observable<Integer> observable = Observable.from(numbers);
//
//        MathObservable
////                .sumInteger(observable)
////                .min(observable)
//                .max(observable)
//                .subscribe(new Subscriber<Integer>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(Integer integer) {
//                        Log.d(TAG, "Max value: " + integer);
//                    }
//                });
    }

   /*
    Concat and Merge
    https://www.androidhive.info/RxJava/map-flatmap-switchmap-concatmap/
    */

    // 8.
    // Concat operator combines output of two or more Observables into a single Observable.
    // Concat operator always maintains the sequential execution without interleaving the emissions.
    private void concatExample() {
        Observable
                .concat(getMaleObservable(), getFemaleObservable())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(User user) {
                        Log.d(TAG, user.getName() + ", " + user.getGender());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    // 9.
    // Merge also merges multiple Observables into a single Observable but it won’t maintain the sequential execution.
    @SuppressLint("CheckResult")
    private void mergeExample() {
        Observable
                .merge(getMaleObservable(), getFemaleObservable())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new Observer<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(User user) {
                        Log.d(TAG, user.getName() + ", " + user.getGender());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    /*
     Map, FlatMap, SwitchMap and ConcatMap
     https://www.androidhive.info/RxJava/map-flatmap-switchmap-concatmap/
    */

    // 10.
    // Map operator transform each item emitted by an Observable and emits the modified item.

    /**
     * Assume getMaleObservable method is making a network call and fetching Users
     * an Observable that emits list of users
     * each User has name and email, but missing email id
     */
    private void mapExample() {
        getMaleObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<User, User>() {
                    @Override
                    public User apply(User user) throws Exception {
                        // modifying user object by adding email address
                        // turning user name to uppercase
                        user.setEmail(String.format("%s@rxjava.wtf", user.getName()));
                        user.setName(user.getName().toUpperCase());
                        return user;
                    }
                })
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(User user) {
                        Log.d(TAG, "onNext: " + user.getName() + ", " + user.getGender() + ", " + user.getEmail() + ", " + user.getAddress());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "All users emitted!");
                    }
                });
    }


    // 11.
    // FlatMap applies a function on each emitted item but instead of returning the modified item,
    // it returns the Observable itself which can emit data again.
    // it can interleave items while emitting i.e the emitted items order is not maintained.
    // FlatMap and ConcatMap work is pretty much same. They merges items emitted by multiple Observables and returns a single Observable.
    // ConcatMap() maintains the order of items and waits for the current Observable to complete its job before emitting the next one.
    // ConcatMap is more suitable when you want to maintain the order of execution.

    /**
     * To better understand FlatMap, consider a scenario where you have a network call to fetch Users with name and gender.
     * Then you have another network that gives you address of each user.
     * Now the requirement is to create an Observable that emits Users with name, gender and address properties.
     * To achieve this, you need to get the users first, then make separate network call for each user to fetch his address.
     * This can be done easily using FlatMap operator
     */
    private void flatOrContactMapExample() {
        getMaleObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //.concatMap(new Function<User, Observable<User>>() {
                .flatMap(new Function<User, ObservableSource<User>>() {
                    @Override
                    public Observable<User> apply(User user) throws Exception {

                        // getting each user address by making another network call
                        return getAddressObservable(user);
                    }
                })
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe");
                        disposable = d;
                    }

                    @Override
                    public void onNext(User user) {
                        Log.d(TAG, "onNext: " + user.getName() + ", " + user.getGender() + ", " + user.getAddress());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "All users emitted!");
                    }
                });
    }


    private Observable<User> getFemaleObservable() {
        String[] names = new String[]{"Lucy", "Scarlett", "April"};

        final List<User> users = new ArrayList<>();
        for (String name : names) {
            User user = new User();
            user.setName(name);
            user.setGender("female");
            users.add(user);
        }

        return Observable
                .create(new ObservableOnSubscribe<User>() {
                    @Override
                    public void subscribe(ObservableEmitter<User> emitter) throws Exception {
                        for (User user : users) {
                            if (!emitter.isDisposed()) {
                                emitter.onNext(user);
                            }
                        }

                        if (!emitter.isDisposed()) {
                            emitter.onComplete();
                        }
                    }
                }).subscribeOn(Schedulers.io());
    }

    private Observable<User> getMaleObservable() {
        String[] names = new String[]{"Mark", "John", "Trump", "Obama"};

        final List<User> users = new ArrayList<>();

        for (String name : names) {
            User user = new User();
            user.setName(name);
            user.setGender("male");
            users.add(user);
        }
        return Observable
                .create(new ObservableOnSubscribe<User>() {
                    @Override
                    public void subscribe(ObservableEmitter<User> emitter) throws Exception {
                        for (User user : users) {
                            if (!emitter.isDisposed()) {
                                emitter.onNext(user);
                            }
                        }

                        if (!emitter.isDisposed()) {
                            emitter.onComplete();
                        }
                    }
                }).subscribeOn(Schedulers.io());
    }

    /**
     * Assume this as a network call
     * returns Users with address filed added
     */
    private Observable<User> getAddressObservable(final User user) {

        final String[] addresses = new String[]{
                "1600 Amphitheatre Parkway, Mountain View, CA 94043",
                "2300 Traverwood Dr. Ann Arbor, MI 48105",
                "500 W 2nd St Suite 2900 Austin, TX 78701",
                "355 Main Street Cambridge, MA 02142"
        };

        return Observable
                .create(new ObservableOnSubscribe<User>() {
                    @Override
                    public void subscribe(ObservableEmitter<User> emitter) throws Exception {
                        String address;
                        address = addresses[new Random().nextInt(2)];
                        if (!emitter.isDisposed()) {
                            user.setAddress(address);
                            // Generate network latency of random duration
                            int sleepTime = new Random().nextInt(1000) + 500;
                            Thread.sleep(sleepTime);
                            emitter.onNext(user);
                            emitter.onComplete();
                        }
                    }
                }).subscribeOn(Schedulers.io());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}