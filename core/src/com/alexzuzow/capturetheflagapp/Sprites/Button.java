package com.alexzuzow.capturetheflagapp.Sprites;

import com.alexzuzow.capturetheflagapp.CaptureTheFlagApp;
import com.alexzuzow.capturetheflagapp.Screens.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;


public class Button extends InteractiveTileObject {
    private Array<Lava> lava;
    public Button(GameScreen screen, Rectangle rectangleBounds, Ellipse ellipseBounds) {
        super(screen, rectangleBounds, ellipseBounds,null);
        fixture.setUserData(this);
        setCategoryFilter(CaptureTheFlagApp.INTERACTIVE_ITEM_BIT);
        lava=new Array<>();

    }

    @Override
    public void onContact(String player) {
        Gdx.app.log("Button Pressed", player);
//        Gdx.app.log("Bomb Hit",player);
        //use this to change the tile to another tile
        //getCell().setTile(map.getTileSets().getTile(1));
        //use this to clear the tile space
    }
    @Override
    public void onContact(Object player)  {
        User character = (User)player;
        System.out.println("Button pressed "+ character.getClinetID());
        if(character.isBlueTeam()){
            System.out.println("SET TO BLUE TRAP");
            for (Lava trap: lava) {
                trap.setBlueTrap();
            }
        }
        else{
            System.out.println("SET TO RED TRAP");
            for (Lava trap: lava) {
                trap.setRedTrap();
            }

        }
//        Gdx.app.log("Button Pressed", player);
//        Gdx.app.log("Bomb Hit",player);
        //use this to change the tile to another tile
        //getCell().setTile(map.getTileSets().getTile(1));
        //use this to clear the tile space



    }
    @Override
    public void onExit(String player) {
        System.out.println("END CONTACT WITH BUTTON");
        for (Lava trap: lava) {
            trap.resetTrap();
        }
    }

    @Override
    public boolean isBlueTeam() {
        return false;
    }

    @Override
    public boolean isRedTeam() {
        return false;
    }

    public void addLava(Lava lava){
        this.lava.add(lava);
    }

}


