package com.alexzuzow.capturetheflagapp.Buffers;

import com.alexzuzow.capturetheflagapp.Sprites.User;
import com.badlogic.gdx.math.Vector2;

public class BufferAddExistingUsers {

    public Integer clientID;
    public float xCoord;
    public float yCoord;
    public boolean isRedTeam;
    public boolean isBlueTeam;
    public boolean hasFlag;
    public boolean alive;
    public long respawnTimer;
    public Vector2 previousLocation;
    public boolean setToDestroy;
    public boolean destroyed;

}
