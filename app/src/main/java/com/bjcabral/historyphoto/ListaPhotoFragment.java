package com.bjcabral.historyphoto;

import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bjcabral.historyphoto.model.History;
import com.bjcabral.historyphoto.model.HistoryPhoto;
import com.bjcabral.historyphoto.model.Photos;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Bruno on 11/10/2015 com controle de versão.
 */
public class ListaPhotoFragment extends ListFragment implements SwipeRefreshLayout.OnRefreshListener {
    List<Photos> listPhoto;
    ArrayAdapter<Photos> listAdapterPhotos;
    SwipeRefreshLayout mSwipe;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.lista_photo_fragment,null);
        //pegando as propriedades do novo lista_photo_fragment
        mSwipe = (SwipeRefreshLayout) view.findViewById(R.id.swipe);
        mSwipe.setOnRefreshListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listPhoto = new ArrayList<>();
        listAdapterPhotos = new ArrayAdapter<Photos>(
                getActivity(),android.R.layout.simple_list_item_1,listPhoto);
        setListAdapter(listAdapterPhotos);
        carregarPhoto();

    }

    //Metodo onde carrega a lista de photos e verifica a conexão com a internet.
    //
    private  void carregarPhoto(){
    ConnectivityManager cM = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = cM.getActiveNetworkInfo();

    if(networkInfo!=null && networkInfo.isConnected()){
        new PhotoTask().execute();
    }else{
        mSwipe.setRefreshing(false);
        Toast.makeText(getContext(),R.string.sem_conexao,Toast.LENGTH_LONG).show();
    }
}
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Photos photo = listPhoto.get(position);
        if(getActivity() instanceof AoClicarNaFoto){
            ((AoClicarNaFoto)getActivity()).clicarNaFoto(photo);
        }
    }

    @Override
    public void onRefresh() {
        carregarPhoto();

    }

    interface AoClicarNaFoto{
        void clicarNaFoto(Photos photo);
    }

    class PhotoTask extends AsyncTask<Void,Void,History>{

        @Override
        protected History doInBackground(Void... params) {
            OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url("https://dl.dropboxusercontent.com/s/q6mhg99wevbwci9/HistoryPhoto.json?dl=0")
                        .build();

            Response response = null;
            try {
                response = client.newCall(request).execute();
                String s = response.body().string();
                Gson gson = new Gson();
                History history = gson.fromJson(s, History.class);
                return history;
            } catch (Throwable e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(History history) {
            super.onPostExecute(history);
            if(history!=null && history.historyPhoto!=null){
                listPhoto.clear();
                for (HistoryPhoto historyPhoto:history.historyPhoto) {
                    listPhoto.addAll(historyPhoto.photos);
                }
                listAdapterPhotos.notifyDataSetChanged();
            }else{

                Toast.makeText(getActivity(),R.string.erro_geral,Toast.LENGTH_LONG).show();
            }
            mSwipe.setRefreshing(false);
        }
    }

}
