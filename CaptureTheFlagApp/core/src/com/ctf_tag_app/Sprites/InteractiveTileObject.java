package com.ctf_tag_app.Sprites;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.ctf_tag_app.CaptureTheFlagApp;
import com.ctf_tag_app.Screens.GameScreen;


public abstract class InteractiveTileObject {
    protected World world;
    protected TiledMap map;
    protected Rectangle rectangleBounds;
    protected Ellipse ellipseBounds;
    protected Body body;
    protected Fixture fixture;
    public InteractiveTileObject(GameScreen screen, Rectangle rectangleBounds, Ellipse ellipseBounds){
        this.world = screen.getWorld();
        this.map=screen.getMap();
        this.rectangleBounds= rectangleBounds;
        this.ellipseBounds = ellipseBounds;
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();
        CircleShape circleShape = new CircleShape();
        //shape is circle
        if(rectangleBounds==null){
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((ellipseBounds.x+ellipseBounds.width/2)/ CaptureTheFlagApp.PPM,(ellipseBounds.y+ellipseBounds.height/2)/CaptureTheFlagApp.PPM);
            body= world.createBody(bdef);
            circleShape.setRadius((ellipseBounds.width/2)/CaptureTheFlagApp.PPM);
            fdef.shape=circleShape;
            fdef.isSensor=true;
            fixture =body.createFixture(fdef);

        }
        //shape is rectangle
        else{
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rectangleBounds.getX()+rectangleBounds.getWidth()/2)/ CaptureTheFlagApp.PPM,(rectangleBounds.getY()+rectangleBounds.getHeight()/2)/CaptureTheFlagApp.PPM);
            body= world.createBody(bdef);
            polygonShape.setAsBox((rectangleBounds.getWidth()/2)/ CaptureTheFlagApp.PPM,(rectangleBounds.getHeight()/2)/CaptureTheFlagApp.PPM);
            fdef.shape=polygonShape;
            if(this instanceof SpawnPoint){
                fdef.isSensor=true;
            }
            fixture= body.createFixture(fdef);

        }

    }
    public abstract void onContact(String player);
    public abstract  void onExit(String player);
    public void setCategoryFilter(short filterBit){
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }
    public TiledMapTileLayer.Cell getCell(){
        TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get(2);
        return layer.getCell((int)(body.getPosition().x*CaptureTheFlagApp.PPM/40),(int)(body.getPosition().y*CaptureTheFlagApp.PPM/40));
    }
}
