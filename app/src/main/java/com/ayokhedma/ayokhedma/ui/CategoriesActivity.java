package com.ayokhedma.ayokhedma.ui;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.ayokhedma.ayokhedma.R;
import com.ayokhedma.ayokhedma.adapters.CategoryAdapter;
import com.ayokhedma.ayokhedma.connection.ApiClient;
import com.ayokhedma.ayokhedma.connection.ApiInterface;
import com.ayokhedma.ayokhedma.models.CategoryModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriesActivity extends AppCompatActivity {
    List<CategoryModel> categories = new ArrayList<>();
    RecyclerView recyclerView;
    GridLayoutManager layoutManager;
    private CategoryAdapter adapter;
    ApiInterface apiInterface;
    SearchView searchView;
    SharedPreferences sharedPreferences;
    ProgressDialog progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        //ToolBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.categoris_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //Shared preferences
        sharedPreferences = getSharedPreferences("userprefences", Context.MODE_PRIVATE);

        //Define progress dialog options
        progress = new ProgressDialog(this);
        progress.setMessage("Please wait ....");
        progress.setMax(100);
        progress.show();

        //recycler of categories
        layoutManager  = new GridLayoutManager(CategoriesActivity.this, 3);
        recyclerView = (RecyclerView) findViewById(R.id.categories_recycler);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        //connect to get data
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<CategoryModel>> call = apiInterface.getAllCategories();
        call.enqueue(new Callback<List<CategoryModel>>() {
            @Override
            public void onResponse(Call<List<CategoryModel>> call, Response<List<CategoryModel>> response) {
                categories = response.body();
                adapter = new CategoryAdapter(CategoriesActivity.this, categories);
                recyclerView.setAdapter(adapter);
                progress.dismiss();
            }
            @Override
            public void onFailure(Call<List<CategoryModel>> call, Throwable t) {
                Toast.makeText(CategoriesActivity.this, "تعذر الاتصال بالخادم", Toast.LENGTH_SHORT).show();
                Log.d("message",t.getMessage());
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Define menu
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main,menu);

        //Define search item
        MenuItem searchItem = menu.findItem(R.id.search_item);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) searchItem.getActionView();
        if(searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setIconified(true);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout :
                if(sharedPreferences.contains("userInfo")){
                    sharedPreferences.edit().clear().apply();
                    Toast.makeText(this, "تم تسجيل الخروج", Toast.LENGTH_SHORT).show();
                }
        }
        return super.onOptionsItemSelected(item);
    }
}
