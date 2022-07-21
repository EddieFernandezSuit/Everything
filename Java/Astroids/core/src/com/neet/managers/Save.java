package com.neet.managers;

import com.badlogic.gdx.Gdx;

import java.io.*;

public class Save {

    public static GameData gd;

    static {
        Save.init();
    }

    public static void save(){
        try{
            ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream("highScores.sav")
            );
            out.writeObject(gd);
            out.close();
        }
        catch(Exception e){
            e.printStackTrace();
            Gdx.app.exit();
        }
    }

    public static void load(){
        if(!saveFileExists()){
            save();
        }

        try{
            ObjectInputStream in = new ObjectInputStream(
                    new FileInputStream("highScores.sav")
            );
            gd = (GameData) in.readObject();
            in.close();
        }
        catch(Exception e){
            e.printStackTrace();
            Gdx.app.exit();
        }
    }

    public static boolean saveFileExists(){
        File f = new File("highScores.sav");
        return f.exists();
    }

    public static void init(){
        gd = new GameData();
        gd.init();
        Save.load();
    }


}
