package com.example.quoteit.ui;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quoteit.Constants;
import com.example.quoteit.R;
import com.example.quoteit.models.Quote;
import com.example.quoteit.services.Api;

import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuotesListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes_list);
        final ListView listView = findViewById(R.id.listView);

        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
        OkHttpClient okHttpClient = builder.build();

        Retrofit retrofit = new Retrofit.Builder( )
                .baseUrl(Constants.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create( ))
                .build( );

        Api api = retrofit.create(Api.class);
        Call<List<Quote>> call = api.getQuote();
        call.enqueue(new Callback<List<Quote>>( ) {
            @Override
            public void onResponse(Call<List<Quote>> call, Response<List<Quote>> response) {
                List<Quote> quotes = response.body();
                String[] quotesList = new String[quotes.size()];

                for (int i = 0; i < quotes.size(); i++) {
                    quotesList[i] = quotes.get(i).getQuote();
                }

                listView.setAdapter(
                        new ArrayAdapter<>(
                                QuotesListActivity.this,
                                android.R.layout.simple_list_item_1,
                                quotesList
                        )
                );
            }

            @Override
            public void onFailure(Call<List<Quote>> call, Throwable t) {
                Toast.makeText(getApplicationContext( ), t.getMessage( ), Toast.LENGTH_LONG).show( );
            }
        });
    }                                                                                                                                                                                                               
}
