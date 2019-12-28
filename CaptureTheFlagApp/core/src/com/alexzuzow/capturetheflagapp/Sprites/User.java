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
    private short playerCategoryBit;
    private short carrierCategoryBit;
    private boolean hasFlag;
    private boolean alive;
    private long respawnTimer;
    private Vector2 previousLocation;
    private boolean setToDestroy;
    private boolean destroyed;
    private Texture playerSprite;


    public User(GameScreen screen, float xCoord, float yCoord, short playerCategoryBit, short carrierCategoryBit, Texture playerSprite) {
        super(screen, xCoord, yCoord, playerSprite);
        this.playerSprite = playerSprite;
        respawnTimer = 0;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.playerCategoryBit = playerCategoryBit;
        this.carrierCategoryBit = carrierCategoryBit;
        alive = true;
        hasFlag = false;

        definePlayer();
    }

    public void update(float x, float y) {
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
        respawnTimer = System.currentTimeMillis();
        alive = false;
        System.out.println("DEAD");
        Filter filter = new Filter();
        filter.categoryBits = CaptureTheFlagApp.USED_BIT;
        fixture.setFilterData(filter);

    }

    @Override
    public void onFlagPickup() {
        hasFlag = true;
        Filter filter = new Filter();
        filter.categoryBits = carrierCategoryBit;
        fixture.setFilterData(filter);

    }

    @Override
    public void onFlagCapture() {
        hasFlag = false;
        Filter filter = new Filter();
        filter.categoryBits = playerCategoryBit;
        fixture.setFilterData(filter);

    }

    @Override
    public void onSpikeContact() {
        respawnTimer = System.currentTimeMillis();
        alive = false;
        System.out.println("DEAD");
        Filter filter = new Filter();
        filter.categoryBits = CaptureTheFlagApp.USED_BIT;
        fixture.setFilterData(filter);

    }
@Override
public void onLavaContact(){
    respawnTimer = System.currentTimeMillis();
    alive = false;
    System.out.println("DEAD");
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
        fdef.filter.categoryBits = playerCategoryBit;
        if(playerCategoryBit==2) {
            fdef.filter.maskBits = CaptureTheFlagApp.RED_FLAG_BIT | CaptureTheFlagApp.WALL_BIT |
                    CaptureTheFlagApp.BOMB_BIT | CaptureTheFlagApp.SPIKE_BIT |
                    CaptureTheFlagApp.BUTTON_BIT | CaptureTheFlagApp.RED_PLAYER_BIT |
                    CaptureTheFlagApp.BLUE_PLAYER_BIT | CaptureTheFlagApp.RED_FLAG_CARRIER |
                    CaptureTheFlagApp.BLUE_FLAG_CARRIER | CaptureTheFlagApp.BLUE_FLAG_BIT |
                    CaptureTheFlagApp.SPAWN_BIT | CaptureTheFlagApp.RED_TRAP_BIT;
        }
        else{
            fdef.filter.maskBits = CaptureTheFlagApp.RED_FLAG_BIT | CaptureTheFlagApp.WALL_BIT |
                    CaptureTheFlagApp.BOMB_BIT | CaptureTheFlagApp.SPIKE_BIT |
                    CaptureTheFlagApp.BUTTON_BIT | CaptureTheFlagApp.RED_PLAYER_BIT |
                    CaptureTheFlagApp.BLUE_PLAYER_BIT | CaptureTheFlagApp.RED_FLAG_CARRIER |
                    CaptureTheFlagApp.BLUE_FLAG_CARRIER | CaptureTheFlagApp.BLUE_FLAG_BIT |
                    CaptureTheFlagApp.SPAWN_BIT | CaptureTheFlagApp.BLUE_TRAP_BIT;

        }

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
        filter.categoryBits = playerCategoryBit;
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

    public short getPlayerBit() {
        return playerCategoryBit;
    }

    public void disconnected() {
        System.out.println("Queued to be destroyed");
        setToDestroy = true;
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
    public short getPlayerCategoryBit(){
        return playerCategoryBit;
    }
    public short getCarrierCategoryBit(){
        return carrierCategoryBit;
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

    public void setPlayerCategoryBit(short playerCategoryBit) {
        this.playerCategoryBit = playerCategoryBit;
    }

    public void setCarrierCategoryBit(short carrierCategoryBit) {
        this.carrierCategoryBit = carrierCategoryBit;
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



}

