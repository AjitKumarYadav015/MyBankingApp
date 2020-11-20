package com.megamindcore.mybanking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Add_User  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        EditText Account_no = findViewById(R.id.edit_account);
        EditText Name=findViewById(R.id.edit_name);
        EditText Balance=findViewById(R.id.edit_init_blanace);
        EditText Email=findViewById(R.id.edit_email);
        Button register = findViewById(R.id.register_btn);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Account_no.getText().length()>3){
                    if(Name.getText().length()>3){
                        if(Balance.getText().length()>=1){
                            if(Email.getText().length()>5){

                                Database myDb = new Database(getApplicationContext());
                                boolean isInserted = (boolean) myDb.insert_user_data(Account_no.getText().toString(),Name.getText().toString(),Balance.getText().toString(),Email.getText().toString());

                                Transfer_database myDbs = new Transfer_database(getApplicationContext());
                                boolean isInserteds = (boolean) myDbs.insert_transfer_data(Account_no.getText().toString(),"0","0","000000000");

                                if(isInserted == true){
                                    Toast.makeText(getApplicationContext(), "Users data inserted Successfully!", Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(Add_User.this, MainActivity.class);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(getApplicationContext(), "Users data inserted failed!", Toast.LENGTH_SHORT).show();
                                }


                                }else{
                                Toast.makeText(getApplicationContext(), " Please Fill Email  Properly! ", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), " Please Fill Balance  Properly! ", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), " Please Fill Name Properly! ", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), " Please Fill Account NO. Properly! ", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

}