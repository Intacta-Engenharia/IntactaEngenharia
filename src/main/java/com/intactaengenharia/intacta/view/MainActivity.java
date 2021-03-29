package com.intactaengenharia.intacta.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.intactaengenharia.intacta.R;
import com.intactaengenharia.intacta.view.adapter.DiaryAdapter;
import com.intactaengenharia.intacta.view.adapter.DocumentAdapter;
import com.intactaengenharia.intacta.view.adapter.EndServiceAdapter;
import com.intactaengenharia.intacta.service.model.Diary;
import com.intactaengenharia.intacta.service.model.Document;
import com.intactaengenharia.intacta.service.model.Obra;
import com.intactaengenharia.intacta.service.utils.Alerts;
import com.intactaengenharia.intacta.viewmodel.ContactViewModel;
import com.intactaengenharia.intacta.viewmodel.DiaryViewModel;
import com.intactaengenharia.intacta.viewmodel.UserViewModel;
import com.intactaengenharia.intacta.viewmodel.ViewModelFactory;
import com.intactaengenharia.intacta.viewmodel.DocumentViewModel;
import com.intactaengenharia.intacta.viewmodel.ObraViewModel;
import com.ramijemli.percentagechartview.PercentageChartView;

import java.text.NumberFormat;
import java.util.List;

import de.mateware.snacky.Snacky;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    //private final Activity activity = this;
    private Obra mObra;
    private String mKeyObra;
    private RecyclerView recycler;
    private PercentageChartView percentageChartView;
    private Toolbar toolbar;
    private TabLayout tabs;
    private TextView empty;
    private ProgressBar pgMain;

    private ObraViewModel mObraViewModel;
    private DocumentViewModel mDocumentViewModel;
    private DiaryViewModel mDiaryViewModel;
    private UserViewModel mUserViewModel;
    private ContactViewModel mContactViewModel;

    private DiaryAdapter mDiaryAdapter;
    private DocumentAdapter mDocumentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity);
        initView();

        Intent intent = getIntent();
        mKeyObra = intent.getStringExtra("keyobra");
        mObraViewModel = new ViewModelProvider(this).get(ObraViewModel.class);
        mDiaryViewModel = new ViewModelProvider(this, new ViewModelFactory(this.getApplication(), mKeyObra)).get(DiaryViewModel.class);
        mDocumentViewModel = new ViewModelProvider(this, new ViewModelFactory(this.getApplication(), mKeyObra)).get(DocumentViewModel.class);
        mContactViewModel = new ViewModelProvider(this, new ViewModelFactory(this.getApplication(), mKeyObra)).get(ContactViewModel.class);
        mUserViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        setupRvDiaries();
        setupRvDocuments();
        //retrieveEndService();

        observer();

        load(true);
        mObraViewModel.get(mKeyObra);
        mDiaryViewModel.onLoad();
    }

    //Carregar a lista com todos
    private void load(Boolean isLoading) {
        if (isLoading) pgMain.setVisibility(View.VISIBLE);
        else pgMain.setVisibility(View.GONE);
    }

    private void observer() {
        mObraViewModel.getObra().observe(this, new Observer<Obra>() {
            @Override
            public void onChanged(Obra obra) {
                if (obra == null) {
                    return;
                }
                mObra = obra;
                if (percentageChartView != null && obra.getProgress() != null) {
                    percentageChartView.setProgress(Float.parseFloat(String.valueOf(obra.getProgress())), true);
                }
                toolbar.setTitle(obra.getObra());
                NumberFormat formatter = NumberFormat.getCurrencyInstance();
                double entry = (double) Math.round(obra.getEntry()) / 100;
                Double pay = Math.round(obra.getValor()) * entry / 100;
                toolbar.setSubtitle("Pago " + obra.getEntry() + "% " + formatter.format(pay));
                toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            }
        });
        mDiaryViewModel.getDiaries().observe(this, new Observer<List<Diary>>() {
            @Override
            public void onChanged(List<Diary> diaries) {
                load(false);
                //diarylist = diaries;
                if (diaries == null || diaries.isEmpty()) {
                    recycler.setVisibility(View.GONE);
                    empty.setText("Não existe diários de obra no momento.");
                    empty.setVisibility(View.VISIBLE);
                    return;
                }
                empty.setVisibility(View.GONE);
                recycler.setVisibility(View.VISIBLE);
                mDiaryAdapter.notifyChanged(diaries);
                recycler.setAdapter(mDiaryAdapter);
            }
        });
        mDocumentViewModel.getDcouments().observe(this, new Observer<List<Document>>() {
            @Override
            public void onChanged(List<Document> documents) {
                load(false);
                //documentlist = documents;
                if (documents == null || documents.isEmpty()) {
                    recycler.setVisibility(View.GONE);
                    empty.setText("Nenhum documento da obra encontrado.");
                    empty.setVisibility(View.VISIBLE);
                    return;
                }
                empty.setVisibility(View.GONE);
                recycler.setVisibility(View.VISIBLE);
                mDocumentAdapter.notifyChanged(documents);
                recycler.setAdapter(mDocumentAdapter);
            }
        });
        mUserViewModel.getLogged().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (!aBoolean) {
                    Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        });
        mContactViewModel.getContacts().observe(this, contacts -> {
            if (!contacts.isEmpty()) {
                Alerts alerts = new Alerts(this);
                alerts.dialogContact(contacts);
            } else {
                DocMessage("Os contatos dos engenheiros ainda não foram informados");
            }
        });
    }

    //setup recyclers
    private void setupRvDiaries() {
        mDiaryAdapter = new DiaryAdapter();
        GridLayoutManager llm = new GridLayoutManager(this, 1, RecyclerView.VERTICAL, false);
        recycler.setLayoutManager(llm);
        //recycler.setAdapter(mDiaryAdapter);
    }

    private void retrieveEndService() {
        load(false);
        if (mObra.getEndservices() == null || mObra.getEndservices().isEmpty()) {
            recycler.setVisibility(View.GONE);
            empty.setText("Nenhum serviço finalizado encontrado.");
            empty.setVisibility(View.VISIBLE);
            return;
        }
        empty.setVisibility(View.GONE);
        recycler.setVisibility(View.VISIBLE);
        EndServiceAdapter adapter = new EndServiceAdapter(mObra.getEndservices());
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recycler.setLayoutManager(llm);
        recycler.setAdapter(adapter);
    }

    private void setupRvDocuments() {
        mDocumentAdapter = new DocumentAdapter();
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recycler.setLayoutManager(llm);
        //recycler.setAdapter(mDiaryAdapter);
    }

    @Override
    protected void onResume() {
        //GetObra();
        load(true);
        mObraViewModel.get(mKeyObra);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                recycler.setVisibility(View.VISIBLE);
                empty.setVisibility(View.GONE);

                if (tab.getPosition() == 0) {
                    load(true);
                    mDiaryViewModel.onLoad();
                    //setupRvDiaries(diarylist);
                }
                if (tab.getPosition() == 1) {
                    load(true);
                    retrieveEndService();
                    //LoadEndservices();
                }
                if (tab.getPosition() == 2) {
                    load(true);
                    mDocumentViewModel.onLoad();
                    //setupRvDocuments(documentlist);
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
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.contact) {
            mContactViewModel.onLoad();
//            DatabaseReference contacts = FirebaseDatabase.getInstance().getReference().child("obras").child(keyobra).child("contacts");
//            ArrayList<Contact> contactlist = new ArrayList<>();
//            contacts.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    if (dataSnapshot.hasChildren()) {
//                        for (DataSnapshot d : dataSnapshot.getChildren()) {
//                            Contact co = d.getValue(Contact.class);
//                            contactlist.add(co);
//                        }
//                        Alerts alerts = new Alerts(activity);
//                        alerts.dialogContact(contactlist);
//                    } else {
//                        DocMessage("Os contatos dos engenheiros ainda não foram informados");
//                    }
//
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });
        } else {
            mUserViewModel.doLogout();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void DocMessage(String message) {
        Snacky.builder().setActivity(this).info().setText(message).show();
    }

    private void initView() {
        recycler = findViewById(R.id.rv_diary);
        percentageChartView = findViewById(R.id.percentage_view);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //toolbar.setNavigationOnClickListener(v -> activity.finish());
        empty = findViewById(R.id.empty);
        tabs = findViewById(R.id.tabs);
        pgMain = findViewById(R.id.pg_main);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        navigationView.setNavigationItemSelectedListener(this);
    }

}
