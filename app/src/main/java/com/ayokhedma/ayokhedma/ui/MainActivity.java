package com.ayokhedma.ayokhedma.ui;

import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
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
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    List<CategoryModel> categories = new ArrayList<>();
    RecyclerView recyclerView;
    GridLayoutManager layoutManager;
    private CategoryAdapter adapter;
    ApiInterface apiInterface;
    private String image_path = "http://www.ayokhedma.com/app/images/mainlogo.png";
    private ImageView logo;
    SearchView searchView;
    SharedPreferences sharedPreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ToolBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //Shared preferences
        sharedPreferences = getSharedPreferences("userprefences",Context.MODE_PRIVATE);

        //main activity logo
        logo = (ImageView) findViewById(R.id.imageView);
        Glide.with(this).load(image_path).into(logo);

        //recycler of categories
        layoutManager  = new GridLayoutManager(MainActivity.this, 3);
        recyclerView = (RecyclerView) findViewById(R.id.main_recycler);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        //connect to get data
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<CategoryModel>> call = apiInterface.getMainCategories("6");
        call.enqueue(new Callback<List<CategoryModel>>() {
            @Override
            public void onResponse(Call<List<CategoryModel>> call, Response<List<CategoryModel>> response) {
                categories = response.body();
                adapter = new CategoryAdapter(MainActivity.this, categories);
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<List<CategoryModel>> call, Throwable throwable) {
                Toast.makeText(MainActivity.this, "تعذر الاتصال بالخادم", Toast.LENGTH_SHORT).show();
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
