package com.alexzuzow.capturetheflagapp.Sprites;


import com.alexzuzow.capturetheflagapp.CaptureTheFlagApp;
import com.alexzuzow.capturetheflagapp.Screens.GameScreen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;


public class User extends Character {
    private int clinetID;
    private float xCoord;
    private float yCoord;
    private boolean hasFlag;
    private boolean alive;
    private long respawnTimer;
    private Vector2 previousLocation;
    private boolean setToDestroy;
    private boolean destroyed;
    private Texture playerSprite;
    private boolean isRedTeam;
    private boolean isBlueTeam;
    private boolean usedPowerUp;
    private long powerUpTimer;
    private boolean powerUpPop;
    private boolean powerupShield;
    private boolean powerUpSpeed;
    private long portalCooldown;
    private boolean setToTeleport;
    private float teleportX;
    private float teleportY;


    public User(GameScreen screen, float xCoord, float yCoord, Texture playerSprite,boolean isRedTeam,boolean isBlueTeam) {
        super(screen, xCoord, yCoord, playerSprite);
        this.playerSprite = playerSprite;
        this.isRedTeam=isRedTeam;
        this.isBlueTeam=isBlueTeam;
        respawnTimer = 0;
        portalCooldown= 0;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        alive = true;
        hasFlag = false;
        usedPowerUp=false;
        setToTeleport=false;
        teleportX=0;
        teleportY=0;
        definePlayer();
    }

    public void update(float x, float y) {
        if(setToTeleport){
//            System.out.println(x);
//            System.out.println(y);
            setBodyPosition(teleportX,teleportY);
            setToTeleport=false;
        }

        if(usedPowerUp) {
            long currentTime = System.currentTimeMillis();
            if ((currentTime - powerUpTimer) / 1000 >= 60) {

                usedPowerUp=false;
            }
        }
        if (setToDestroy && !destroyed) {
            world.destroyBody(b2Body);
            destroyed = true;

        } else if (!destroyed) {

            setPosition(b2Body.getPosition().x - .01f - getWidth() / 2, b2Body.getPosition().y - getHeight() / 2);
            if (!alive) {
                long currentTime = System.currentTimeMillis();
                if ((currentTime - respawnTimer) / 1000 >= 5) {
                    spawnPlayer(x, y);
                }
            }
        }

    }


    @Override
    public void onPlayerContact() {
        if(powerupShield){
            //power up off and push that person away
        }else {
            respawnTimer = System.currentTimeMillis();
            alive = false;
//        System.out.println("DEAD");
            Filter filter = new Filter();
            filter.categoryBits = CaptureTheFlagApp.USED_BIT;
            fixture.setFilterData(filter);
        }
    }

    @Override
    public void onFlagPickup() {
        hasFlag = true;

    }

    @Override
    public void onFlagCapture() {
        hasFlag = false;

    }

    @Override
    public void onSpikeContact() {
        respawnTimer = System.currentTimeMillis();
        alive = false;
//        System.out.println("DEAD");
        Filter filter = new Filter();
        filter.categoryBits = CaptureTheFlagApp.USED_BIT;
        fixture.setFilterData(filter);

    }
@Override
public void onLavaContact(){
    respawnTimer = System.currentTimeMillis();
    alive = false;
//    System.out.println("DEAD");
    Filter filter = new Filter();
    filter.categoryBits = CaptureTheFlagApp.USED_BIT;
    fixture.setFilterData(filter);
}



    public void definePlayer() {
        setToDestroy = false;
        destroyed = false;
        BodyDef bdef = new BodyDef();
        bdef.position.set(xCoord / CaptureTheFlagApp.PPM, yCoord / CaptureTheFlagApp.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        setBounds(0, 0, 40f / CaptureTheFlagApp.PPM, 40f / CaptureTheFlagApp.PPM);
//        setRegion(playerBody);
        b2Body = world.createBody(bdef);
        b2Body.setLinearDamping(0.5f);
        b2Body.setAngularDamping(1f);
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(20 / CaptureTheFlagApp.PPM);
        fdef.shape = shape;
//        fdef.density=10;
        fdef.filter.categoryBits = CaptureTheFlagApp.PLAYER_BIT;


        fdef.filter.maskBits = CaptureTheFlagApp.INTERACTIVE_ITEM_BIT | CaptureTheFlagApp.WALL_BIT |
                    CaptureTheFlagApp.SPAWN_BIT | CaptureTheFlagApp.GOAL_BIT |
                    CaptureTheFlagApp.PLAYER_BIT |
                    CaptureTheFlagApp.TRAP_BIT;

        fixture = b2Body.createFixture(fdef);
        fixture.setUserData(this);
        previousLocation = new Vector2(getX(), getY());
    }

    public float getFlagSpriteX() {
        return b2Body.getPosition().x - getWidth() / 2;
    }

    public float getFlagSpriteY() {
        return b2Body.getPosition().y - getHeight() / 2;
    }
    @Override
    public boolean hasFlag() {
        return hasFlag;
    }

    public boolean isAlive() {
        return alive;
    }

    public void spawnPlayer(float x, float y) {
        alive = true;
        b2Body.setTransform(x, y, 0);
        Filter filter = new Filter();
        filter.categoryBits = CaptureTheFlagApp.PLAYER_BIT;
        fixture.setFilterData(filter);

    }

    public void resetFlag() {
        hasFlag = false;
    }

    public boolean hasMoved() {
        if (previousLocation.x != b2Body.getPosition().x || previousLocation.y != b2Body.getPosition().y) {
            previousLocation.x = b2Body.getPosition().x;
            previousLocation.y = b2Body.getPosition().y;
            xCoord=previousLocation.x;
            yCoord=previousLocation.y;
            return true;
        }
        return false;
    }


    public boolean isSetToDestroy(){
        return setToDestroy;
    }
    public boolean isDestroyed() {
        return destroyed;
    }

    public int getClinetID() {
        return clinetID;
    }

    public float getxCoord() {
        return xCoord;
    }

    public float getyCoord() {
        return yCoord;
    }

    public boolean getHasFlag() {
        return hasFlag;
    }

    public long getRespawnTimer() {
        return respawnTimer;
    }

    public Vector2 getPreviousLocation() {
        return previousLocation;
    }

    public void dispose() {
        playerSprite.dispose();
    }

    public void setBodyPosition(float x, float y){
        b2Body.setTransform(x,y,0);
    }
    public  String getName(){
        return name;
    }
    public void setClinetID(int clinetID) {
        this.clinetID = clinetID;
    }

    public void setxCoord(float xCoord) {
        this.xCoord = xCoord;
    }

    public void setyCoord(float yCoord) {
        this.yCoord = yCoord;
    }
    public void setHasFlag(boolean hasFlag) {
        this.hasFlag = hasFlag;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setRespawnTimer(long respawnTimer) {
        this.respawnTimer = respawnTimer;
    }

    public void setPreviousLocation(Vector2 previousLocation) {
        this.previousLocation = previousLocation;
    }

    public void setSetToDestroy(boolean setToDestroy) {
        this.setToDestroy = setToDestroy;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }
    @Override
    public boolean isBlueTeam(){
        return isBlueTeam;
    }
    public boolean isRedTeam(){
        return isRedTeam;
    }
    public void setRedTeam(boolean isRedTeam){
        this.isRedTeam=isRedTeam;
    }
    public void setBlueTeam(boolean isBlueTeam){
        this.isBlueTeam=isBlueTeam;
    }
    public boolean hasUsedPowerUp(){
        return usedPowerUp;
    }
    public void setPowerUp(int powerUp){
        //0 = none, 1 = speed , 2 = pop ,3 = shield
        usedPowerUp=true;
        powerUpTimer = System.currentTimeMillis();
        if (powerUp == 1) {
            powerUpPop=false;
            powerupShield=false;
            powerUpSpeed=true;
        } else if (powerUp == 2) {
            powerUpPop=true;
            powerupShield=false;
            powerUpSpeed=false;
        } else if (powerUp == 3) {
            powerUpPop=false;
            powerupShield=true;
            powerUpSpeed=false;

        }
    }
    public boolean hasPowerUpSpeed(){
        return powerUpSpeed;
    }
    public boolean hasPowerUpPop(){
        return powerUpPop;
    }
    public boolean hasPowerUpSheild(){
        return powerupShield;
    }
    public long getPortalCooldown(){
        return portalCooldown;
    }
    public void setPortalCooldown(Long portalCooldown){
        this.portalCooldown=portalCooldown;
    }
    public void setSetToTeleport(float x,float y){
        teleportX=x;
        teleportY=y;
        this.setToTeleport=true;
    }



}

