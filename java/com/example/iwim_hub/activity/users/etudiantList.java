package com.example.iwim_hub.activity.users;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.iwim_hub.R;

public class etudiantList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etudiant_list);
    }

    public static class etudiant extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_etudiant);
        }
    }

    public static class emploiDetail extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_emploi_detail);
        }
    }
}
