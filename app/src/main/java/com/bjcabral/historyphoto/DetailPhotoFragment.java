package com.bjcabral.historyphoto;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bjcabral.historyphoto.model.Photos;
import com.squareup.picasso.Picasso;

/**
 * Created by Bruno on 11/10/2015.
 */
public class DetailPhotoFragment extends Fragment {

    public static DetailPhotoFragment NovaInstancia(Photos p){
        Bundle args = new Bundle();
        args.putSerializable("photo", p);

        DetailPhotoFragment dPF = new DetailPhotoFragment();
        dPF.setArguments(args);
      return dPF;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Photos photo = (Photos) getArguments().getSerializable("photo");

        View layout = inflater.inflate(R.layout.layout_detail_fragment,null);
        TextView txtTitulo = (TextView) layout.findViewById(R.id.idTitulo);
        TextView txtLocal = (TextView) layout.findViewById(R.id.idLocal);
        TextView txtData = (TextView) layout.findViewById(R.id.idData);
        TextView txtDescricao = (TextView) layout.findViewById(R.id.idDescricao);
        ImageView img = (ImageView) layout.findViewById(R.id.imageView);
        Picasso.with(getActivity())
                .load(photo.urlImg)
                .into(img);

        txtTitulo.setText(photo.titulo);
        txtLocal.setText(photo.local);
        txtData.setText(photo.data);
        txtDescricao.setText(photo.historia);

        return layout;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_detalhe,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() ==R.id.acao_favorito){
            Toast.makeText(getActivity(),"Clicou em Favoritos",Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
