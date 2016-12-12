package com.example.dell.photoapp53;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by Dell on 12/11/2016.
 */
public class Search extends AppCompatActivity {

    private static final String TAG = Search.class.getSimpleName();

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_screen);

        loadData();

        final EditText tags = (EditText)findViewById(R.id.tags);

        Button search = (Button)findViewById(R.id.search);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Photo> photos = new ArrayList<Photo>();
                String text = tags.getText().toString();
                if (text.length() > 0){
                    String[] tokens = tags.getText().toString().split(" ");

                    for (int z = 0; z < tokens.length; z++){
                        String input = tokens[z];
                        for (int i = 0; i < MainActivity.user.getAlbums().size(); i++){
                            Album album = MainActivity.user.getAlbums().get(i);
                            for (int j = 0; j < album.getPhotos().size(); j++){
                                Photo photo = album.getPhotos().get(j);
                                for (int k = 0; k < photo.tags.size(); k++){
                                    Tag tag = photo.tags.get(k);
                                    if (tag.getValue().contains(input)){
                                        if (!photos.contains(photo))
                                            photos.add(photo);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }

                MainActivity.results = photos;
                Album resultalbum = new Album("RESULT7");
                resultalbum.photos=photos;
                MainActivity.user.albums.add(resultalbum);

                MainActivity.user.setCurrentAlbum(MainActivity.user.albums.indexOf(resultalbum));
                MainActivity.currentAlbum=MainActivity.user.currentAlbumIndex;
                saveData();
                Intent intent = new Intent(Search.this,Results.class);
                finish();
                startActivity(intent);

            }
        });

        Button cancel = (Button)findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                Intent intent = new Intent(Search.this, MainActivity.class);
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
