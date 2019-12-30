package com.alexzuzow.capturetheflagapp.Listeners;

import com.alexzuzow.capturetheflagapp.Buffers.BufferAddExistingUsers;
import com.alexzuzow.capturetheflagapp.Buffers.BufferAddNewUser;
import com.alexzuzow.capturetheflagapp.Buffers.BufferDisconectPlayer;
import com.alexzuzow.capturetheflagapp.Buffers.BufferPlayerConnect;
import com.alexzuzow.capturetheflagapp.Buffers.BufferPlayerUpdate;
import com.alexzuzow.capturetheflagapp.Network.ServerGame;
import com.alexzuzow.capturetheflagapp.Sprites.User;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.util.HashMap;


public class ServerGameListener extends Listener {
    ServerGame server;

    public ServerGameListener(ServerGame server) {
        this.server = server;
    }

    @Override
    public void connected(Connection c) {

    }

    @Override
    public void disconnected(Connection c) {
        System.out.println(c.getID() + " Disconnected From server ");
        server.getUserList().remove((c.getID()));
        BufferDisconectPlayer disconectPlayer = new BufferDisconectPlayer();
        disconectPlayer.id=c.getID();
        server.server.sendToAllTCP(disconectPlayer);

    }

    public void received(Connection c, Object o) {

        if (o instanceof BufferPlayerConnect) {
            System.out.println("BUFFER PLAYER CONNECT");
            BufferPlayerConnect connectedPlayer = (BufferPlayerConnect) o;
            //get each player already connected to the server

            for (HashMap.Entry<Integer, BufferAddNewUser> entry : server.getUserList().entrySet()) {
                BufferAddNewUser newPlayer = new BufferAddNewUser();
                newPlayer.clientID = entry.getValue().clientID;
                newPlayer.xCoord = entry.getValue().xCoord;
                newPlayer.yCoord = entry.getValue().yCoord;
                newPlayer.isRedTeam = entry.getValue().isRedTeam;
                newPlayer.isBlueTeam = entry.getValue().isBlueTeam;
                newPlayer.hasFlag = entry.getValue().hasFlag;
                newPlayer.alive = entry.getValue().alive;
                newPlayer.respawnTimer = entry.getValue().respawnTimer;
                newPlayer.previousLocation = entry.getValue().previousLocation;
                newPlayer.setToDestroy = entry.getValue().setToDestroy;
                newPlayer.destroyed = entry.getValue().destroyed;
                c.sendTCP(newPlayer);
                System.out.println("sending " + connectedPlayer.clientID + " Client Info of " + newPlayer.clientID);
            }
        }
        if (o instanceof BufferAddNewUser) {
            System.out.println("SERVER BUFFER ADD USER ");
            BufferAddNewUser newUser = ((BufferAddNewUser) o);
            System.out.println("sending everyone new player: " + newUser.clientID);
            server.server.sendToAllTCP(newUser);

        }
        if (o instanceof BufferPlayerUpdate) {
            BufferPlayerUpdate updatePlayer = ((BufferPlayerUpdate) o);
            BufferAddNewUser updateServer = new BufferAddNewUser();
            updateServer.clientID = updatePlayer.clientID;
            updateServer.xCoord = updatePlayer.xCoord;
            updateServer.yCoord = updatePlayer.yCoord;
            updateServer.isRedTeam = updatePlayer.isRedTeam;
            updateServer.isBlueTeam = updatePlayer.isBlueTeam;
            updateServer.hasFlag = updatePlayer.hasFlag;
            updateServer.alive = updatePlayer.alive;
            updateServer.respawnTimer = updatePlayer.respawnTimer;
            updateServer.previousLocation = updatePlayer.previousLocation;
            updateServer.setToDestroy = updatePlayer.setToDestroy;
            updateServer.destroyed = updatePlayer.destroyed;
            server.getUserList().put(updatePlayer.clientID, updateServer);
            server.server.sendToAllExceptTCP(updatePlayer.clientID, updatePlayer);
        }
    }
}
