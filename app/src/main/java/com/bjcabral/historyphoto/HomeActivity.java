package com.bjcabral.historyphoto;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bjcabral.historyphoto.model.Photos;

public class HomeActivity extends AppCompatActivity
implements ListaPhotoFragment.AoClicarNaFoto{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new PaginasAdapter(getSupportFragmentManager()));
        TabLayout TabLayout = (TabLayout) findViewById(R.id.tabs);
        TabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void clicarNaFoto(Photos photo) {
        if(getResources().getBoolean(R.bool.cell)) {
            Intent it = new Intent(this, PhotoDetailActivity.class);
            it.putExtra("photo", photo);
            startActivity(it);
        }else{
            DetailPhotoFragment dPF = DetailPhotoFragment.NovaInstancia(photo);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.idDetailPhotoFragment, dPF)
                    .commit();
        }
    }

    private class PaginasAdapter extends FragmentPagerAdapter {

        public PaginasAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position==0){
                return new ListaPhotoFragment();
            }else{
                return new Fragment();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return position==0 ?"Web":"Favoritos";
        }
    }
}
