package com.ctf_tag_app.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.ctf_tag_app.CaptureTheFlagApp;
import com.ctf_tag_app.Screens.GameScreen;

public class Flag extends InteractiveTileObject {
    private boolean flagInUse;
    private short categoryBit;
    private boolean flagScored;

    public Flag(GameScreen screen, Rectangle rectangleBounds, Ellipse ellipseBounds, short categoryBit) {
        super(screen, rectangleBounds, ellipseBounds);
        fixture.setUserData(this);
        this.categoryBit = categoryBit;
        setCategoryFilter(categoryBit);
        flagInUse = false;
        flagScored = false;

    }

    @Override
    public void onContact(String player) {
        setCategoryFilter(CaptureTheFlagApp.USED_BIT);
        Gdx.app.log("Pickup Flag", player);
        getCell().setTile(null);
    }

    @Override
    public void onExit(String player) {

    }

    public void flagCaptured(String player) {
        flagScored = true;
        setCategoryFilter(categoryBit);
        Gdx.app.log("Pickup Flag", player);
        getCell().setTile(map.getTileSets().getTile(31));
    }

    public boolean wasFlagCaptured() {
        return flagScored;
    }

    public void resetFlag() {
        flagScored = false;
        setCategoryFilter(categoryBit);
        if (categoryBit == 4) {
            getCell().setTile(map.getTileSets().getTile(31));
        } else {
            getCell().setTile(map.getTileSets().getTile(32));
        }

    }
}
