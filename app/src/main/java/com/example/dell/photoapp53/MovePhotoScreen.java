package com.example.dell.photoapp53;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Ethan Berroa
 * Milan Patel
 */

public class MovePhotoScreen extends AppCompatActivity{

        ArrayList<Album> albums = MainActivity.user.getAlbums();
        ListView listView;
        Album previousAlbum = MainActivity.user.getAlbums().get(MainActivity.currentAlbum);
        int albumsIndex = MainActivity.currentAlbum;
        int size = MainActivity.user.getAlbums().size();
        Photo photo = previousAlbum.getPhotos().get(AlbumScreen.currentPhoto);
        Album toAlbum;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.album_move_list);

            ArrayAdapter adapter = new ArrayAdapter<Album>(this, R.layout.album_move, albums);
            listView = (ListView) findViewById(R.id.album_move_list);



            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {
                    Album album = (Album) parent.getAdapter().getItem(position);
                    Intent intent = new Intent(MovePhotoScreen.this, AlbumScreen.class);
                    previousAlbum.deletePhoto(photo);
                    toAlbum = MainActivity.user.getAlbums().get(position);
                    toAlbum.addPhoto(photo);
                    Toast.makeText(MovePhotoScreen.this, "Photo moved to: " + toAlbum.toString(),
                            Toast.LENGTH_SHORT).show();
                    saveData();
                    finish();
                    startActivity(intent);
                }
            });
        }

        private void loadData(){
            try {
                FileInputStream fis = openFileInput(MainActivity.saveFile);
                ObjectInputStream ois = new ObjectInputStream(fis);
                MainActivity.user = (User)ois.readObject();
                ois.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        private void saveData(){


            try {


                FileOutputStream fos = openFileOutput(MainActivity.saveFile, Context.MODE_PRIVATE);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(MainActivity.user);
                oos.flush();
                oos.close();
            } catch(Exception e) {
                e.printStackTrace();
            }

        }


}
