package com.alexzuzow.capturetheflagapp.Buffers;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class BufferPlayerUpdate {
    public Integer clientID;
    public float xCoord;
    public float yCoord;
    public short playerCategoryBit;
    public short carrierCategoryBit;
    public boolean hasFlag;
    public boolean alive;
    public long respawnTimer;
    public Vector2 previousLocation;
    public boolean setToDestroy;
    public boolean destroyed;
    public Vector2 linearVelocity;
}
