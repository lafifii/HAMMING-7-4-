package com.algebralineal.tpalgebralineal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToEncodeActivity(View view) {
        Intent intent = new Intent(MainActivity.this, EncodeActivity.class);
        startActivity(intent);
    }

    public void goToInformationActivity(View view)
    {
        Intent intent = new Intent(MainActivity.this, InformationActivity.class);
        startActivity(intent);
    }

    public void goToCreditsActivity(View view)
    {
        Intent intent = new Intent(MainActivity.this, CreditsActivity.class);
        startActivity(intent);
    }
}
