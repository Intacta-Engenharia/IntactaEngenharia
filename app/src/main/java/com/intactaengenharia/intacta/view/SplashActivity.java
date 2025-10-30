package com.intactaengenharia.intacta.view;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;
import com.intactaengenharia.intacta.R;
import com.intactaengenharia.intacta.service.listeners.ValidationListener;
import com.intactaengenharia.intacta.service.model.User;
import com.intactaengenharia.intacta.viewmodel.UserViewModel;
import com.santalu.maskedittext.MaskEditText;

public class SplashActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout rlLogin;
    private Button buttonLogin;
    private RelativeLayout rlSplash;
    private LinearLayout llSplash;
    private MaskEditText editCnpj;
    private EditText editPassword;
    private TextInputLayout txlPassword;
    private CheckBox rememberme;
    private UserViewModel mViewModel;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
        mViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        observer();
        setListeners();
        mViewModel.verifyLoggedUser();

    }

    private void setListeners() {
        buttonLogin.setOnClickListener(this);
    }

    private void observer() {
        mViewModel.getLogged().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!aBoolean) {
                            Rect bounds = new Rect();
                            rlLogin.getDrawingRect(bounds);

                            int cx = bounds.centerX();
                            int cy = bounds.centerY();
                            int radius = Math.max(rlSplash.getWidth(), rlSplash.getHeight());
                            Animator anim = ViewAnimationUtils.createCircularReveal(rlLogin, cx, cy,
                                    100, radius);
                            anim.setDuration(1200);
                            rlLogin.setVisibility(View.VISIBLE);

                            anim.start();
                            llSplash.setVisibility(View.GONE);
                        } else {
                            startActivity(new Intent(SplashActivity.this, ObraActivity.class));
                        }
                    }
                }, 1000);
            }
        });
        mViewModel.getLogin().observe(this, new Observer<ValidationListener>() {
            @Override
            public void onChanged(ValidationListener validationListener) {
                if (validationListener.success()) {
                    startActivity(new Intent(SplashActivity.this, ObraActivity.class));
                } else {
                    Toast.makeText(SplashActivity.this, validationListener.failure(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        mViewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                txlPassword.setVisibility(View.VISIBLE);
                rememberme.setVisibility(View.VISIBLE);
                mUser = user;
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_login) {
            if (txlPassword.getVisibility() == View.GONE) {
                mViewModel.doLogin(editCnpj.getRawText());
            } else {
                mViewModel.verifyPassword(editCnpj.getRawText(), editPassword.getText().toString(), mUser);
            }
        }

    }

    private void initView() {
        rlSplash = findViewById(R.id.rl_splash_activity);
        llSplash = findViewById(R.id.ll_splash);
        rlLogin = findViewById(R.id.rl_login);
        buttonLogin = findViewById(R.id.btn_login);
        rememberme = findViewById(R.id.rememberme);
        editCnpj = findViewById(R.id.edit_cnpj);
        txlPassword = findViewById(R.id.txl_pass);
        editPassword = findViewById(R.id.edit_pass);
    }


}
