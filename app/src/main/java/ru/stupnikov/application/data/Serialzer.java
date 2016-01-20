package ru.stupnikov.application.data;

import android.content.Context;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by rodion on 21.01.16.
 */

public class Serialzer {

    public Serialzer() {}

    public static final String FILE_DATA = "file_data";


    public void  write(ArrayList<Pouch> listPouchs) {
        try {
            FileOutputStream fos = new FileOutputStream(FILE_DATA);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            for (Pouch pouch: listPouchs) {
                oos.writeBoolean(true);
                oos.writeChars(pouch.name);
                oos.writeInt(pouch.value);
                oos.writeInt(pouch.position);
            }
            oos.writeBoolean(false);
            oos.close();
            fos.close();

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Pouch> read() {
        try
        {
            FileInputStream fis = new FileInputStream(FILE_DATA);
            ObjectInputStream ois = new ObjectInputStream(fis);
            ArrayList<Pouch> listPouchs = new ArrayList<Pouch>();

            while (ois.readBoolean())
            {
                Pouch p = new Pouch(String.valueOf(ois.readObject()));
                p.value = ois.readInt();
                p.position = ois.readInt();
                listPouchs.add(p);
            }

            return listPouchs;

        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
            return null;
        }

    }
}
