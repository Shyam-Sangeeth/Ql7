package ql.cev.ql9;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Objects;
import static com.google.android.gms.internal.zzbfq.NULL;

public class RegisterActivity extends AppCompatActivity {
    private EditText emailField, usernameField, passwordField;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        TextView loginTxtView = findViewById(R.id.loginTxtView);
        Button registerBtn = findViewById(R.id.registerBtn);
        emailField = findViewById(R.id.emailField);
        usernameField = findViewById(R.id.usernameField);
        passwordField = findViewById(R.id.passwordField);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        loginTxtView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });
        registerBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = usernameField.getText().toString().trim();
                final String email = emailField.getText().toString().trim();
                final String password = passwordField.getText().toString().trim();
                int size=password.length();
                if (size<8){
                    Toast.makeText(RegisterActivity.this, "Password : Minimum 8 characters", Toast.LENGTH_LONG).show();
                }
                int Flag;
                String pattern="[a-z0-9]+@[a-z]+\\.+[a-z]+";
                if (email.matches(pattern))
                {
                    Flag=0;
                }
                else {
                    Flag=1;
                    Toast.makeText(RegisterActivity.this, "Invalid email", Toast.LENGTH_SHORT).show();
                }
                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(username)&&!TextUtils.isEmpty(password)&&size>=8&&(Flag==0)){
                    Toast.makeText(RegisterActivity.this, "LOADING...", Toast.LENGTH_LONG).show();
                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this, "User exists", Toast.LENGTH_LONG).show();
                                Intent regIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(regIntent);
                                finish();
                            }else {
                                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        String user_id = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                                        if (user_id.equals(NULL)){
                                            Toast.makeText(RegisterActivity.this, "Invalid email", Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            DatabaseReference current_user_db = mDatabase.child(user_id);
                                            current_user_db.child("Username").setValue(username);
                                            Toast.makeText(RegisterActivity.this, "Registeration Succesful", Toast.LENGTH_SHORT).show();
                                            Intent regIntent = new Intent(RegisterActivity.this, CevCornerActivity.class);
                                            regIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(regIntent);
                                            finish();
                                        }
                                    }
                                });
                            }
                        }
                    });
                }else {
                    Toast.makeText(RegisterActivity.this, "Complete all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}