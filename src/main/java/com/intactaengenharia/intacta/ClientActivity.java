package com.intactaengenharia.intacta;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.intactaengenharia.intacta.Adapter.ObrasAdapter;
import com.intactaengenharia.intacta.Beans.Obra;
import com.intactaengenharia.intacta.Beans.User;
import com.intactaengenharia.intacta.Interfaces.ObraModelListener;
import com.intactaengenharia.intacta.Interfaces.UserModelListener;
import com.intactaengenharia.intacta.Model.ObraModel;
import com.intactaengenharia.intacta.Model.UserModel;

import java.util.ArrayList;

public class ClientActivity extends AppCompatActivity implements UserModelListener.OnCompleteUserListener, ObraModelListener.OnCompleteObrasListener {

    private RecyclerView obrasrecycler;
    private AppBarLayout appbarlayout;
    private CollapsingToolbarLayout collapsetoolbar;
    private Toolbar toolbar;
    private User user;
    //private ArrayList<Obra> obraArrayList;
    private Activity activity = this;
    private TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        initView();
    }

    private void initView() {
        obrasrecycler = findViewById(R.id.obrasrecycler);
        appbarlayout = findViewById(R.id.appbarlayout);
        collapsetoolbar = findViewById(R.id.collapsetoolbar);
        toolbar = findViewById(R.id.toolbar);
        title = findViewById(R.id.title);
        setSupportActionBar(toolbar);

        //getClient();
        Intent i = getIntent();
        UserModel userModel = new UserModel();
        if(!i.getStringExtra("cnpj").isEmpty()) {
            userModel.getUser(this, i.getStringExtra("cnpj"));
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        appbarlayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsetoolbar.setTitle(user.getName());
                    isShow = true;
                } else if (isShow) {
                    collapsetoolbar.setTitle(" ");//careful there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });
        Typeface typeface = Typeface.createFromAsset(activity.getAssets(), "fonts/quicksandmedium.ttf");
        collapsetoolbar.setCollapsedTitleTypeface(typeface);
        collapsetoolbar.setExpandedTitleTypeface(typeface);

    }


    @Override
    public void callbackUser(User user) {
        if (user == null) {
            return;
        }
        title.setText(user.getName());
        ObraModel obraModel = new ObraModel(user);
        obraModel.getObras(this);
        //loadobras(user.getCnpj());

    }

    @Override
    public void callbackObras(ArrayList<Obra> obralist) {
        ObrasAdapter adapter = new ObrasAdapter(obralist, activity);
        obrasrecycler.setAdapter(adapter);
        GridLayoutManager llm = new GridLayoutManager(activity, 1, RecyclerView.VERTICAL, false);
        obrasrecycler.setLayoutManager(llm);
        adapter.notifyDataSetChanged();
    }

}
