package ru.stupnikov.application.data;

/**
 * Created by rodion on 21.01.16.
 */
public class Pouch  {

    public Pouch (String name) {this.name = name;}
    public Pouch (String name, int value, int position) {
        this.name =name; this.value =value; this.position =position;
    }

    public String name;
    public int value =0;
    public int position = 0;
}
