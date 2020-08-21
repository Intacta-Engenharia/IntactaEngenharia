package com.intactaengenharia.intacta.Adapter;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.intactaengenharia.intacta.Beans.Contact;
import com.intactaengenharia.intacta.R;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.MyViewHolder> {

     private Context mContext;
      ArrayList<Contact> data;

    public ContactListAdapter(Context mContext, ArrayList<Contact> data) {
         this.mContext = mContext;
        this.data = data;
     }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.custom_list,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
      holder.title.setText(data.get(position).getNome());
      holder.title.setOnClickListener(v -> {
          openWhatsApp(data.get(position).getTelefone());
      });

    }

    private void openWhatsApp(String number) {
        try {
            number = number.replace(" ", "").replace("+", "");

            Intent sendIntent = new Intent("android.intent.action.MAIN");
            sendIntent.setComponent(new ComponentName("com.whatsapp","com.whatsapp.Conversation"));
            sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(number)+"@s.whatsapp.net");
            mContext.startActivity(sendIntent);

        } catch(Exception e) {
            Log.e(TAG, "ERROR_OPEN_MESSANGER"+e.toString());
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title;





        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);


        }
    }
}
