

package com.example.food_donation_app;

        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.os.Bundle;
        import android.text.TextUtils;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.auth.AuthResult;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;

public class SignUp extends AppCompatActivity {
    private EditText mEmail,mPassword;
    private Button mResisterBtn;
    private TextView mLoginBtn;
    private FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mEmail=findViewById(R.id.Email);
        mPassword= findViewById(R.id.password);
        //mPhone=findViewById(R.id.phone);
        mResisterBtn=findViewById(R.id.registerBtn);
        mLoginBtn = findViewById(R.id.createText);
        //progressBar= findViewById(R.id.createText);
        fAuth= FirebaseAuth.getInstance();


    }

    public void registerClick(View view) {
        String email=mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();

        if(email.isEmpty()){
            mEmail.setError("Email is Required.");
            mEmail.requestFocus();
        }
        else if(password.isEmpty()){
            mPassword.setError("Password is Required.");
            mPassword.requestFocus();
        }
        else if(email.isEmpty() && password.isEmpty()){
            Toast.makeText(SignUp.this,"Fields are empty",Toast.LENGTH_SHORT).show();
        }
        else if(!(email.isEmpty() && password.isEmpty())){

            fAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override

                        public void onComplete(Task<AuthResult> task) {

                            if(!task.isSuccessful()){
                                //startActivity(new Intent(MainActivity.this,HomeActivity.class));
                                Log.w("tag","createUserWithEmail:failure", task.getException());
                                Toast.makeText(SignUp.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);
                                Toast.makeText(SignUp.this,"SignUp Unsuccessful! Please try again!",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Log.d("tag", "createUserWithEmail:success");
                                FirebaseUser user = fAuth.getCurrentUser();
                                updateUI(user);
                                startActivity(new Intent(SignUp.this,HomeActivity.class));
                            }
                        }

                        private void updateUI(FirebaseUser user) {
                            if(user!=null){
                                Toast.makeText(SignUp.this,"login successfully!!",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(SignUp.this,HomeActivity.class));
                            }
                            else{
                                Toast.makeText(SignUp.this,"login unsuccessful",Toast.LENGTH_LONG).show();
                            }
                        }
                        /*public void onStart() {
                            super.onStart();
                            // Check if user is signed in (non-null) and update UI accordingly.
                            FirebaseUser currentUser = fAuth.getCurrentUser();
                            updateUI(currentUser);
                        }*/
                    });
        }
        else
        {
            Toast.makeText(SignUp.this,"Error Occured!",Toast.LENGTH_SHORT).show();
        }
    }

    public void loginClick(View view) {
        Intent i=new Intent(SignUp.this,MainActivity.class);
        startActivity(i);
    }

}