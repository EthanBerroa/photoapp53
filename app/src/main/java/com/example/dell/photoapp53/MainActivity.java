package com.example.dell.photoapp53;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.view.MenuItem;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.view.ContextMenu.ContextMenuInfo;
import android.content.Context;
import android.widget.ArrayAdapter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements java.io.Serializable{

    private static final String TAG = MainActivity.class.getSimpleName();
    public static User user;
    public static String saveFile = "data";
    private String[] def = {"Add some albums!"};
    public static int currentAlbum;
    public static ArrayList<Photo> results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user= new User();

        loadData();

        if(user.albums.contains(new Album("RESULT7")))
        {
            user.deleteAlbum(user.albums.indexOf(new Album("RESULT7")));
            saveData();
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);
        }

        Log.d(TAG, "current album number = " + user.getAlbums().size());
        GridView gridview = (GridView) findViewById(R.id.album_list);
        if (user.getAlbums().size() != 0){
            gridview.setAdapter(new GridAdapter(this));
            registerForContextMenu(gridview);
            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {

                    //stuff
                    saveData();
                    currentAlbum = position;
                    Log.d(TAG, "current album number = " + user.getAlbums().size());
                    Intent intent = new Intent(MainActivity.this, AlbumScreen.class);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    startActivity(intent);
                }
            });
        }
        else{
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, def);
            gridview.setAdapter(adapter);
        }

        TextView add = (TextView)findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveData();
                Intent intent = new Intent(MainActivity.this, NewAlbumScreen.class);
                startActivity(intent);

            }
        });



        TextView search = (TextView)findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveData();
                Intent intent = new Intent(MainActivity.this, Search.class);
                startActivity(intent);

            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.album_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;
        switch (item.getItemId()) {

            case R.id.delete:
                //delete album
                user.deleteAlbum(position);
                saveData();
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.rename:
                currentAlbum = position;
                saveData();
                Intent intent2 = new Intent(MainActivity.this, RenameScreen.class);
                startActivity(intent2);

                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }




    private void loadData(){
        try{
            FileInputStream fis = openFileInput(saveFile);
        }catch(Exception e){
            Log.d(TAG, "File does not exist!!!!");
            try{
                saveData();
            }catch(Exception d){
                Log.d(TAG, "File creation failed!!!!");
            }

        }

        try {
            FileInputStream fis = openFileInput(saveFile);
            ObjectInputStream ois = new ObjectInputStream(fis);
            user = (User)ois.readObject();
            ois.close();
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    private void saveData(){

        if (user == null){
            user = new User();
        }
        try {
            FileOutputStream fos = openFileOutput(saveFile, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(user);
            oos.flush();
            oos.close();
            Log.d(TAG, "saved the file!!!");
        } catch(Exception e) {
            e.printStackTrace();
        }

    }


}
