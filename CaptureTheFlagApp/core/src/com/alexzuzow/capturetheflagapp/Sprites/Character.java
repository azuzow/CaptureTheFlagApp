package com.alexzuzow.capturetheflagapp.Sprites;


import com.alexzuzow.capturetheflagapp.Screens.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

public  abstract class Character extends Sprite {
    protected World world;
    protected TiledMap map;
    public Body b2Body;
    protected Fixture fixture;
    public String name;
    //come back and dispose of the Texture
    public Character(GameScreen screen, float xCoord, float yCoord, Texture playerSprite){
        super(playerSprite);
        this.world = screen.getWorld();
        this.map = screen.getMap();
        setPosition(xCoord,yCoord);
        definePlayer();

    }
    public abstract void update(float x,float y);
    protected abstract void definePlayer();
    public abstract void onPlayerContact();
    public abstract  void onSpikeContact();

    public abstract  void onFlagPickup();
    public abstract void  onFlagCapture();
    public abstract  void onLavaContact();
    public void setCategoryFilter(short filterBit){
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
        fixture.setUserData(this);
    }

}
