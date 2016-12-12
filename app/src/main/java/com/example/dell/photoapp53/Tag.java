package com.example.dell.photoapp53;

/**
 * Created by Dell on 12/8/2016.
 */

import java.io.IOException;


import java.io.IOException;
import java.util.ArrayList;

/**
 * Tag object class. A tag is simply a "type" string (either "Location", "Name", or "Other") and a value string.
 * @author Ethan Berroa
 * @author Milan Patel
 *
 */

public class Tag implements java.io.Serializable
{
    private String type;
    private String value;


    /**
     * Empty constructor.
     */
    public Tag(){

    }

    /**
     * Tag constructor that takes in type and value parameters.
     * @param type
     * @param value
     */
    public Tag(String type, String value){
        this.type = type;
        this.value = value;
    }



    public String getType(){
        return type;
    }


    public String getValue(){
        return value;
    }


    private void writeObject(java.io.ObjectOutputStream stream)
            throws IOException
    {
        stream.writeObject(type);
        stream.writeObject(value);
    }


    private void readObject(java.io.ObjectInputStream stream)
            throws IOException, ClassNotFoundException
    {
        type = (String) stream.readObject();
        value = (String)stream.readObject();
    }


    @Override
    public boolean equals(Object o){
        if (!(o instanceof Tag)) return false;
        else{
            Tag tag = (Tag)o;
            if (tag.getType().equals(this.getType()) && (tag.getValue().equals((this.getValue())))){
                return true;
            }
            else
            {
                return false;
            }
        }
    }


    public String toString(){
        return type + ":  " + value;
    }

}
