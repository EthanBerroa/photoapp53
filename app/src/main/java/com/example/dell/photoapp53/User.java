package com.example.dell.photoapp53;

import java.io.IOException;
import java.util.ArrayList;

/**
 * User object class. A user is comprised of a name, a password (useless), a currentAlbumIndex (keeps track of which album is open),
 * and an arrayList of all albums this user has.
 * @author Ethan Berroa
 * @author Milan Patel
 *
 */
public class User implements java.io.Serializable{

    public int currentAlbumIndex;
    public ArrayList<Album> albums;


    public User()
    {

        albums = new ArrayList<Album>();
    }

    /**
     * Serialization, writes user data.
     * @param stream
     * @throws IOException
     */
    private void writeObject(java.io.ObjectOutputStream stream)
            throws IOException
    {
        stream.writeObject(albums);
    }

    /**
     * Serialization, reads in user data.
     * @param stream
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void readObject(java.io.ObjectInputStream stream)
            throws IOException, ClassNotFoundException
    {

        albums = (ArrayList<Album>) stream.readObject();
    }


    /**
     * Adds a new album to this user's list.
     * @param name
     */
    public void addAlbum(String name)
    {
        albums.add(new Album(name));
    }

    /**
     * Changes the current album open.
     * @param i
     */
    public void setCurrentAlbum(int i)
    {
        this.currentAlbumIndex = i;
    }



    /**
     * Gets the specified album from the album list via index i.
     * @param i
     * @return
     */
    public Album getAlbum(int i){
        return this.albums.get(i);
    }

    /**
     * Gets the specified album from this users' list that has the specified NAME, returns null if there is no such album.
     * @param name
     * @return
     */
    public Album getAlbum(String name){
        for(int i = 0; i < this.albums.size(); i++)
        {
            if(this.albums.get(i).name.equals(name))
                return this.albums.get(i);
        }
        return null;
    }

    /**
     * Returns the entire album list.
     * @return
     */
    public ArrayList<Album> getAlbums() {
        // TODO Auto-generated method stub
        return this.albums;
    }

    /**
     * Deletes the album from the list at index i.
     * @param i
     */
    public void deleteAlbum(int i) {
        this.albums.remove(i);
        // TODO Auto-generated method stub

    }



}
