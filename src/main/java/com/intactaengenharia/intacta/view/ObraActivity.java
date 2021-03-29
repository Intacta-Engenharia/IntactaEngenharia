package com.intactaengenharia.intacta.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.intactaengenharia.intacta.R;
import com.intactaengenharia.intacta.service.constants.UserConstants;
import com.intactaengenharia.intacta.service.listeners.OnItemClickListener;
import com.intactaengenharia.intacta.service.utils.Preferences;
import com.intactaengenharia.intacta.view.adapter.ObraAdapter;
import com.intactaengenharia.intacta.service.model.Obra;
import com.intactaengenharia.intacta.viewmodel.ObraViewModel;

import java.util.List;

import de.mateware.snacky.Snacky;

public class ObraActivity extends AppCompatActivity {

    private RecyclerView rvObra;
    private ProgressBar pgObra;
    private AppBarLayout appbarlayout;
    private CollapsingToolbarLayout collapsetoolbar;
    private final Activity activity = this;
    private Preferences mPreferences;
    private ObraViewModel mViewModel;
    private ObraAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obra);
        initView();
        mViewModel = new ViewModelProvider(this).get(ObraViewModel.class);
        observer();
        load(true);
        mViewModel.onLoad();
    }

    //Carregar a lista com todos
    private void load(Boolean isLoading) {
        if (isLoading) pgObra.setVisibility(View.VISIBLE);
        else pgObra.setVisibility(View.GONE);
    }

    private void observer() {
        mViewModel.getObras().observe(this, new Observer<List<Obra>>() {
            @Override
            public void onChanged(List<Obra> obras) {
                mAdapter.notifyChanged(obras);
                load(false);
            }
        });
        mViewModel.getValidation().observe(this, validationListener -> {
            if (!validationListener.success()) {
                showMessage(validationListener.failure());
            }
        });
    }

    private void setupRecycler() {
        GridLayoutManager llm = new GridLayoutManager(activity, 1, RecyclerView.VERTICAL, false);
        mAdapter = new ObraAdapter(new OnItemClickListener<Obra>() {
            @Override
            public void onItemClick(Obra item) {
                Intent i = new Intent(activity, MainActivity.class);
                i.putExtra("keyobra", item.getKey());
                activity.startActivity(i);
            }

            @Override
            public void onLongClick(String id) {
            }
        });
        rvObra.setLayoutManager(llm);
        rvObra.setAdapter(mAdapter);

    }

    private void showMessage(String message) {
        Snacky.builder().setActivity(this).warning().setText(message).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        load(true);
        mViewModel.onLoad();
        appbarlayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsetoolbar.setTitle(mPreferences.get(UserConstants.PREFERENCES.USER_NAME));
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


    private void initView() {
        rvObra = findViewById(R.id.rv_obras);
        pgObra = findViewById(R.id.pg_obra);
        appbarlayout = findViewById(R.id.appbarlayout);
        collapsetoolbar = findViewById(R.id.collapsetoolbar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView title = findViewById(R.id.txt_name_user);
        mPreferences = new Preferences(this);
        title.setText(mPreferences.get(UserConstants.PREFERENCES.USER_NAME));
        setupRecycler();
    }

}
