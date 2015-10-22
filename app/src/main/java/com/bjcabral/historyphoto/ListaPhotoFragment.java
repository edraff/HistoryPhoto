package com.bjcabral.historyphoto;

import android.app.DownloadManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

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

/**
 * Created by Bruno on 11/10/2015.
 */
public class ListaPhotoFragment extends ListFragment {
    List<Photos> listPhoto;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new PhotoTask().execute();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Photos photo = listPhoto.get(position);
        if(getActivity() instanceof AoClicarNaFoto){
            ((AoClicarNaFoto)getActivity()).clicarNaFoto(photo);
        }
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
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(History history) {
            super.onPostExecute(history);
            if(history!=null && history.historyPhoto!=null){
                listPhoto = new ArrayList<>();
                for (HistoryPhoto historyPhoto:history.historyPhoto) {
                    listPhoto.addAll(historyPhoto.photos);
                    
                }
                ArrayAdapter<Photos> listAdapterPhotos = new ArrayAdapter<Photos>(
                        getActivity(),android.R.layout.simple_list_item_1,listPhoto);
                setListAdapter(listAdapterPhotos);
            }
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
