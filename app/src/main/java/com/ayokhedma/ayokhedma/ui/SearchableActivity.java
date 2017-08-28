package com.ayokhedma.ayokhedma.ui;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import com.ayokhedma.ayokhedma.R;
import com.ayokhedma.ayokhedma.adapters.ObjectAdapter;
import com.ayokhedma.ayokhedma.connection.ApiClient;
import com.ayokhedma.ayokhedma.connection.ApiInterface;
import com.ayokhedma.ayokhedma.models.ObjectModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchableActivity extends AppCompatActivity  {
    SearchView searchView;
    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    String query = "";
    private List<ObjectModel> objects = new ArrayList<>();
    private ObjectAdapter adapter;
    ProgressDialog progress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);


        progress = new ProgressDialog(this);
        progress.setMessage("Please wait ....");
        progress.setMax(100);
        progress.show();


        layoutManager = new GridLayoutManager(this,1);
        recyclerView = (RecyclerView) findViewById(R.id.search_recycler);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);


        onNewIntent(getIntent());

        SearchManager searchManager = (SearchManager) this.getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) findViewById(R.id.search_view);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setQuery(query, true);
    }


    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            query = intent.getStringExtra(SearchManager.QUERY);
            if (searchView != null) {
                searchView.clearFocus();
            }
            ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
            Call<List<ObjectModel>> search = apiInterface.search(query);
            search.enqueue(new Callback<List<ObjectModel>>() {
                @Override
                public void onResponse(Call<List<ObjectModel>> call, Response<List<ObjectModel>> response) {
                    objects = response.body();
                    adapter = new ObjectAdapter(SearchableActivity.this,objects);
                    recyclerView.setAdapter(adapter);
                    progress.hide();
                }

                @Override
                public void onFailure(Call<List<ObjectModel>> call, Throwable t) {
                    Toast.makeText(SearchableActivity.this, "تعذر الاتصال بالخادم", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }



}
