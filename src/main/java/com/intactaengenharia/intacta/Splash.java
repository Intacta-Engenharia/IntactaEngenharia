package com.intactaengenharia.intacta;

import android.animation.Animator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.intactaengenharia.intacta.Beans.User;
import com.intactaengenharia.intacta.Interfaces.UserModelListener;
import com.intactaengenharia.intacta.Model.UserModel;
import com.intactaengenharia.intacta.Utilities.Preferences;
import com.santalu.maskedittext.MaskEditText;

public class Splash extends AppCompatActivity implements UserModelListener.OnCompleteLoginListener {
    private Activity activity = this;
    private RelativeLayout view;
    private TextView title;
    private TextInputLayout username;
    private TextInputLayout password;
    private ProgressBar progress;
    private RelativeLayout loginlayout;
    private LinearLayout loading;
    private Button login;
    private EditText passtext;
    private TextView message;
    private CheckBox rememberme;
    private MaskEditText usertxt;

    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
        CountDownTimer timer = new CountDownTimer(1500, 100) {
            @Override
            public void onTick(long millisUntilFinished) {

            }
            @Override
            public void onFinish() {
                Preferences preferences = new Preferences(activity);

                if (preferences.getLogin().isEmpty()) {

                    Rect bounds = new Rect();
                    loginlayout.getDrawingRect(bounds);

                    int cx = bounds.centerX();
                    int cy = bounds.centerY();
                    int radius = Math.max(view.getWidth(), view.getHeight());
                    Animator anim = ViewAnimationUtils.createCircularReveal(loginlayout, cx, cy,
                            100, radius);
                    anim.setDuration(1200);
                    loginlayout.setVisibility(View.VISIBLE);

                    anim.start();
                    loading.setVisibility(View.GONE);
                } else {
                    Splash.this.start();
                }
            }
        }.start();
        login.setOnClickListener(v -> {
            if (password.getVisibility() == View.GONE) {
                UserModel userModel = new UserModel();
                userModel.getLogin(this, usertxt.getRawText());
            } else {
                if (passtext.getText().toString().equals(user.getPassword())) {
                    if (rememberme.isChecked()) {
                        Preferences preferences = new Preferences(activity);
                        preferences.setLogged(user.getCnpj());
                        //System.out.println("remember key " + preferences.getpreference());
                        start();
                    }else{
                        Intent i = new Intent(this, ClientActivity.class);
                        i.putExtra("cnpj", user.getCnpj());
                        startActivity(i);
                        this.finish();
                    }
                } else {
                    password.setError("Senha incorreta");
                }
            }
        });


    }

    @Override
    public void callbackLogin(User user) {
        this.user = user;
        if (usertxt.getRawText().equals(user.getCnpj())) {
            password.setVisibility(View.VISIBLE);
            rememberme.setVisibility(View.VISIBLE);
            message.setText("Digite a senha");
        } else {
            Toast.makeText(activity, "Usuário não encontrado", Toast.LENGTH_LONG).show();

        }
    }


    private void start() {
        Preferences preferences = new Preferences(this);
        Intent i = new Intent(this, ClientActivity.class);
        i.putExtra("cnpj", preferences.getLogin());
        startActivity(i);
        this.finish();
    }

    private void initView() {
        view = findViewById(R.id.view);
        title = findViewById(R.id.title);
        password = findViewById(R.id.password);
        loginlayout = findViewById(R.id.loginlayout);
        loading = findViewById(R.id.loading);
        login = findViewById(R.id.login);
        usertxt = findViewById(R.id.usertxt);
        passtext = findViewById(R.id.passtext);
        message = findViewById(R.id.message);
        rememberme = findViewById(R.id.rememberme);
        usertxt = findViewById(R.id.usertxt);
    }


}
