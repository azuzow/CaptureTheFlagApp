package com.ctf_tag_app.Sprites;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.ctf_tag_app.CaptureTheFlagApp;
import com.ctf_tag_app.Screens.GameScreen;

public  abstract class Player1 extends Sprite {
    protected World world;
    protected TiledMap map;
    public Body b2Body;
    protected TextureRegion playerBody;
    protected Fixture fixture;
    protected float xCoord;
    protected float yCoord;
    protected short playerCategoryBit;
    protected short carrierCategoryBit;
    protected  boolean hasFlag;
    protected boolean alive;
    protected long respawnTimer;
    public Player1(GameScreen screen, float xCoord, float yCoord,String playerSprite){
        super(new Texture(Gdx.files.internal(playerSprite)));
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
    public void setCategoryFilter(short filterBit){
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
        fixture.setUserData(this);
    }

}
