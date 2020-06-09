package com.orchtech.assem.rxrecap.fligh_app.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.orchtech.assem.rxrecap.R;
import com.orchtech.assem.rxrecap.fligh_app.network.FlightsApiService;
import com.orchtech.assem.rxrecap.fligh_app.network.model.Ticket;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;

public class FlightAppActivity extends AppCompatActivity {

    private static final String TAG = FlightAppActivity.class.getSimpleName();
    private static final String from = "DEL";
    private static final String to = "HYD";

    private CompositeDisposable disposable = new CompositeDisposable();
    private Unbinder unbinder;

    private FlightsApiService apiService;
    private TicketsAdapter mAdapter;
    private ArrayList<Ticket> ticketsList = new ArrayList<>();

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.flights_layout)
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_app);
    }
}