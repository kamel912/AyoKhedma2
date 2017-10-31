package com.ayokhedma.ayokhedma.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ayokhedma.ayokhedma.R;
import com.ayokhedma.ayokhedma.adapters.CommentAdapter;
import com.ayokhedma.ayokhedma.connection.ApiClient;
import com.ayokhedma.ayokhedma.connection.ApiInterface;
import com.ayokhedma.ayokhedma.models.ObjectModel;
import com.ayokhedma.ayokhedma.models.UserModel;
import com.ayokhedma.ayokhedma.ui.ObjectActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommentFragment extends Fragment{

    String id,count;
    private String link;
    private Gson gson;
    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    private List<ObjectModel.CommentModel> comments = new ArrayList<>();
    private CommentAdapter adapter;
    private ObjectActivity objectActivity;
    Button add_comment_text,add,cancel;
    ObjectModel.CommentModel comment;
    SharedPreferences sharedPreferences;
    UserModel user;
    TextView comment_count;
    RelativeLayout comments_parent,add_comment_parent;
    EditText subject_field,commentBody_field;
    ProgressDialog progress;
    ApiInterface apiInterface;


    public CommentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comment, container, false);


        layoutManager = new GridLayoutManager(getActivity(),1);
        recyclerView = (RecyclerView) view.findViewById(R.id.comment_recycler);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        comment_count = (TextView) view.findViewById(R.id.comment_count);
        add_comment_text = (Button) view.findViewById(R.id.add_comment_text);
        add = (Button) view.findViewById(R.id.add);
        cancel = (Button) view.findViewById(R.id.cancel);
        add_comment_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addComment();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });

        progress = new ProgressDialog(getActivity());
        progress.setMessage("Please wait ....");
        progress.setMax(100);
        progress.show();

        subject_field = (EditText) view.findViewById(R.id.subject_field);
        commentBody_field = (EditText) view.findViewById(R.id.comment_field);

        comments_parent = (RelativeLayout) view.findViewById(R.id.comments_parent);
        add_comment_parent = (RelativeLayout) view.findViewById(R.id.add_comment_parent);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences("userprefences", Context.MODE_PRIVATE);



        objectActivity = (ObjectActivity) getActivity();
        id = objectActivity.getIntent().getStringExtra("id");
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        getData(id);


    }



    private void getData(String objid){
        apiInterface.getComments(objid).enqueue(new Callback<List<ObjectModel.CommentModel>>() {
            @Override
            public void onResponse(Call<List<ObjectModel.CommentModel>> call, Response<List<ObjectModel.CommentModel>> response) {
                comments = response.body();
                if (comments != null) {
                    recyclerView.setVisibility(View.VISIBLE);
                    adapter = new CommentAdapter(getActivity(), comments);
                    recyclerView.setAdapter(adapter);
                    count = String.valueOf(comments.size());
                    comment_count.setText(commentCount(count));
                }else {
                    recyclerView.setVisibility(View.GONE);
                    comment_count.setText("لا يوجد تعليقات");
                }
                progress.hide();
            }

            @Override
            public void onFailure(Call<List<ObjectModel.CommentModel>> call, Throwable t) {

            }
        });
    }



    public void add(){
        comments_parent.setVisibility(View.GONE);
        add_comment_parent.setVisibility(View.VISIBLE);
    }
    public void cancel(){
        comments_parent.setVisibility(View.VISIBLE);
        add_comment_parent.setVisibility(View.GONE);
    }
    public void addComment (){
        progress.show();

        subject_field.setText("");
        commentBody_field.setText("");
    }
    private void displayAlert(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Server response ....");
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cancel();

                }
        });
        builder.create();
        builder.show();
    }
    private String commentCount(String string){
        String c = null;
        if (Integer.parseInt(string) < 4 ) {
            switch (string) {
                case "1":
                    c = "واحد تعليق";
                    break;
                case "2":
                    c = "يوجد تعليقان";
                    break;
                case "3":
                    c = "يوجد ثلاثة تعليقات";
                    break;
            }
        }else {
            c = string + " تعليقات";
        }
        return c;
    }
}
