

package com.example.food_donation_app;

        import android.app.Dialog;
        import android.content.Intent;
        import android.net.Uri;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;

        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.auth.AuthResult;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;

        import static com.example.food_donation_app.R.id;


public class MainActivity extends AppCompatActivity {
    private EditText mEmail, mPassword;
    private Button mLoginBtn;
    private TextView mLoginText;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListner;
    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Dialog d=new Dialog(this);
        // mEmail = (EditText)findViewById(R.id.Email);
        // @InjectView(R.id.Email)EditText mEmail;
        mLoginBtn =(Button) findViewById(R.id.loginBtn);
        mLoginText = findViewById(R.id.loginText);
        //progressBar= findViewById(R.id.createText);
        // mPassword =(EditText)findViewById(R.id.password);
        //mPhone=findViewById(R.id.phone);
        mFirebaseAuth = FirebaseAuth.getInstance();
        //setContentView(R.layout.activity_login);
        mEmail=(EditText)findViewById(R.id.Email) ;
        mPassword=(EditText)findViewById(R.id.password);

        //when user is already logged in,so there is no need to again login,so it just check this,and helps to direct jump to the main page instead of again login or signup
        mAuthStateListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if (mFirebaseUser != null) {
                    email=mEmail.getText().toString().trim();
                    password = mPassword.getText().toString();
                    Toast.makeText(MainActivity.this, "You are logged  in ", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(i);
                }
                else {
                    Toast.makeText(MainActivity.this, "Please Login", Toast.LENGTH_SHORT).show();
                }
            }
        };
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
               /*if (email.isEmpty()) {
                    mEmail.setError("Email is Required.");
                    mEmail.requestFocus();
                }
                else if (password.isEmpty()) {
                    mPassword.setError("Password is Required.");
                    mPassword.requestFocus();
                }
                else if (email.isEmpty() && password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Fields are empty", Toast.LENGTH_SHORT).show();
                }
                else if (!(email.isEmpty() && password.isEmpty())) {*/
                    mFirebaseAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful()) {
                                       /* Log.w("abcd", "signInWithEmail:failure", task.getException());
                                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                        updateUI(null);
                                        mEmail.setText("");
                                        mPassword.setText("");*/
                                        Log.w("tag", "signInWithEmail:failure", task.getException());
                                        Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                        updateUI(null);

                                        Toast.makeText(MainActivity.this, "Login Error,Please Login Again!", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Log.d("tag", "signInWithEmail:success");
                                        FirebaseUser user = mFirebaseAuth.getCurrentUser();
                                        updateUI(user);
                                        /*Intent i1 = new Intent(MainActivity.this, HomeActivity.class);

                                        startActivity(i1);

                                        // Sign in success, update UI with the signed-in user's information
                       Log.d("TAG", "signInWithEmail:success");
                        FirebaseUser user = mFirebaseAuth.getCurrentUser();
                        updateUI(user);*/
                                    }
                                }
                            });
              /* } else {
                    Toast.makeText(LoginActivity.this, "Error Occured!", Toast.LENGTH_SHORT).show();
                }*/
                }
                catch(Exception e){
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }

        });

        mLoginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i3 = new Intent(MainActivity.this, SignUp.class);
                startActivity(i3);
            }
        });

    }
    private void updateUI(FirebaseUser user) {
        if(user!=null){
            Toast.makeText(MainActivity.this,"login successfully!!",Toast.LENGTH_LONG).show();
            startActivity(new Intent(MainActivity.this,HomeActivity.class));
        }
        else{
            Toast.makeText(MainActivity.this,"login unsuccessful",Toast.LENGTH_LONG).show();
        }
    }
    @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        updateUI(user);
        // mFirebaseAuth.addAuthStateListener(mAuthStateListner);
    }

    /*private void updateUI(FirebaseUser user) {
        if(user!=null){
            Toast.makeText(this,"login successfully!!",Toast.LENGTH_LONG).show();
            startActivity(new Intent(this,HomeActivity.class));
        }
        else{
            Toast.makeText(this,"login unsuccessful",Toast.LENGTH_LONG).show();
        }
    }*/
}

