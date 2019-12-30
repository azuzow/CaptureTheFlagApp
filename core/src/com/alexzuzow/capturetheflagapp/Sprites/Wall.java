package com.alexzuzow.capturetheflagapp.Sprites;

import com.alexzuzow.capturetheflagapp.CaptureTheFlagApp;
import com.alexzuzow.capturetheflagapp.Screens.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;


public class Wall extends InteractiveTileObject {
    public Wall(GameScreen screen, Rectangle rectangleBounds, Ellipse ellipseBounds) {
        super(screen, rectangleBounds, null,null);
        fixture.setUserData(this);
        setCategoryFilter(CaptureTheFlagApp.WALL_BIT);
        //create Wall object


    }

    @Override
    public void onContact(String player) {
        Gdx.app.log("Hit Wall", player);
    }

    @Override
    public void onContact(Object player) {

    }

    @Override
    public void onExit(String player) {

    }

    @Override
    public boolean isBlueTeam() {
        return false;
    }

    @Override
    public boolean isRedTeam() {
        return false;
    }
}
