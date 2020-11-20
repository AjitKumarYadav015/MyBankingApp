package com.megamindcore.mybanking.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.megamindcore.mybanking.Add_User;
import com.megamindcore.mybanking.Clan;
import com.megamindcore.mybanking.Clan_adapter;
import com.megamindcore.mybanking.Log_Database;
import com.megamindcore.mybanking.history_adapter;
import com.megamindcore.mybanking.Database;
import com.megamindcore.mybanking.R;
import com.megamindcore.mybanking.ui.dashboard.DashboardFragment;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment implements history_adapter.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private history_adapter mAdapter;

    private ProgressBar mProgressCircle;

    private List<Clan> mClan;
    
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        Button Add = root.findViewById(R.id.Add);
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), Add_User.class);
                startActivity(intent);
            }
        });

        Database global_top = new Database(getActivity());

        final String Global_Top = "basic_users_list";
        boolean databased_check = doesDatabaseExist(getActivity(), "users_data.db");

        boolean table_global_check = global_top.isTableExists(Global_Top, true);

        if (databased_check == true) {
            if (table_global_check == true) {

                SharedPreferences sp = getActivity().getSharedPreferences("Setting", MODE_PRIVATE);
                Long selected_acount= sp.getLong("selected_account",123456);

                Cursor globals_Tops = global_top.Get_basic_users_list(0,20);
                while (globals_Tops.moveToNext()) {
                 if ((globals_Tops.getString(0).trim().equals((String.valueOf(selected_acount)).trim()))) {

                        String Account_no = globals_Tops.getString(0).trim();
                        String Name = globals_Tops.getString(1).trim();
                        String Balance = globals_Tops.getString(2).trim();
                        String Email = globals_Tops.getString(3).trim();


                        if (Account_no == null) {
                            Account_no = "!!";
                        }
                        if (Name == null) {
                            Name = "!!";
                        }
                        if (Balance == null) {
                            Balance = "00";
                        }
                        if (Email == null) {
                            Email = "!!";
                        }

                        TextView Account_h = root.findViewById(R.id.Account_h);
                        TextView Balance_h = root.findViewById(R.id.balance_h);
                        TextView Name_h = root.findViewById(R.id.name_h);
                        TextView Email_h = root.findViewById(R.id.email_h);
                        Account_h.setText(Account_no);
                        Name_h.setText(Name);
                        Balance_h.setText(Balance);
                        Email_h.setText(Email);
                    }
                }

            }else{
                Toast.makeText(getActivity(), " Table Top List Not Exist !", Toast.LENGTH_SHORT).show();}
        }else {Toast.makeText(getActivity(), " Table Top List Not Exist !", Toast.LENGTH_SHORT).show();}





        mRecyclerView = root.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayout = new LinearLayoutManager(getActivity());
        linearLayout.setReverseLayout(false);
        linearLayout.isSmoothScrollbarEnabled();
        mRecyclerView.setLayoutManager(linearLayout);




        mProgressCircle = root.findViewById(R.id.progress_circle);

        mClan = new ArrayList<>();


        mAdapter = new history_adapter(getActivity(), mClan);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(HomeFragment.this);


        mClan.clear();

        loadData(0,20);

        return root;
    }

    public void onItemClick(int position) {
        Toast.makeText(getActivity(), "Normal click at position: " + position, Toast.LENGTH_SHORT).show();
    }




    public void onDeleteClick(int position) {

        Toast.makeText(getActivity(), "Available Soon: " + position, Toast.LENGTH_SHORT).show();


    }



    private void loadData(int start,int end) {

        mProgressCircle.setVisibility(RecyclerView.GONE);

        Log_Database global_top = new Log_Database(getActivity());

        final String Global_Top = "log_users_list";
        boolean databased_check = doesDatabaseExist(getActivity(), "log_transfer_data.db");

        boolean table_global_check = global_top.isTableExists(Global_Top, true);

        if (databased_check == true) {
            if (table_global_check == true) {

                SharedPreferences sp = getActivity().getSharedPreferences("Setting", MODE_PRIVATE);
                long selected_acount= sp.getLong("selected_account",0);

                Cursor globals_Tops = global_top.Get_log_users(start,end);
                while (globals_Tops.moveToNext()) {

                    if (String.valueOf(selected_acount).trim()
                            .equals(globals_Tops.getString(1).trim().substring(1,globals_Tops.getString(1).trim().length()-1)) ||
                            String.valueOf(selected_acount).trim()
                                    .equals(globals_Tops.getString(3).trim().substring(1,globals_Tops.getString(3).trim().length()-1))) {

                        String Sender = String.valueOf(globals_Tops.getString(2)).trim().substring(1,  String.valueOf(globals_Tops.getString(2)).trim().length()-1);
                        String Receiver = String.valueOf(globals_Tops.getString(4)).trim().substring(1,  String.valueOf(globals_Tops.getString(4)).trim().length()-1);
                        String Balance = String.valueOf(globals_Tops.getString(5)).trim().substring(1,  String.valueOf(globals_Tops.getString(5)).trim().length()-1);


                    if (Sender == null) {
                            Sender = "!!";
                        }
                        if (Sender == null) {
                            Sender = "!!";
                        }
                        if (Balance == null) {
                            Balance = "!!";
                        }


                        final Clan clan = new Clan();
                        clan.setKey(Receiver);
                        clan.setm(Sender);
                        clan.setn(Balance);


                        mClan.addAll(Collections.singleton(clan));
                        mAdapter.notifyDataSetChanged();

                    }

                    mProgressCircle.setVisibility(RecyclerView.GONE);
                }
            }else{Toast.makeText(getActivity(), " Table Top List Not Exist !", Toast.LENGTH_SHORT).show();}
        }else {Toast.makeText(getActivity(), " Table Top List Not Exist !", Toast.LENGTH_SHORT).show();}


    }

    private static boolean doesDatabaseExist(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }





}