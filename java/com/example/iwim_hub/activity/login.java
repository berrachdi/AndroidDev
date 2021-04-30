package com.example.iwim_hub.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iwim_hub.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import static android.content.ContentValues.TAG;

public class login extends Activity implements View.OnClickListener {


    LinearLayout layout1;
    LinearLayout layout2;
    LinearLayout layout3;
    LinearLayout layout4;
    LinearLayout layout5;
    LinearLayout layout6;
    LinearLayout layout7;
    LinearLayout layout8;

    Button singupBtt;
    Button singoutBtt;
    Button  loginBtt;
    RadioButton radioEtudiant;
    RadioButton radioProfesseur;

    TextView authstat;

    EditText login;
    EditText pass;



    private FirebaseAuth mAuth;
    FirebaseFirestore db ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

         db = FirebaseFirestore.getInstance();

        layout1=findViewById(R.id.state);
        layout2=findViewById(R.id.authInfo);
        layout3=findViewById(R.id.btt1);
        layout4=findViewById(R.id.actualites);
        layout5=findViewById(R.id.deconnexion);
        layout6=findViewById(R.id.profile);
        layout7=findViewById(R.id.zoneEtudiant);
        layout8=findViewById(R.id.zoneProfesseur);

        login=findViewById(R.id.loginText);
         pass=findViewById(R.id.passText);

        singupBtt=findViewById(R.id.singup);
        singupBtt.setOnClickListener(this);

        singoutBtt=findViewById(R.id.singout);
        singoutBtt.setOnClickListener(this);
        radioEtudiant=findViewById(R.id.radioEtudiant);
        radioProfesseur=findViewById(R.id.radioProfesseur);





        loginBtt=findViewById(R.id.login);
        loginBtt.setOnClickListener(this);

        authstat=findViewById(R.id.authstate);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {

        if (currentUser!=null) {
            authstat.setText("Utilisateur authentifié avec " + currentUser.getEmail());
            layout2.setVisibility(View.INVISIBLE);
            layout3.setVisibility(View.INVISIBLE);
            layout4.setVisibility(View.VISIBLE);
            layout5.setVisibility(View.VISIBLE);

            //recuperer l'utilisateur en fonction de son email
            DocumentReference docRef1 = db.collection("etudiant").document(login.getText().toString());
            DocumentReference docRef2 = db.collection("professeur").document(login.getText().toString());
            if (docRef1==null &&  docRef2==null){
                layout6.setVisibility(View.VISIBLE);
                layout1.setVisibility(View.INVISIBLE);
                layout2.setVisibility(View.INVISIBLE);
                layout3.setVisibility(View.INVISIBLE);
            }
            else{
                layout6.setVisibility(View.INVISIBLE);
                layout1.setVisibility(View.VISIBLE);
                layout2.setVisibility(View.VISIBLE);
                layout3.setVisibility(View.VISIBLE);
            }


        }
        else{
            authstat.setText("Veuillez vous authentifier" );
            layout2.setVisibility(View.VISIBLE);
            layout3.setVisibility(View.VISIBLE);
            layout4.setVisibility(View.INVISIBLE);
            layout5.setVisibility(View.INVISIBLE);

        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.login){
            signIn(login.getText().toString() ,pass.getText().toString());
            while (mAuth.getCurrentUser()==null)
                System.out.print(".");
            //this.recreate();
        }

        else if (v.getId()==R.id.singup){
            createAccount(login.getText().toString() ,pass.getText().toString());
            //this.recreate();
        }

        else if (v.getId()==R.id.singout){
            signOUt();
            while (mAuth.getCurrentUser()!=null)
                System.out.print(".");
            //this.recreate();
        }

    }

    public void onRadioButtonClicked(View view){

     boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioEtudiant:
                if (checked)
                    layout7.setVisibility(View.VISIBLE);
                layout8.setVisibility(View.INVISIBLE);
                    break;
            case R.id.radioProfesseur:
                if (checked)
                    layout7.setVisibility(View.INVISIBLE);
                    layout8.setVisibility(View.VISIBLE);
                    break;
        }
    }

    public void createAccount(String email,String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });

    }

    void signIn(String email,String password){

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    void signOUt(){
        mAuth.signOut();
        updateUI(null);
    }
}
