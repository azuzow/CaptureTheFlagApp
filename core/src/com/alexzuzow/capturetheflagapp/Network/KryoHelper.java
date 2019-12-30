package com.alexzuzow.capturetheflagapp.Network;

import com.alexzuzow.capturetheflagapp.Buffers.BufferAddExistingUsers;
import com.alexzuzow.capturetheflagapp.Buffers.BufferDisconectPlayer;
import com.alexzuzow.capturetheflagapp.Buffers.BufferPlayerConnect;
import com.alexzuzow.capturetheflagapp.Buffers.BufferPlayerMove;
import com.alexzuzow.capturetheflagapp.Buffers.BufferPlayerUpdate;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

public class KryoHelper {

    static public void registerClasses(EndPoint endPoint){
        Kryo kryo = endPoint.getKryo();
        kryo.register(BufferPlayerConnect.class);
        kryo.register(BufferPlayerMove.class);
        kryo.register(BufferDisconectPlayer.class);
        kryo.register(BufferAddExistingUsers.class);
        kryo.register(BufferPlayerUpdate.class);
        kryo.register(com.alexzuzow.capturetheflagapp.Sprites.User.class);
        kryo.register(com.badlogic.gdx.graphics.Color.class);
        kryo.register(float[].class);
        kryo.register(com.badlogic.gdx.physics.box2d.World.class);
        kryo.register(Integer.class);
        kryo.register(com.badlogic.gdx.physics.box2d.Body.class);
        kryo.register(com.badlogic.gdx.utils.Array.class);
        kryo.register(Object[].class);
        kryo.register(com.badlogic.gdx.physics.box2d.Fixture.class);
        kryo.register(com.badlogic.gdx.math.Vector2.class);
        kryo.register(com.alexzuzow.capturetheflagapp.Buffers.BufferAddNewUser.class);


    }
}
