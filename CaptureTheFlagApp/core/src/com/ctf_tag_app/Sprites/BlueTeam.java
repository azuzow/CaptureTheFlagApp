package com.ctf_tag_app.Sprites;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.ctf_tag_app.CaptureTheFlagApp;
import com.ctf_tag_app.Screens.GameScreen;

public class BlueTeam extends Player1 {



    public BlueTeam(GameScreen screen, float xCoord, float yCoord,short playerCategoryBit,short carrierCategoryBit,String playerSprite) {
        super(screen, xCoord, yCoord,playerSprite);
//        SpriteTexture = new Texture(Gdx.files.internal(playerSprite));
        respawnTimer=0;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.playerCategoryBit=playerCategoryBit;
        this.carrierCategoryBit=carrierCategoryBit;
        alive=true;
        hasFlag=false;
//        this.textureRegionY=textureRegionY;
        definePlayer();
    }
    public void update(float x,float y){
//        fixture.getBody().applyAngularImpulse(5f,true);
//        setRotation(fixture.getBody().getAngle());
//        if(fixture.getBody().getAngularVelocity()>5f){
//            fixture.getBody().setAngularVelocity(5);
//        }
        setPosition(b2Body.getPosition().x-.01f-getWidth()/2,b2Body.getPosition().y-getHeight()/2);
        if(!alive){
            long currentTime = System.currentTimeMillis();
            if((currentTime-respawnTimer)/1000>=5){
                spawnPlayer(x,y);
            }
        }
    }


    @Override
    public void onPlayerContact() {
        respawnTimer = System.currentTimeMillis();
        alive=false;
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
        hasFlag=false;
        Filter filter = new Filter();
        filter.categoryBits = playerCategoryBit;
        fixture.setFilterData(filter);

    }

    @Override
    public void onSpikeContact() {
        respawnTimer = System.currentTimeMillis();
        alive=false;
        System.out.println("DEAD");
        Filter filter = new Filter();
        filter.categoryBits = CaptureTheFlagApp.USED_BIT;
        fixture.setFilterData(filter);

    }
    public void definePlayer() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(xCoord / CaptureTheFlagApp.PPM, yCoord / CaptureTheFlagApp.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
//        playerBody = new TextureRegion(getTexture(),0,0,40,40);
        setBounds(0,0,40f/ CaptureTheFlagApp.PPM,40f/CaptureTheFlagApp.PPM);
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
        fdef.filter.maskBits = CaptureTheFlagApp.RED_FLAG_BIT | CaptureTheFlagApp.WALL_BIT |
                CaptureTheFlagApp.BOMB_BIT | CaptureTheFlagApp.SPIKE_BIT |
                CaptureTheFlagApp.BUTTON_BIT | CaptureTheFlagApp.RED_PLAYER_BIT |
                CaptureTheFlagApp.BLUE_PLAYER_BIT |CaptureTheFlagApp.RED_FLAG_CARRIER |
                CaptureTheFlagApp.BLUE_FLAG_CARRIER | CaptureTheFlagApp.BLUE_FLAG_BIT |
                CaptureTheFlagApp.SPAWN_BIT;

        fixture = b2Body.createFixture(fdef);
        fixture.setUserData(this);

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
    public boolean isAlive(){
        return alive;
    }

    public void spawnPlayer(float x,float y) {
        alive=true;
        b2Body.setTransform(x,y,0);
        Filter filter = new Filter();
        filter.categoryBits = playerCategoryBit;
        fixture.setFilterData(filter);

    }
    public void resetHasFlag(){
        hasFlag=false;
    }


}
