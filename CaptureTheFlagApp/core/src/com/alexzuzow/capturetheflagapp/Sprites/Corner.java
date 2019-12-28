package com.alexzuzow.capturetheflagapp.Sprites;

import com.alexzuzow.capturetheflagapp.CaptureTheFlagApp;
import com.alexzuzow.capturetheflagapp.Screens.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;

public class Corner extends InteractiveTileObject{
    public Corner(GameScreen screen, Rectangle rectangleBounds, Ellipse ellipseBounds,Polygon polygonBounds) {
        super(screen, rectangleBounds, ellipseBounds,polygonBounds);
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
}
