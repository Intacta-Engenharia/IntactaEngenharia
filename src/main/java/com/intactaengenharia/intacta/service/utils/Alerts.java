package com.intactaengenharia.intacta.service.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.intactaengenharia.intacta.view.adapter.ContactAdapter;
import com.intactaengenharia.intacta.view.adapter.ViewPagerAdapter;
import com.intactaengenharia.intacta.service.model.Contact;
import com.intactaengenharia.intacta.R;

import java.util.List;

public class Alerts {
    public static final int RC_SIGN_IN = 123;

    private Context context;

    public Alerts(Context context) {
        this.context = context;
    }


    public void dialogContact(List<Contact> contacts) {
        Dialog contactdialog = new Dialog(context, R.style.Dialog_No_Border);
        contactdialog.setContentView(R.layout.contact_dialog);
        RecyclerView contactlist = contactdialog.findViewById(R.id.rv_contact);
        ContactAdapter contactAdapter = new ContactAdapter(context, contacts);
        contactlist.setAdapter(contactAdapter);
        GridLayoutManager llm = new GridLayoutManager(context, 1, RecyclerView.VERTICAL, false);
        contactlist.setLayoutManager(llm);
        contactdialog.show();
        contactdialog.setCanceledOnTouchOutside(true);

    }

    public void dialogPhoto(List<String> images, int position) {
        Dialog imagedialog = new Dialog(context, android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        imagedialog.setContentView(R.layout.fullscreen_dialog);
        TabLayout tabs;
        ViewPager viewpager;
        tabs = imagedialog.findViewById(R.id.tabs);
        viewpager = imagedialog.findViewById(R.id.viewpager);
        tabs.setupWithViewPager(viewpager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(images);
        viewpager.setAdapter(adapter);
        viewpager.setCurrentItem(position);
        imagedialog.show();
    }

    static public void dialogPdf(Context context, String uri) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(uri));
        context.startActivity(intent);
    }


//    public void login(Preferences preferences) {
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if (user == null) {
//          Snackbar snackbar =  Snacky.builder()
//                    .setActivity(activity)
//                    .setText("Faça login para receber notificações")
//                    .setDuration(Snacky.LENGTH_INDEFINITE)
//                    .setBackgroundColor(Color.WHITE)
//                    .setActionTextColor(Color.BLUE)
//                    .setTextColor(Color.DKGRAY)
//                    .setIcon(R.drawable.ic_notifications_none_black_24dp).build();
//          snackbar.setAction("Ok", v -> {
//                buildlogin();
//                snackbar.dismiss();
//          });
//          snackbar.show();
//
//
//
//        }
//    }
//
//    private void buildlogin() {
//        List<AuthUI.IdpConfig> providers = Collections.singletonList(
//                new AuthUI.IdpConfig.GoogleBuilder().build());
//        activity.startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
//                .setLogo(R.mipmap.ic_launcher)
//                .setAvailableProviders(providers)
//                .setTheme(R.style.AppTheme)
//                .build(),RC_SIGN_IN);
//    }


}
