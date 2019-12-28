package com.alexzuzow.capturetheflagapp.Sprites;

import com.alexzuzow.capturetheflagapp.CaptureTheFlagApp;
import com.alexzuzow.capturetheflagapp.Screens.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
public class Lava extends InteractiveTileObject{
    private short defaultCategoryBits;
    public Lava(GameScreen screen, Rectangle rectangleBounds, Ellipse ellipseBounds,short defaultCategoryBits) {
        super(screen, rectangleBounds, null,null);



        this.defaultCategoryBits=defaultCategoryBits;
        fixture.setUserData(this);
        setCategoryFilter(defaultCategoryBits);
        getCell().setTile(null);
        System.out.println("LAVA CATEGORY BITS "+ defaultCategoryBits);

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

        setCategoryFilter(CaptureTheFlagApp.RED_TRAP_BIT);
        getCell().setTile(map.getTileSets().getTile(63));



    }
    public void setBlueTrap(){

        setCategoryFilter(CaptureTheFlagApp.BLUE_TRAP_BIT);
        getCell().setTile(map.getTileSets().getTile(64));


    }
    public void resetTrap(){
        System.out.println("==========================================");
        System.out.println("RESETING TRAP");
        System.out.println("==========================================");
        setCategoryFilter(defaultCategoryBits);
        if(defaultCategoryBits==CaptureTheFlagApp.USED_BIT) {
            getCell().setTile(null);
        }
        else if(defaultCategoryBits==CaptureTheFlagApp.RED_TRAP_BIT){
            getCell().setTile(map.getTileSets().getTile(63));
        }
        else{
            getCell().setTile(map.getTileSets().getTile(64));
        }
    }

}
