package com.example.dell.photoapp53;

/**
 * Created by Dell on 12/11/2016.
 */
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by Dell on 12/11/2016.
 */
public class PhotoScreen extends AppCompatActivity {

    private int THUMBNAIL_SIZE = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_screen);
        loadData();

        ImageView imageview = (ImageView)findViewById(R.id.currentImage);
        Bitmap bt = null;
        try{
            bt = getThumbnail(MainActivity.user.getAlbums().get(MainActivity.currentAlbum).getPhotos().get(AlbumScreen.currentPhoto).getURI());
        }catch(Exception e){
            e.printStackTrace();
        }
        imageview.setImageBitmap(bt);

        TextView tags = (TextView)findViewById(R.id.tags);
        ArrayList<Tag> taglist =  MainActivity.user.getAlbums().get(MainActivity.currentAlbum).getPhotos().get(AlbumScreen.currentPhoto).tags;
        String output = "";
        if (taglist.size() > 0){
            for (int i = 0; i < taglist.size() - 1; i++){
                output = output + taglist.get(i).getValue() + ", ";
            }
            output = output + " " + taglist.get(taglist.size() - 1).getValue();

            tags.setText(output);
        }


        TextView edit = (TextView)findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveData();
                Intent intent = new Intent(PhotoScreen.this, EditTags.class);
                startActivity(intent);
            }
        });

        ImageView previous = (ImageView)findViewById(R.id.previous);
        previous.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlbumScreen.currentPhoto--;
                if (AlbumScreen.currentPhoto >= 0){
                    saveData();
                    Intent intent = new Intent(PhotoScreen.this, PhotoScreen.class);
                    finish();
                    startActivity(intent);
                }
                else{
                    AlbumScreen.currentPhoto++;
                }

            }
        });

        ImageView next = (ImageView)findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlbumScreen.currentPhoto++;
                if (AlbumScreen.currentPhoto < MainActivity.user.getAlbums().get(MainActivity.currentAlbum).getPhotos().size()){
                    saveData();
                    Intent intent = new Intent(PhotoScreen.this, PhotoScreen.class);
                    finish();
                    startActivity(intent);
                }
                else{
                    AlbumScreen.currentPhoto--;
                }

            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.photo_menu, menu);
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

    public Bitmap getThumbnail(Uri uri) throws FileNotFoundException, IOException {
        InputStream input = PhotoScreen.this.getContentResolver().openInputStream(uri);

        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither=true;//optional
        onlyBoundsOptions.inPreferredConfig=Bitmap.Config.ARGB_8888;//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();
        if ((onlyBoundsOptions.outWidth == -1) || (onlyBoundsOptions.outHeight == -1))
            return null;

        int originalSize = (onlyBoundsOptions.outHeight > onlyBoundsOptions.outWidth) ? onlyBoundsOptions.outHeight : onlyBoundsOptions.outWidth;

        double ratio = (originalSize > THUMBNAIL_SIZE) ? (originalSize / THUMBNAIL_SIZE) : 1.0;

        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = getPowerOfTwoForSampleRatio(ratio);
        bitmapOptions.inDither=true;//optional
        bitmapOptions.inPreferredConfig=Bitmap.Config.ARGB_8888;//optional
        input = PhotoScreen.this.getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();
        return bitmap;
    }

    private static int getPowerOfTwoForSampleRatio(double ratio){
        int k = Integer.highestOneBit((int)Math.floor(ratio));
        if(k==0) return 1;
        else return k;
    }


}
