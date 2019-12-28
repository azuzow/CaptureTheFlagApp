package com.alexzuzow.capturetheflagapp.Sprites;

import com.alexzuzow.capturetheflagapp.CaptureTheFlagApp;
import com.alexzuzow.capturetheflagapp.Screens.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class Bomb extends InteractiveTileObject {
    private boolean hasExploded;
    private long respawnTimer;
    public Bomb(GameScreen screen, Rectangle rectangleBounds, Ellipse ellipseBounds) {
        super(screen, rectangleBounds, ellipseBounds,null);
        fixture.setUserData(this);
        hasExploded=false;
        respawnTimer=0;

        setCategoryFilter(CaptureTheFlagApp.BOMB_BIT);
        //create bomb object

    }

    @Override
    public void onContact(String player) {
        setCategoryFilter(CaptureTheFlagApp.USED_BIT);
        Gdx.app.log("Bomb Hit",player);
        //use this to change the tile to another tile
        //getCell().setTile(map.getTileSets().getTile(1));
        //use this to clear the tile space
        getCell().setTile(null);
        explode(world);
        hasExploded=true;
        respawnTimer = System.currentTimeMillis();
    }

    @Override
    public void onContact(Object player) {

    }

    @Override
    public void onExit(String player) {

    }

    public void resetBomb(){
        setCategoryFilter(CaptureTheFlagApp.BOMB_BIT);
        getCell().setTile(map.getTileSets().getTile(29));
        hasExploded=false;
    }
    public void update(float dt){
        if(hasExploded){
            long currentTime = System.currentTimeMillis();
            if((currentTime-respawnTimer)/1000>=60){
                resetBomb();
            }
        }
    }
    void applyBlastImpulse(Body body, Vector2 blastCenter, Vector2 applyPoint, float blastPower) {
        Vector2 blastDir = applyPoint.sub(blastCenter);
        float distance = blastDir.len();
        //ignore bodies exactly at the blast point - blast direction is undefined
        if ( distance == 0 )
            return;
        float invDistance = 1 / distance;
        float impulseMag = blastPower * invDistance * invDistance;
        body.applyLinearImpulse(  blastDir.scl(impulseMag), applyPoint,true );
    }
    void explode(World world){
        Array<Body> bodies = new Array<Body>();
        System.out.println(world.getBodyCount());
        world.getBodies(bodies);
        for(Body bod: bodies){
            if(bod!= body){

                Vector2 bombPosition = body.getPosition();
                Vector2 playerPosition = bod.getPosition();
                if(bombPosition.dst(playerPosition)<0.9f){
                    applyBlastImpulse(bod,bombPosition,playerPosition,4);
                }
            }

        }

    }


}
