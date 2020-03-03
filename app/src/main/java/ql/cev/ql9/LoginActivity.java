package ql.cev.ql9;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private EditText loginEmail, loginPass;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private Button loginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginBtn = findViewById(R.id.loginBtn);
        loginEmail = findViewById(R.id.login_email);
        loginPass = findViewById(R.id.login_password);
        TextView notUser = findViewById(R.id.signUpTxtView);
        TextView forgot = findViewById(R.id.Forgot);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        notUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginActivity.this);
                alertDialogBuilder.setTitle("Do yo want to reset your password");
                alertDialogBuilder
                        .setMessage("")
                        .setCancelable(false)
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    @SuppressLint("SetTextI18n")
                                    public void onClick(DialogInterface dialog, int id) {
                                        loginPass.setVisibility(View.INVISIBLE);
                                        loginBtn.setText("Reset");
                                        loginBtn.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                String s =loginEmail.getText().toString().trim();
                                                if (!TextUtils.isEmpty(s)) {
                                                    FirebaseAuth.getInstance().sendPasswordResetEmail(s).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @SuppressLint("SetTextI18n")
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()){
                                                                Toast toast = Toast.makeText(getApplicationContext(),"Check your email", Toast.LENGTH_LONG);
                                                                View view = toast.getView();
                                                                view.setBackgroundResource(R.drawable.nice_button_enabled);
                                                                toast.show();
                                                                loginPass.setVisibility(View.VISIBLE);
                                                                loginBtn.setText("LOGIN");
                                                            }
                                                            else {
                                                                Toast toast = Toast.makeText(getApplicationContext(),"email not sent-email not valid", Toast.LENGTH_LONG);
                                                                View view = toast.getView();
                                                                view.setBackgroundResource(R.drawable.nice_button_enabled);
                                                                toast.show();
                                                            }
                                                        }
                                                    });
                                                }
                                            }
                                        });
                                    }


                                })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getApplicationContext(),"Processing", Toast.LENGTH_SHORT);
                View v = toast.getView();
                v.setBackgroundResource(R.drawable.nice_button_enabled);
                toast.show();
                String email = loginEmail.getText().toString().trim();
                String password = loginPass.getText().toString().trim();
                if (!TextUtils.isEmpty(email)&& !TextUtils.isEmpty(password)){
                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                checkUserExistence();
                            }else {
                                Toast toast = Toast.makeText(getApplicationContext(),"User not found", Toast.LENGTH_SHORT);
                                View view = toast.getView();
                                view.setBackgroundResource(R.drawable.nice_button_enabled);
                                toast.show();
                            }
                        }
                    });
                }else {
                    Toast toas = Toast.makeText(getApplicationContext(),"Complete all fields", Toast.LENGTH_SHORT);
                    v = toast.getView();
                    v.setBackgroundResource(R.drawable.nice_button_enabled);
                    toas.show();
                }
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void checkUserExistence(){
        final String user_id = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(user_id)){
                    startActivity(new Intent(LoginActivity.this, CevCornerActivity.class));
                    finish();
                }else {
                    Toast toast = Toast.makeText(getApplicationContext(),"User not registered", Toast.LENGTH_LONG);
                    View view = toast.getView();
                    view.setBackgroundResource(R.drawable.nice_button_enabled);
                    toast.show();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }
}