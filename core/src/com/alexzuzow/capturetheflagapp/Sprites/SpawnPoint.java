package com.alexzuzow.capturetheflagapp.Sprites;

import com.alexzuzow.capturetheflagapp.CaptureTheFlagApp;
import com.alexzuzow.capturetheflagapp.Screens.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;

public class SpawnPoint extends InteractiveTileObject {
public int playersInArea;

    public SpawnPoint(GameScreen screen, Rectangle rectangleBounds, Ellipse ellipseBounds) {
        super(screen, rectangleBounds, ellipseBounds,null);
        playersInArea=0;
        fixture.setUserData(this);
        fixture.isSensor();
        setCategoryFilter(CaptureTheFlagApp.INTERACTIVE_ITEM_BIT);
        //create bomb object

    }

    @Override
    public void onContact(String player) {
//        Gdx.app.log("Entered Spawn Area",player);
        playersInArea++;
//        System.out.println(playersInArea);
    }

    @Override
    public void onContact(Object player) {

    }

    public void onExit(String player){
//        Gdx.app.log("Left Spawn Area",player);
        playersInArea--;

    }

    @Override
    public boolean isBlueTeam() {
        return false;
    }

    @Override
    public boolean isRedTeam() {
        return false;
    }

    public boolean isSafeSpawnPoint(){
        if(playersInArea==0){
//            System.out.println(playersInArea);
            return true;
        }
        else{
            return false;
        }
    }
    public float getX(){
        return body.getPosition().x;
    }
    public float getY(){
       return body.getPosition().y;
    }
}
