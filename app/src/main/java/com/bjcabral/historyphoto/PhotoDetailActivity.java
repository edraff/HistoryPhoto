package com.bjcabral.historyphoto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bjcabral.historyphoto.model.Photos;

public class PhotoDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);
        if(savedInstanceState == null) {
            Photos photo = (Photos) getIntent().getSerializableExtra("photo");
            DetailPhotoFragment dPF = DetailPhotoFragment.NovaInstancia(photo);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.idDetailPhotoFragment, dPF)
                    .commit();
        }



    }
}
