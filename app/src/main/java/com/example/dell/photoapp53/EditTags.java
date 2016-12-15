package com.example.dell.photoapp53;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Ethan Berroa
 * Milan Patel
 */


    public class EditTags extends AppCompatActivity {

        private static final String TAG = AlbumScreen.class.getSimpleName();
        public static int currentposition;

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.edittag_screen);
            loadData();

            Photo photo = MainActivity.user.getAlbums().get(MainActivity.currentAlbum).getPhotos().get(AlbumScreen.currentPhoto);

            final Spinner spinner = (Spinner) findViewById(R.id.types);
            final EditText tags = (EditText)findViewById(R.id.tags);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.tag_types, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);

            final ListView tagList1 = (ListView) findViewById(R.id.tags_list);
            ArrayAdapter<Tag> adapter1 = new ArrayAdapter<Tag>(EditTags.this, android.R.layout.simple_spinner_item, photo.tags);
            tagList1.setAdapter(adapter);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            tagList1.setAdapter(adapter1);


            Button add = (Button)findViewById(R.id.add);
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Rename album and save
                    String type = spinner.getSelectedItem().toString();
                    String input = tags.getText().toString();//.split(" "); Made it so tags are NOT separated by space.
                    Photo photo = MainActivity.user.getAlbums().get(MainActivity.currentAlbum).getPhotos().get(AlbumScreen.currentPhoto);

                    for (int i = 0; i < input.length(); i++) {
                        Tag tag = new Tag(type, input.toLowerCase());
                        if (!photo.tags.contains(tag)) {
                            photo.addTag(tag);
                            Log.d(TAG, "Tag added");
                            Toast.makeText(EditTags.this, "Tag added", Toast.LENGTH_SHORT).show();
                        }
                    }
                    saveData();
                    finish();
                    Intent intent = new Intent(EditTags.this, EditTags.class);
                    startActivity(intent);

                }
            });


            tagList1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                     EditTags.currentposition= position;}});

            Button delete = (Button)findViewById(R.id.delete);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Photo photo = MainActivity.user.getAlbums().get(MainActivity.currentAlbum).getPhotos().get(AlbumScreen.currentPhoto);

                    if (photo.tags.size() >= 0){
                        photo.tags.remove(currentposition);
                        Toast.makeText(EditTags.this, "Tag deleted", Toast.LENGTH_SHORT).show();
                        saveData();
                        finish();
                        Intent intent = new Intent(EditTags.this, EditTags.class);
                        startActivity(intent);
                    }


                }
            });

            Button save = (Button)findViewById(R.id.save);
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    saveData();
                    finish();
                    Intent intent = new Intent(EditTags.this, PhotoScreen.class);
                    startActivity(intent);

                }
            });

            Button cancel = (Button)findViewById(R.id.cancel);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    saveData();
                    finish();
                    Intent intent = new Intent(EditTags.this, PhotoScreen.class);
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
