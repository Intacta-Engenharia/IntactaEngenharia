package com.intactaengenharia.intacta;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.intactaengenharia.intacta.Adapter.DiariesAdapter;
import com.intactaengenharia.intacta.Adapter.EndserviceListAdapter;
import com.intactaengenharia.intacta.Beans.Contact;
import com.intactaengenharia.intacta.Beans.Diary;
import com.intactaengenharia.intacta.Beans.Obra;
import com.intactaengenharia.intacta.Interfaces.DiaryModelListener;
import com.intactaengenharia.intacta.Interfaces.ObraModelListener;
import com.intactaengenharia.intacta.Model.DiaryModel;
import com.intactaengenharia.intacta.Model.ObraModel;
import com.intactaengenharia.intacta.Utilities.Alerts;
import com.intactaengenharia.intacta.Utilities.Preferences;
import com.ramijemli.percentagechartview.PercentageChartView;

import java.text.NumberFormat;
import java.util.ArrayList;

import de.mateware.snacky.Snacky;

public class MainActivityv2 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, DiaryModelListener.OnCompleteDiariesListener, ObraModelListener.OnCompleteObraListener {

    private Activity activity = this;
    private Obra obra;
    private RecyclerView diaryrecycler;
    private PercentageChartView viewId;
    private Toolbar toolbar;
    private TextView empty;
    private String keyobra;
    private TabLayout tabs;
    boolean isdiaries = true;
    boolean isendedservices = false;
    private DiaryModel diaryModel;
    private ObraModel obraModel;
    private Preferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activityv2);
        initView();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        navigationView.setNavigationItemSelectedListener(this);

        Intent intent = getIntent();
        keyobra = intent.getStringExtra("keyobra");
        if (keyobra != null) {
            obraModel = new ObraModel();
            obraModel.getObra(this, keyobra);
        }
    }


    @Override
    public void callbackObra(Obra obra) {
        if (obra == null) {
            return;
        }
        this.obra = obra;
        if (viewId != null && obra.getProgress() != null) {
            viewId.setProgress(Float.parseFloat(String.valueOf(obra.getProgress())), true);
        }
        toolbar.setTitle(obra.getObra());
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        double entry = (double) Math.round(obra.getEntry()) / 100;
        Double pay = Math.round(obra.getValor()) * entry / 100;
        toolbar.setSubtitle("Pago " + obra.getEntry() + "%: " + formatter.format(pay));

        /*setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> activity.finish());*/
        /*Drawable drawable = ContextCompat.getDrawable(this, R.drawable.ic_menu_black_24dp);
        toolbar.setNavigationIcon(drawable);*/
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);


        /*Preferences preferences = new Preferences(activity);
        preferences.setNotification(keyobra);*/
        retrieveDiary();

    }

    private void retrieveDiary() {
        diaryModel = new DiaryModel(obra);
        diaryModel.getDiaries(this);
    }

    @Override
    public void callbackDiaries(ArrayList<Diary> diarylist) {
        if (diarylist.isEmpty()) {
            return;
        }
        DiariesAdapter adapter = new DiariesAdapter(activity, diarylist);
        adapter.notifyDataSetChanged();
        GridLayoutManager llm = new GridLayoutManager(activity, 1, RecyclerView.VERTICAL, false);
        diaryrecycler.setAdapter(adapter);
        diaryrecycler.setLayoutManager(llm);
        diaryrecycler.setVisibility(View.VISIBLE);
    }

    private void retrieveEndService() {
        EndserviceListAdapter adapter = new EndserviceListAdapter(activity, obra.getEndservices());
        diaryrecycler.setAdapter(adapter);
        GridLayoutManager llm = new GridLayoutManager(activity, 1, RecyclerView.VERTICAL, false);
        diaryrecycler.setLayoutManager(llm);

    }

    @Override
    protected void onResume() {
        //GetObra();
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    if (!isdiaries) {
                        //LoadDiary();
                        isendedservices = false;
                        retrieveDiary();
                    }
                } else {
                    if (!isendedservices) {
                        isdiaries = false;
                        retrieveEndService();
                        //LoadEndservices();
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        super.onResume();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                Preferences preferences = new Preferences(activity);
                //preferences.setNotification(obra.getKey());

            } else {
                Toast.makeText(activity, "Erro ao realizar login...", Toast.LENGTH_LONG).show();
            }

        }*/
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id) {
            case R.id.contrato: {
                Log.i("LOG(R.id)", "contrato");
                if (obra.getContracturl() != null) {
                    Alerts.dialogPdf(this, obra.getContracturl());
                } else {
                    DocMessage("O contrato ainda nao foi enviado ao sistema");
                }
                break;
            }
            case R.id.progress:
                DatabaseReference contacts = FirebaseDatabase.getInstance().getReference().child("obras").child(keyobra).child("contacts");
                ArrayList<Contact> contactlist = new ArrayList<>();
                contacts.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChildren()) {
                            for (DataSnapshot d : dataSnapshot.getChildren()) {
                                Contact co = d.getValue(Contact.class);
                                contactlist.add(co);
                            }
                            Alerts alerts = new Alerts(activity);
                            alerts.dialogContact(contactlist);
                        } else {
                            DocMessage("Os contatos dos engenheiros ainda não foram informados");
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                break;
            case R.id.cronogram: {
                if (obra.getCronogram() != null) {
                    Alerts.dialogPdf(this,obra.getCronogram());
                } else {
                    DocMessage("O cronograma ainda não foi enviado ao sistema");
                }
                break;
            }
            case R.id.art: {
                if (obra.getArt() != null) {
                    Alerts.dialogPdf(this,obra.getArt());
                } else {
                    DocMessage("ART ainda não foi enviado ao sistema");
                }
                break;
            }
            case R.id.artexecução: {
                if (obra.getArtexecution() != null) {
                    Alerts.dialogPdf(this,obra.getArtexecution());
                } else {
                    DocMessage("ART de execução ainda não foi enviado ao sistema");
                }
                break;
            }
            case R.id.serviceoder: {
                if (obra.getServiceorder() != null) {
                    Alerts.dialogPdf(this,obra.getServiceorder());
                } else {
                    DocMessage("A ordem de serviço ainda não foi enviada ao sistema");
                }
                break;
            }
            default:
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signOut();
                Preferences preferences = new Preferences(this);
                preferences.destroy();
                Intent intent = new Intent(getApplicationContext(), Splash.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void DocMessage(String message) {
        Snacky.builder().setActivity(this).info().setText(message).show();
    }

    private void initView() {
        diaryrecycler = findViewById(R.id.diaryrecycler);
        viewId = findViewById(R.id.view_id);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> activity.finish());
        empty = findViewById(R.id.empty);
        tabs = findViewById(R.id.tabs);
    }

}
