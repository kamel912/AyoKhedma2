package com.ayokhedma.ayokhedma.ui;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ayokhedma.ayokhedma.R;
import com.ayokhedma.ayokhedma.adapters.ObjectAdapter;
import com.ayokhedma.ayokhedma.adapters.RegionAdapter;
import com.ayokhedma.ayokhedma.connection.ApiClient;
import com.ayokhedma.ayokhedma.connection.ApiInterface;
import com.ayokhedma.ayokhedma.models.ObjectModel;
import com.ayokhedma.ayokhedma.models.RegionModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class CategoryActivity extends AppCompatActivity {
    private String catid,catname,regid;
    private List<ObjectModel> objects = new ArrayList<>();
    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    private ObjectAdapter adapter;
    private Spinner spinner;
    private List<RegionModel> regions = new ArrayList<>();
    private RegionAdapter regionAdapter;
    ProgressDialog progress;
    SearchView searchView;
    TextView title;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        //define toolbar and title
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (TextView) toolbar.findViewById(R.id.title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //get data from intent
        Intent intent = getIntent();
        catid = intent.getStringExtra("id");
        catname = intent.getStringExtra("name");
        title.setText(catname);
        getSupportActionBar().setTitle(catname);

        //define progress dialog
        progress = new ProgressDialog(this);
        progress.setMessage("Please wait ....");
        progress.setMax(100);
        progress.show();

        //define spinner of regions
        spinner = (Spinner) findViewById(R.id.spinner);

        //define objects recycler
        layoutManager = new GridLayoutManager(CategoryActivity.this,1);
        recyclerView = (RecyclerView) findViewById(R.id.objs_recycler);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        //define the interface
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        //call regions method
        regions();
    }

    @Override
    protected void onResume() {
        super.onResume();
        regions();
    }

    private void regions(){
        //get data of regions
        apiInterface.getRegions().enqueue(new Callback<List<RegionModel>>() {
            @Override
            public void onResponse(Call<List<RegionModel>> call, Response<List<RegionModel>> response) {
                //populate regions data to spinner
                regions = response.body();
                regionAdapter = new RegionAdapter(CategoryActivity.this,android.R.layout.simple_spinner_dropdown_item,regions);
                regionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(regionAdapter);

                //filter objects using regions
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        regid = ((RegionModel) parent.getItemAtPosition(position)).getId();
                        Call<List<ObjectModel>> listCall;
                        if (regid.equals("1")){
                            progress.show();
                            listCall = apiInterface.getAllObjects(catid);
                        }else {
                            progress.show();
                            listCall = apiInterface.getRegionObjects(catid,regid);
                        }
                        listCall.enqueue(new Callback<List<ObjectModel>>() {
                            @Override
                            public void onResponse(Call<List<ObjectModel>> call, Response<List<ObjectModel>> response) {
                                if (response.equals("null")) {
                                    Toast.makeText(CategoryActivity.this,"لا توجد " + catname + " في هذه المنطقة",Toast.LENGTH_SHORT).show();
                                    progress.hide();
                                }else {
                                    objects = response.body();
                                    adapter = new ObjectAdapter(CategoryActivity.this, objects);
                                    recyclerView.setAdapter(adapter);
                                    progress.hide();
                                }
                            }

                            @Override
                            public void onFailure(Call<List<ObjectModel>> call, Throwable t) {
                                Toast.makeText(CategoryActivity.this, "تعذر الاتصال بالخادم", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onFailure(Call<List<RegionModel>> call, Throwable t) {
                Toast.makeText(CategoryActivity.this, "تعذر الاتصال بالخادم", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);

        final MenuItem searchItem = menu.findItem(R.id.search_item);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setItemsVisibility(menu,searchItem,false);
                title.setVisibility(View.GONE);
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                setItemsVisibility(menu, searchItem, true);
                title.setVisibility(View.VISIBLE);
                return false;
            }
        });
        if(searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setIconified(true);
        }
        return super.onCreateOptionsMenu(menu);
    }
    private void setItemsVisibility(Menu menu, MenuItem exception, boolean visible) {
        for (int i=0; i<menu.size(); ++i) {
            MenuItem item = menu.getItem(i);
            if (item != exception) {
                item.setVisible(visible);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this,CategoriesActivity.class);
        startActivity(intent);
    }

}
