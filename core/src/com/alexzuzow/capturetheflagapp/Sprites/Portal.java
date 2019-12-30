package com.alexzuzow.capturetheflagapp.Sprites;
import com.alexzuzow.capturetheflagapp.CaptureTheFlagApp;
import com.alexzuzow.capturetheflagapp.Screens.GameScreen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;


public class Portal extends InteractiveTileObject{
    private Portal pair;
    private Animation portalAnimation;
    private float stateTime;
    private Array<TextureRegion> frames;
    public static final float ANIMATION_SPEED=0.3f;
    public Portal(GameScreen screen, Rectangle rectangleBounds, Ellipse ellipseBounds) {
        super(screen, rectangleBounds, ellipseBounds, null);
        stateTime=0;
        frames = new Array<TextureRegion>();
        for (int i = 0; i <5 ; i++) {
            frames.add(new TextureRegion(new Texture("portal.png"),i*40,0,40,40));
        }

        portalAnimation=new Animation(ANIMATION_SPEED,frames);
        fixture.setUserData(this);
        setCategoryFilter(CaptureTheFlagApp.INTERACTIVE_ITEM_BIT);
    }
    @Override
    public void onContact(String player) {

    }
    @Override
    public void onContact(Object player) {
        User character = (User)player;
        long currentTime = System.currentTimeMillis();
        if ((currentTime - character.getPortalCooldown()) / 1000 >= 1) {
            character.setPortalCooldown(currentTime);
            character.setSetToTeleport(pair.getX(),pair.getY());
            System.out.println("teleporting");
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
    public void setPair(Portal pair){
        this.pair=pair;
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
        return (TextureRegion)portalAnimation.getKeyFrame(stateTime,true);
    }
    public void dispose(){
        for (int i = 0; i <5 ; i++) {
            frames.get(i).getTexture().dispose();
        }
    }
}
