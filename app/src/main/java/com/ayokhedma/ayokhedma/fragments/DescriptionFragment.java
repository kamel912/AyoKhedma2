package com.ayokhedma.ayokhedma.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ayokhedma.ayokhedma.R;
import com.ayokhedma.ayokhedma.adapters.PhoneAdapter;
import com.ayokhedma.ayokhedma.connection.ApiClient;
import com.ayokhedma.ayokhedma.connection.ApiInterface;
import com.ayokhedma.ayokhedma.models.ObjectModel;
import com.ayokhedma.ayokhedma.models.UserModel;
import com.ayokhedma.ayokhedma.ui.ObjectActivity;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class DescriptionFragment extends Fragment{
    ApiInterface apiInterface;
    TextView address,description,worktime,weekend;
    private ObjectModel object;
    ProgressDialog progress;
    private ObjectActivity objectActivity;
    RatingBar ratingBar;
    SharedPreferences sharedPreferences;
    UserModel user;
    String id;
    ImageView obj_pic;
    private String objimage_path = "http://www.fatmanoha.com/ayokhedma/images/object/";
    List<String> phones = new ArrayList<>();
    PhoneAdapter phoneAdapter;
    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;


    public DescriptionFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_description, container, false);
        obj_pic = (ImageView) view.findViewById(R.id.obj_img);
        address = (TextView) view.findViewById(R.id.address);
        description = (TextView) view.findViewById(R.id.description);
        worktime = (TextView) view.findViewById(R.id.worktime);
        weekend = (TextView) view.findViewById(R.id.weekend);
        ratingBar = (RatingBar) view.findViewById(R.id.rating);
        /*ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if(fromUser){setRate(rating);}
            }
        });
        */
        //ddefine progress dialog
        progress = new ProgressDialog(getActivity());
        progress.setMessage("Please wait ....");
        progress.setMax(100);
        progress.show();

        //define recyler of phones
        layoutManager = new GridLayoutManager(getActivity(),4);
        recyclerView = (RecyclerView) view.findViewById(R.id.phone_recycler);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        //define object image
        String path = objimage_path + id + ".png";
        Glide.with(getActivity()).load(path).error(R.drawable.defaul).into(obj_pic);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getActivity().getSharedPreferences("userPreferences", Context.MODE_PRIVATE);

        objectActivity = (ObjectActivity) getActivity();
        id = objectActivity.getIntent().getStringExtra("id");

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        getData();
    }

    private void getData(){
        apiInterface.getObject(id).enqueue(new Callback<ObjectModel>() {
            @Override
            public void onResponse(Call<ObjectModel> call, Response<ObjectModel> response) {
                object = response.body();
                String start1 = trimming(object.getStart1()),
                        end1 = trimming(object.getEnd1()),
                        start2 = trimming(object.getStart2()),
                        end2 = trimming(object.getEnd2()),
                        week = object.getWeekend(),
                        work;
                ratingBar.setRating(object.getRate());
                address.setText("العنوان : شارع " + object.getStreet() + " " + object.getBeside());
                description.setText("الوصف : " + object.getDescription());
                if (!week.equals("")){
                    String weekend = " كل الأيام عدا  يوم " + week;
                    if(start2.equals("0:00") && end2.equals("0:00")){
                        work = "أوقات العمل : من " + start1 + " إلى " + end1 + "\n" + weekend;
                    }else {
                        work =  "أوقات العمل : " + "\n" + "الفترة الأولى : من " +start1 + " إلى " + end1 + "\n" +
                            " الفترة الثانية : من  " + start2 + " إلى " + end2 + "\n" + weekend;
                }

                    worktime.setText(work);

                }else{
                    if(start2.equals("0:00") && end2.equals("0:00")){
                        work = "أوقات العمل : من " + start1 + " إلى " + end1;
                    }else {
                        work =  "أوقات العمل : " + "\n" + "الفترة الأولى : من " +start1 + " إلى " + end1 + "\n" +
                                " الفترة الثانية : من  " + start2 + " إلى " + end2;
                    }

                    worktime.setText(work);
                }
                phones = object.getPhones();
                final String phone = phones.get(0);
                if (phone == null){
                    recyclerView.setVisibility(View.GONE);
                }else {
                    phoneAdapter = new PhoneAdapter(getActivity(), phones);
                    recyclerView.setAdapter(phoneAdapter);
                }

                //Hide the progress dialog
                progress.hide();

            }

            @Override
            public void onFailure(Call<ObjectModel> call, Throwable t) {
                Toast.makeText(getActivity(), "تعذر الاتصال بالخادم", Toast.LENGTH_SHORT).show();
                Log.d("message",t.getMessage());
            }
        });
    }



    private String trimming(String string){
        StringBuilder builder = new StringBuilder();
        String time;
        int j;

        if(string.charAt(0) == '0'){
            j = 1;
        }else{
            j = 0;
        }
        for (int i = j ; i <5; i++){
            builder.append(string.charAt(i));
        }
        time = builder.toString();
        return  time;
    }

}
