package com.ctf_tag_app.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.ctf_tag_app.CaptureTheFlagApp;
import com.ctf_tag_app.Screens.GameScreen;

public class SpawnPoint extends InteractiveTileObject {
private int playersInArea;

    public SpawnPoint(GameScreen screen, Rectangle rectangleBounds, Ellipse ellipseBounds) {
        super(screen, rectangleBounds, ellipseBounds);
        playersInArea=0;
        fixture.setUserData(this);
        fixture.isSensor();
        setCategoryFilter(CaptureTheFlagApp.SPAWN_BIT);
        //create bomb object

    }

    @Override
    public void onContact(String player) {
        Gdx.app.log("Entered Spawn Area",player);
        playersInArea++;
    }
    public void onExit(String player){
        Gdx.app.log("Left Spawn Area",player);
        playersInArea--;
        System.out.println(playersInArea);
    }
    public boolean isSafeSpawnPoint(){
        if(playersInArea==0){
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
