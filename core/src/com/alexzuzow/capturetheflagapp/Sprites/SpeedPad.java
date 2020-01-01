package com.alexzuzow.capturetheflagapp.Sprites;

import com.alexzuzow.capturetheflagapp.CaptureTheFlagApp;
import com.alexzuzow.capturetheflagapp.Screens.GameScreen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class SpeedPad extends InteractiveTileObject{
    private Animation portalAnimation;
    private float stateTime;
    private Array<TextureRegion> frames;
    private static final float ANIMATION_SPEED=0.6f;
    private boolean isBlueTeam;
    private boolean isRedTeam;
    private boolean used;
    private long respawnTimer;

    public SpeedPad(GameScreen screen, Rectangle rectangleBounds, Ellipse ellipseBounds,String path,boolean isBlueTeam,boolean isRedTeam) {
        super(screen, rectangleBounds, ellipseBounds, null);
        this.isBlueTeam=isBlueTeam;
        this.isRedTeam=isRedTeam;
        stateTime=0;
        frames = new Array<TextureRegion>();
        for (int i = 0; i <5 ; i++) {
            frames.add(new TextureRegion(new Texture(path),i*40,0,40,40));
        }
        portalAnimation=new Animation(ANIMATION_SPEED,frames);
        fixture.setUserData(this);
        setCategoryFilter(CaptureTheFlagApp.INTERACTIVE_ITEM_BIT);
        respawnTimer=0;
        used=false;
    }
    @Override
    public void onContact(String player) {

    }
    @Override
    public void onContact(Object player) {
        if(!used) {
            User character = (User) player;
            Vector2 velocity = character.b2Body.getLinearVelocity();
            //neutral
            final float MAX_SPEED=8f;
            final float MIN_SPEED=-8f;
            if (!isBlueTeam && !isRedTeam) {

                character.b2Body.setLinearVelocity(velocity.x*8,velocity.y*8);
                if(velocity.x*8>=MAX_SPEED){
                    character.b2Body.setLinearVelocity(MAX_SPEED,velocity.y);
                }
                if(velocity.y*8>=MAX_SPEED){
                    character.b2Body.setLinearVelocity(velocity.x,MAX_SPEED);
                }
                if(velocity.x*8<=MIN_SPEED){
                    character.b2Body.setLinearVelocity(MIN_SPEED,velocity.y);
                }
                if(velocity.y*8<=MIN_SPEED){
                    character.b2Body.setLinearVelocity(velocity.x,MIN_SPEED);
                }
                used=true;
                respawnTimer=System.currentTimeMillis();
            }
            //red
            else if (isRedTeam&&character.isRedTeam()) {

                character.b2Body.setLinearVelocity(velocity.x*8,velocity.y*8);
                if(velocity.x*8>=MAX_SPEED){
                    character.b2Body.setLinearVelocity(MAX_SPEED,velocity.y);
                }
                if(velocity.y*8>=MAX_SPEED){
                    character.b2Body.setLinearVelocity(velocity.x,MAX_SPEED);
                }
                if(velocity.x*8<=MIN_SPEED){
                    character.b2Body.setLinearVelocity(MIN_SPEED,velocity.y);
                }
                if(velocity.y*8<=MIN_SPEED) {
                    character.b2Body.setLinearVelocity(velocity.x, MIN_SPEED);
                }
                    used=true;
                respawnTimer=System.currentTimeMillis();
            }
            //blue
            else if (isBlueTeam&&character.isBlueTeam()) {
                character.b2Body.setLinearVelocity(velocity.x*8,velocity.y*8);

                if(velocity.x*8>=MAX_SPEED){
                    character.b2Body.setLinearVelocity(MAX_SPEED,velocity.y);
                }
                if(velocity.y*8>=MAX_SPEED){
                    character.b2Body.setLinearVelocity(velocity.x,MAX_SPEED);
                }
                if(velocity.x*8<=MIN_SPEED){
                    character.b2Body.setLinearVelocity(MIN_SPEED,velocity.y);
                }
                if(velocity.y*8<=MIN_SPEED) {
                    character.b2Body.setLinearVelocity(velocity.x, MIN_SPEED);
                }
                used=true;
                respawnTimer=System.currentTimeMillis();
            }
            System.out.println(character.b2Body.getLinearVelocity());

        }
//        long currentTime = System.currentTimeMillis();
//        if ((currentTime - character.getPortalCooldown()) / 1000 >= 1) {
//            character.setPortalCooldown(currentTime);
//            character.setSetToTeleport(pair.getX(),pair.getY());
//            System.out.println("teleporting");
//        }

    }
    public void update(float dt){
        if(used){
            long currentTime = System.currentTimeMillis();
            if((currentTime-respawnTimer)/1000>=10){
                used=false;
            }
        }
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
    public float getX(){
        return body.getPosition().x;
    }
    public float getY(){
        return body.getPosition().y;
    }
    public float getStateTime(){
        return stateTime;

    }
    public void setStateTime(float dt){
        stateTime+=dt;

    }
    public TextureRegion getAnimation(float stateTime){
        if(used){
            return frames.get(4);
        }else{
            return (TextureRegion)portalAnimation.getKeyFrame(stateTime,true);
        }


    }
    public void dispose(){
        for (int i = 0; i <5 ; i++) {
            frames.get(i).getTexture().dispose();
        }
    }
}
