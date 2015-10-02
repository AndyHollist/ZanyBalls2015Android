package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

/**
 * Created by Andy on 9/13/2015.
 */

enum BallStreak{None,Agility,Immunity,WarpSpeed,AutoPilot}

public class MyPlayer {

    int level;
    int progress;
    int target_progress;
    int high_score;
    BallStreak selected_ballstreak;
    int current_streak;
    int target_streak;
    String file_name = "profile.sav";

    public MyPlayer(){
        level = 1;
        progress = 0;
        target_progress = 30;
        high_score = 0;
        selected_ballstreak = BallStreak.None;
        current_streak = 0;
        target_streak = 0;
    }

    public void load(){
        String save = readFile(file_name);
        if(save == "") return;
        Json json = new Json();
        MyPlayer profile = json.fromJson(MyPlayer.class, save);
        level = profile.level;
        progress = profile.progress;
        target_progress = profile.target_progress;
        high_score = profile.high_score;
        selected_ballstreak = profile.selected_ballstreak;
        current_streak = 0;
        target_streak = profile.target_streak;
    }

    public void save(){
        Json json = new Json();
        writeFile(file_name, json.toJson(this));
    }

    public void level_up(){

    }

    public static void writeFile(String fileName, String s) {
        FileHandle file = Gdx.files.local(fileName);
        file.writeString(com.badlogic.gdx.utils.Base64Coder.encodeString(s), false);
    }

    public static String readFile(String fileName) {
        FileHandle file = Gdx.files.local(fileName);
        if (file != null && file.exists()) {
            String s = file.readString();
            if (!s.isEmpty()) {
                return com.badlogic.gdx.utils.Base64Coder.decodeString(s);
            }
        }
        return "";
    }

}
