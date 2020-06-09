package com.orchtech.assem.rxrecap.fligh_app.network;

import com.orchtech.assem.rxrecap.fligh_app.network.model.Price;
import com.orchtech.assem.rxrecap.fligh_app.network.model.Ticket;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FlightsApiService {


    @GET("airline-tickets.php")
    Single<List<Ticket>> searchTickets(@Query("from") String from, @Query("to") String to);

    @GET("airline-tickets-price.php")
    Single<Price> getPrice(@Query("flight_number") String flightNumber, @Query("from") String from, @Query("to") String to);

}
