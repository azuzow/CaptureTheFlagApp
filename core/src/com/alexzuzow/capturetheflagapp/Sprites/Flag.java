package com.alexzuzow.capturetheflagapp.Sprites;

import com.alexzuzow.capturetheflagapp.CaptureTheFlagApp;
import com.alexzuzow.capturetheflagapp.Screens.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;

public class Flag extends InteractiveTileObject {
    private boolean flagInUse;
    private short categoryBit;
    private boolean flagScored;
    private boolean isRedFlag;
    private boolean isBlueFlag;
    public Flag(GameScreen screen, Rectangle rectangleBounds, Ellipse ellipseBounds, short categoryBit,boolean isBlueFlag,boolean isRedFlag) {
        super(screen, rectangleBounds, ellipseBounds,null);
        fixture.setUserData(this);
        this.categoryBit = categoryBit;
        setCategoryFilter(categoryBit);
        flagInUse = false;
        flagScored = false;
        this.isBlueFlag=isBlueFlag;
        this.isRedFlag=isRedFlag;

    }

    @Override
    public void onContact(String player) {
        setCategoryFilter(CaptureTheFlagApp.USED_BIT);
//        Gdx.app.log("Pickup Flag", player);
        getCell().setTile(null);
    }

    @Override
    public void onContact(Object player) {

    }

    @Override
    public void onExit(String player) {

    }

    @Override
    public boolean isBlueTeam() {
        return isBlueFlag;
    }

    @Override
    public boolean isRedTeam() {
        return isRedFlag;
    }

    public void flagCaptured(String player) {
        flagScored = true;
        setCategoryFilter(categoryBit);
//        Gdx.app.log("Pickup Flag", player);
        getCell().setTile(map.getTileSets().getTile(31));
    }

    public boolean wasFlagCaptured() {
        return flagScored;
    }

    public void resetFlag() {
        flagScored = false;
        setCategoryFilter(categoryBit);
        if (isRedFlag) {
            getCell().setTile(map.getTileSets().getTile(31));
        } else {
            getCell().setTile(map.getTileSets().getTile(32));
        }

    }
}
