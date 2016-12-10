package com.example.dell.photoapp53;

/**
 * Created by Dell on 12/8/2016.
 */

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Album object class. Contains  a name and a arraylist of photos.
 * @author Ethan Berroa
 * @author Milan Patel
 *
 */
public class Album implements java.io.Serializable{

    public String name;
    public ArrayList<Photo> photos;

    /**
     * Album constructor, only takes in a name.
     * @param name
     */
    public Album(String name)
    {
        this.name = name;
        this.photos = new ArrayList<Photo>();
    }

    /**
     * Overriden equals method, two albums are considered "equal" if they have the same name.
     */
    @Override
    public boolean equals(Object o){
        if (!(o instanceof Album)) return false;
        else{
            Album album = (Album)o;
            if (album.name.equals(this.name)){
                return true;
            }
            else
            {
                return false;
            }
        }
    }

    /**
     * Returns album name string.
     * @return
     */
    public String getAlbumName() {
        // TODO Auto-generated method stub
        return this.name;
    }

    /**
     * Adds a photo to this album's arraylist photos.
     * @param photo
     */
    public void addPhoto(Photo photo)
    {
        this.photos.add(photo);
    }

    public void deletePhoto(Photo photo)
    {
        for(int i = 0; i < this.photos.size(); i++)
        {
            if(photos.get(i).equals(photo))
                photos.remove(i);
        }
    }

    /**
     * Returns the entire arraylist of photos in this album.
     * @return
     */
    public ArrayList<Photo> getPhotos() {
        // TODO Auto-generated method stub
        return photos;
    }

    /**
     * Serialization, writes data.
     * @param stream
     * @throws IOException
     */
    private void writeObject(java.io.ObjectOutputStream stream)
            throws IOException
    {
        stream.writeObject(name);
        stream.writeObject(photos);
    }

    /**
     * Serialization, reads data in.
     * @param stream
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void readObject(java.io.ObjectInputStream stream)
            throws IOException, ClassNotFoundException
    {
        name = (String) stream.readObject();
        photos = (ArrayList<Photo>)stream.readObject();
    }

}