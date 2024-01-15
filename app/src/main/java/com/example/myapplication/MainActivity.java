package com.example.myapplication;

import static com.example.myapplication.ManagerDB.addListToUser;
import static com.example.myapplication.ManagerDB.addUser;
import static com.example.myapplication.ManagerDB.hasUser;
import static com.example.myapplication.ManagerDB.readDatabase;


import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    SharedPreferences sp;
    static ManagerDB db = new ManagerDB();

    Dialog d,d2;
    EditText etUserName;
    EditText etEmail;
    EditText etPass;
    Button btnCustomLogin;
    Button btnDialogSignup;

    TextView tvsignup;
    TextView continuetext;
    Intent intent;
    Button fullscreenbtn;
    static User u;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        readDatabase();
        login = findViewById(R.id.login);
        login.setOnClickListener(this);

        fullscreenbtn = findViewById(R.id.fullScreenButton);
        continuetext = findViewById(R.id.leave);
        fullscreenbtn.setOnClickListener(this);
        sp=getSharedPreferences("details1",0);

        fullscreenbtn.setClickable(false);
        intent= new Intent(this, MainScreen.class);
        continuetext.setVisibility(View.GONE);

        tvsignup= findViewById(R.id.tvsignup);
        SpannableString spannableString = new SpannableString("Dont have an account?");
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {

                createSignupDialog();
            }
        };
        spannableString.setSpan(clickableSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        tvsignup.setText(spannableString);

        tvsignup.setMovementMethod(LinkMovementMethod.getInstance());


    }
    public void createSignupDialog()
    {


        d2= new Dialog(this);
        d2.setContentView(R.layout.dialog_signup);
        d2.setTitle("Sign up");
        d2.setCancelable(true);
        etEmail=(EditText)d2.findViewById(R.id.etEmail);
        etUserName=(EditText)d2.findViewById(R.id.etUserName);
        btnDialogSignup = d2.findViewById(R.id.btnDialogSignup);
        btnDialogSignup.setOnClickListener(this);
        etPass=(EditText)d2.findViewById(R.id.etPassword);
        d2.show();




    }

    public void createLoginDialog()
    {


        d= new Dialog(this);
        d.setContentView(R.layout.dialog);
        d.setTitle("Login");
        d.setCancelable(true);
        etUserName=(EditText)d.findViewById(R.id.etUserName);
        etPass=(EditText)d.findViewById(R.id.etPassword);
        btnCustomLogin=(Button)d.findViewById(R.id.btnDialogLogin);
        btnCustomLogin.setOnClickListener(this);
        d.show();



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    private boolean isValidEmail() {
        // Get the task name from the EditText
        String userName = etUserName.getText().toString().trim();

        // Perform validation
        return !userName.isEmpty();
    }

    @Override
    public void onClick(View v) {
        if(v==login)
        {
            createLoginDialog();
        }
        else if(isValidEmail() && v==btnCustomLogin) {

            if(!etUserName.getText().toString().matches("")&&!etPass.getText().toString().matches("")) {
                u = new User(etPass.getText().toString(),etUserName.getText().toString());
                if (hasUser(u)) //Checks if user info is correct.
                {
                    addListToUser(u);
                    Toast.makeText(this, "Login successful", Toast.LENGTH_LONG).show();
                    d.dismiss();
                    fullscreenbtn.setClickable(true);
                    login.setVisibility(View.GONE);
                    continuetext.setVisibility(View.VISIBLE);
                    tvsignup.setVisibility(View.GONE);
                    startActivity(new Intent(this, MainScreen.class));
                }
                else {
                    Toast.makeText(this, "Invalid login ", Toast.LENGTH_LONG).show();
                }
            }
            else {
                Toast.makeText(this, "Invalid email ", Toast.LENGTH_LONG).show();
            }

        }
        else if (v==btnDialogSignup)
        {
            if(!etEmail.getText().toString().matches("")&&!etPass.getText().toString().matches(""))
            {
                 u = new User(etPass.getText().toString(), etEmail.getText().toString());
                if(addUser(u)) //Checks if user info is correct.
                {
                    ManagerDB.curr = u;
                    startActivity(new Intent(this,MainScreen.class));
                }
            }

            else {
                Toast.makeText(this,"john",Toast.LENGTH_SHORT).show();
            }


        }
        else if (v==fullscreenbtn) {

            startActivityForResult(intent,0);

        }


    }
}