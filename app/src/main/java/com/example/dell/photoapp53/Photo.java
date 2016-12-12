package com.example.dell.photoapp53;

/**
 * Created by Dell on 12/8/2016.
 */

import android.net.Uri;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class Photo implements java.io.Serializable{

    private String URI;
    private static final long serialVersionUID = 2L;

    public ArrayList<Tag> tags;

    public Photo(Uri uri){
        this.URI = uri.toString();
        this.tags = new ArrayList<Tag>();
    }


    public Uri getURI() {
        // TODO Auto-generated method stub
        return Uri.parse(URI);
    }


    public boolean equals (Object o)
    {
        if (!(o instanceof Photo)) return false;
        else{
            Photo photo = (Photo)o;
            if (photo.getURI().equals(this.getURI())){
                return true;
            }
            else
            {
                return false;
            }
        }

    }


    public boolean containsTag(Tag tmp)
    {
        for(int i = 0; i < tags.size(); i++)
        {
            if(tmp.equals(tags.get(i)))
                return true;
        }
        return false;
    }


    public boolean addTag(Tag tmp)
    {
        if(!this.containsTag(tmp))
        {
            tags.add(tmp);
            return true;
        }
        return false;
    }




    private void writeObject(java.io.ObjectOutputStream stream)
            throws IOException
    {
        stream.writeObject(URI);
        stream.writeObject(tags);

    }


    private void readObject(java.io.ObjectInputStream stream)
            throws IOException, ClassNotFoundException
    {
        URI = (String) stream.readObject();
        tags = (ArrayList<Tag>)stream.readObject();
    }


}