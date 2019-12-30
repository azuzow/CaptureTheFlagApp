package com.alexzuzow.capturetheflagapp.Sprites;

import com.alexzuzow.capturetheflagapp.CaptureTheFlagApp;
import com.alexzuzow.capturetheflagapp.Screens.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
public class Lava extends InteractiveTileObject{


    // DO SOMETHING WITH THE DEFAULT CATEGORY
    private short defaultCategoryBits;
    private int defaultTrap;
    private boolean isBlueTrap;
    private boolean isRedTrap;
    public Lava(GameScreen screen, Rectangle rectangleBounds, Ellipse ellipseBounds,short defaultCategoryBits,int defaultTrap) {
        super(screen, rectangleBounds, null,null);



        this.defaultCategoryBits=defaultCategoryBits;
        this.defaultTrap=defaultTrap;
        fixture.setUserData(this);
        setCategoryFilter(defaultCategoryBits);
        getCell().setTile(null);
//        System.out.println("LAVA CATEGORY BITS "+ defaultCategoryBits);
        if(defaultTrap==-1){
            isBlueTrap=true;
            isRedTrap=false;
        }
        else if(defaultTrap==0){
            isBlueTrap=false;
            isRedTrap=false;
        }
        else{
            isBlueTrap=false;
            isRedTrap=true;
        }
    }

    @Override
    public void onContact(String player) {

        Gdx.app.log("Lava Pressed", player);
    }

    @Override
    public void onContact(Object player) {

    }

    @Override
    public void onExit(String player) {

    }
    public void setRedTrap(){
        isRedTrap=true;
        isBlueTrap=false;
        setCategoryFilter(CaptureTheFlagApp.TRAP_BIT);
        getCell().setTile(map.getTileSets().getTile(63));



    }
    public void setBlueTrap(){
        isBlueTrap=true;
        isRedTrap=false;
        setCategoryFilter(CaptureTheFlagApp.TRAP_BIT);
        getCell().setTile(map.getTileSets().getTile(64));


    }
    public void resetTrap(){

        setCategoryFilter(defaultCategoryBits);
        if(defaultTrap==0) {
            getCell().setTile(null);
            isBlueTrap=false;
            isRedTrap=false;
        }
        else if(defaultTrap==1){
            getCell().setTile(map.getTileSets().getTile(63));
            isBlueTrap=false;
            isRedTrap=true;
        }
        else{
            getCell().setTile(map.getTileSets().getTile(64));
            isBlueTrap=true;
            isRedTrap=false;
        }
    }
    public boolean isRedTeam(){
        return isRedTrap;
    }
    public boolean isBlueTeam(){
        return isBlueTrap;
    }

}
