package com.megamindcore.mybanking.ui.notifications;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.megamindcore.mybanking.Add_User;
import com.megamindcore.mybanking.Database;
import com.megamindcore.mybanking.Log_Database;
import com.megamindcore.mybanking.MainActivity;
import com.megamindcore.mybanking.R;
import com.megamindcore.mybanking.Transfer_database;

import java.io.File;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static android.content.Context.MODE_PRIVATE;

public class NotificationsFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        EditText Account_h = root.findViewById(R.id.account_t);
        EditText Balance_h = root.findViewById(R.id.amount_t);

        Button Add = root.findViewById(R.id.Transfer);

        final String[] Sender_Account_no = {"Sender_Account_no"};
        final String[] Sender_Name = {"Sender_Name"};
        final String[] Receiver_Account_no = {"Receiver_Account_no"};
        final String[] Receiver_Name = {"Receiver_Name"};
        final String[] Balance_log = {"Balance"};

        final int[] S = {0};
        final int[] user_me = {0};
        final int[] lock_me = { 0 };
        final int[] user_other = { 0 };
        final int[] lock_other = { 0 };

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Account_h!=null&&Balance_h!=null){

                Transfer_database global_top = new Transfer_database(getActivity());

                final String Global_Top = "transfer_users_list";
                boolean databased_check = doesDatabaseExist(getActivity(), "users_data.db");

                boolean table_global_check = global_top.isTableExists(Global_Top, true);

                if (databased_check == true) {
                    if (table_global_check == true) {


                        SharedPreferences sp = getActivity().getSharedPreferences("Setting", MODE_PRIVATE);
                        int selected_acount= (int) sp.getLong("selected_account",0);
                        Sender_Account_no[0] = String.valueOf(selected_acount).trim();
                        Cursor globals_Tops = global_top.Get_transfer_users_list(0,20);
                        while (globals_Tops.moveToNext()) {

                            if (globals_Tops.getString(0).trim() .equals( String.valueOf(selected_acount).trim())) {
                                user_me[0] =1;
                                String Account_no = globals_Tops.getString(0).trim();
                                String Read_Lock = globals_Tops.getString(1).trim();
                                String Write_Lock = globals_Tops.getString(2).trim();
                                String TimeStamp = globals_Tops.getString(3).trim();

                                if (Account_no == null) {
                                    Account_no = "!!";
                                }
                                if (Read_Lock == null) {
                                    Read_Lock = "0";
                                }
                                if (Write_Lock == null) {
                                    Write_Lock = "0";
                                }
                                if (TimeStamp == null) {
                                    TimeStamp = "00000000";
                                }


                                long timer = (long) (TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));

                                long deff= ( timer- (Integer.valueOf((TimeStamp.trim())) ));

                                if( ( (Integer.valueOf(Read_Lock.trim())==0) && (Integer.valueOf(Write_Lock.trim())==0) )|| deff>60 ){
                                  lock_me[0] =0;
                              }else{
                                  lock_me[0] =1;
                              }

                            }


                            if (globals_Tops.getString(0).trim().equals(String.valueOf(Account_h.getText()).trim())) {
                                user_other[0] =1;
                                String Account_no = globals_Tops.getString(0).trim();
                                String Read_Lock = globals_Tops.getString(1).trim();
                                String Write_Lock = globals_Tops.getString(2).trim();
                                String TimeStamp = globals_Tops.getString(3).trim();

                                if (Account_no == null) {
                                    Account_no = "!!";
                                }
                                if (Read_Lock == null) {
                                    Read_Lock = "0";
                                }
                                if (Write_Lock == null) {
                                    Write_Lock = "0";
                                }
                                if (TimeStamp == null) {
                                    TimeStamp = "00000000";
                                }
                                long timer = (long) (TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
                                Log.e("new ", "ok-"+Integer.valueOf((String.valueOf(TimeStamp.trim()).trim())) );
                               // Toast.makeText(getActivity(), " "+TimeStamp+"", Toast.LENGTH_SHORT).show();

                                long deff= ( timer- (int)(Integer.valueOf((String.valueOf(TimeStamp.trim()).trim())) ));
                                if( ( (Integer.valueOf(Read_Lock.trim())==0) && (Integer.valueOf(Write_Lock.trim())==0) )|| deff>60 ){
                                    lock_other[0] =0;
                                }else{
                                    lock_other[0] =1;
                                }
                            }

                        }

                    }else{
                        Toast.makeText(getActivity(), " Table Top List Not Exist !", Toast.LENGTH_SHORT).show();}
                }else {Toast.makeText(getActivity(), " Table Top List Not Exist !", Toast.LENGTH_SHORT).show();}


                    if(user_other[0] ==0){ Toast.makeText(getActivity(), "  Others Account number Wrong!", Toast.LENGTH_SHORT).show();}
                    if(user_me[0] ==0){ Toast.makeText(getActivity(), "  Your Account number wrong!", Toast.LENGTH_SHORT).show();}
                }



                if(user_me[0] ==1&& user_other[0] ==1){

                    if( lock_other[0] ==0 && lock_me[0] ==0){

                        Database global_top = new Database(getActivity());

                        final String Global_Top = "basic_users_list";
                        boolean databased_check = doesDatabaseExist(getActivity(), "users_data.db");

                        boolean table_global_check = global_top.isTableExists(Global_Top, true);

                        if (databased_check == true) {
                            if (table_global_check == true) {

                                SharedPreferences sp = getActivity().getSharedPreferences("Setting", MODE_PRIVATE);
                                long selected_acount= sp.getLong("selected_account",0);

                                Cursor globals_Tops = global_top.Get_basic_users_list(0,20);
                                while (globals_Tops.moveToNext()) {

                                    if (globals_Tops.getString(0).trim() .equals( String.valueOf(selected_acount).trim())) {
                                        String Account_no = globals_Tops.getString(0).trim();
                                        String Name = globals_Tops.getString(1).trim();
                                        String Balance = globals_Tops.getString(2).trim();
                                        String Email = globals_Tops.getString(3).trim();

                                        if (Account_no == null) {
                                            Account_no = "00";
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

                                        Sender_Name[0] = String.valueOf(Name).trim();

                                        long timer = (long) (TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));

                                        Transfer_database myDbs = new Transfer_database(getActivity());
                                        boolean isInserteds = (boolean) myDbs.update_transfer_data(Account_no.trim(),"1","1",String.valueOf(timer));

                                        if(isInserteds == true){
                                            Toast.makeText(getActivity(), "Your Account Locked Successfully!", Toast.LENGTH_SHORT).show();

                                        }else{
                                            boolean IsUpdate = (boolean) myDbs.update_transfer_data(Account_no.trim(),"1","1",String.valueOf(timer));

                                            if(IsUpdate == true){
                                                Toast.makeText(getActivity(), "Your Account Locked Successfully!", Toast.LENGTH_SHORT).show();

                                            }else{
                                            Toast.makeText(getActivity(), "Your Account Lock failed!", Toast.LENGTH_SHORT).show();
                                        }
                                        }
                                        int Balances = Integer.valueOf(String.valueOf(Balance_h.getText().toString().trim()));
                                        int new_balance= (int) (Integer.valueOf(Balance)-Balances);

                                        Database myDb = new Database(getActivity());
                                        boolean isInserted = (boolean) myDb.update_user_data(Account_no.toString(),Name.toString(),String.valueOf(new_balance),Email.toString());

                                        if(isInserted){
                                            Toast.makeText(getActivity(), "Users data inserted Successfully!", Toast.LENGTH_SHORT).show();
                                        }else{

                                            Toast.makeText(getActivity(), "Users data inserted failed!", Toast.LENGTH_SHORT).show();
                                        }


                                        timer = (long) (TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
                                        isInserteds = (boolean) myDbs.update_transfer_data(Account_no.trim(),"0","0",String.valueOf(timer));

                                        if(isInserteds == true){
                                            Toast.makeText(getActivity(), "Your Account Locked Successfully!", Toast.LENGTH_SHORT).show();

                                        }else {
                                            Toast.makeText(getActivity(), "Your Account Locked failed!", Toast.LENGTH_SHORT).show();
                                        }
                                    }


                                    if (globals_Tops.getString(0).trim() .equals( Account_h.getText().toString().trim()) ){
                                        String Account_no = globals_Tops.getString(0).trim();
                                        String Name = globals_Tops.getString(1).trim();
                                        String Balance = globals_Tops.getString(2).trim();
                                        String Email = globals_Tops.getString(3).trim();




                                        if (Account_no == null) {
                                            Account_no = "00";
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


                                        Receiver_Account_no[0] =String.valueOf(Account_no).trim();
                                        Receiver_Name[0] =String.valueOf(Name).trim();


                                        int timer = (int) (TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));

                                        Transfer_database myDbs = new Transfer_database(getActivity());
                                        boolean isInserteds = (boolean) myDbs.insert_transfer_data(Account_no.trim(),"1","1",String.valueOf(timer));

                                        if(isInserteds == true){
                                            Toast.makeText(getActivity(), "Other Account Locked Successfully!", Toast.LENGTH_SHORT).show();

                                        }else{
                                            boolean IsUpdate = (boolean) myDbs.update_transfer_data(Account_no.trim(),"1","1",String.valueOf(timer));

                                            if(IsUpdate == true){
                                                Toast.makeText(getActivity(), "Other Account Locked Successfully!", Toast.LENGTH_SHORT).show();

                                            }else{
                                                Toast.makeText(getActivity(), "Other Account Lock failed!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        Integer Accounts = Integer.valueOf((String.valueOf(Account_h.getText().toString().trim())));
                                        Integer Balances = Integer.valueOf((String.valueOf(Balance_h.getText()).toString().trim()));

                                        Balance_log[0] =String.valueOf(Balances).trim();

                                        Integer new_balance= Integer.valueOf(Balance)+Balances;

                                        Database myDb = new Database(getActivity());
                                        boolean isInserted = (boolean) myDb.update_user_data(Account_no.toString(),Name.toString(),String.valueOf(new_balance),Email.toString());

                                        if(isInserted == true){
                                            Toast.makeText(getActivity(), "Users data inserted Successfully!", Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(getActivity(), "Users data inserted failed!", Toast.LENGTH_SHORT).show();
                                        }


                                        timer = (int) (TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
                                        isInserteds = (boolean) myDbs.update_transfer_data(Account_no.trim(),"0","0",String.valueOf(timer));
                                        if(isInserteds == true){
                                            Toast.makeText(getActivity(), "Other Account Locked Successfully!", Toast.LENGTH_SHORT).show();
                                            S[0] =1;
                                        }else {
                                            Toast.makeText(getActivity(), "Other Account Locked failed!", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                }

                            }else{
                                Toast.makeText(getActivity(), " Table Top List Not Exist !", Toast.LENGTH_SHORT).show();}
                        }else {Toast.makeText(getActivity(), " Table Top List Not Exist !", Toast.LENGTH_SHORT).show();}


                    }else{
                        Toast.makeText(getActivity(), " The User has been lacked please wait for few seconds! !", Toast.LENGTH_SHORT).show();
                    }
                }

                if(S[0] ==1){
                    long timer = (long) (TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));

                    Log_Database myDbs = new Log_Database(getActivity());
                    boolean isInserteds = (boolean) myDbs.insert_log_data(String.valueOf(timer).trim(), Arrays.toString(Sender_Account_no), Arrays.toString(Sender_Name), Arrays.toString(Receiver_Account_no), Arrays.toString(Receiver_Name), Arrays.toString(Balance_log));

                    if(isInserteds == true){
                        Toast.makeText(getActivity(), "Record saved Successfully!", Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(getActivity(), "Record saved failed!", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(getActivity(), " Transaction successfull! ", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getActivity(), " Transaction failed! ", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }

            }

        });


        return root;
    }
    private static boolean doesDatabaseExist(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }
}