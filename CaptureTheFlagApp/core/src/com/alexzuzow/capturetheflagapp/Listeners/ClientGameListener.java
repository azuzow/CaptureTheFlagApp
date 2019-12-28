package com.alexzuzow.capturetheflagapp.Listeners;

import com.alexzuzow.capturetheflagapp.Buffers.BufferAddExistingUsers;
import com.alexzuzow.capturetheflagapp.Buffers.BufferAddNewUser;
import com.alexzuzow.capturetheflagapp.Buffers.BufferDisconectPlayer;
import com.alexzuzow.capturetheflagapp.Buffers.BufferPlayerConnect;
import com.alexzuzow.capturetheflagapp.Buffers.BufferPlayerUpdate;
import com.alexzuzow.capturetheflagapp.CaptureTheFlagApp;
import com.alexzuzow.capturetheflagapp.Sprites.User;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.nio.Buffer;

public class ClientGameListener extends Listener {
    private Client client;

    public void init(Client client) {
        this.client = client;
    }

    @Override
    public void connected(Connection c) {

        BufferPlayerConnect startConnection = new BufferPlayerConnect();
        startConnection.clientID = c.getID();
//        CaptureTheFlagApp.playerList.put(CaptureTheFlagApp.clientID,null);
        System.out.println("sending object " + startConnection + "to server");

        client.sendTCP(startConnection);
    }

    @Override
    public void disconnected(Connection c) {
        System.out.println(c.getID() + " Closed application");
    }

    @Override
    public void received(Connection c, Object o) {
        if (o instanceof BufferAddNewUser) {

            BufferAddNewUser newPlayer = (BufferAddNewUser) o;

            if (!CaptureTheFlagApp.playerList.containsKey(newPlayer.clientID)) {

                if (newPlayer.playerCategoryBit == 2) {
                    CaptureTheFlagApp.blueTeamSize++;
                } else {
                    CaptureTheFlagApp.redTeamSize++;
                }

                CaptureTheFlagApp.newPlayers.add(newPlayer);
            }

        }
        if (o instanceof BufferPlayerUpdate) {
            BufferPlayerUpdate updatePlayer = ((BufferPlayerUpdate) o);

            if (CaptureTheFlagApp.playerList.containsKey(updatePlayer.clientID)) {

                CaptureTheFlagApp.playerList.get(updatePlayer.clientID).setClinetID(updatePlayer.clientID);
                CaptureTheFlagApp.playerList.get(updatePlayer.clientID).setxCoord(updatePlayer.xCoord);
                CaptureTheFlagApp.playerList.get(updatePlayer.clientID).setyCoord(updatePlayer.yCoord);
                CaptureTheFlagApp.playerList.get(updatePlayer.clientID).setPlayerCategoryBit(updatePlayer.playerCategoryBit);
                CaptureTheFlagApp.playerList.get(updatePlayer.clientID).setCarrierCategoryBit(updatePlayer.carrierCategoryBit);
                CaptureTheFlagApp.playerList.get(updatePlayer.clientID).setHasFlag(updatePlayer.hasFlag);
                CaptureTheFlagApp.playerList.get(updatePlayer.clientID).setAlive(updatePlayer.alive);
                CaptureTheFlagApp.playerList.get(updatePlayer.clientID).setRespawnTimer(updatePlayer.respawnTimer);
                CaptureTheFlagApp.playerList.get(updatePlayer.clientID).setPreviousLocation(updatePlayer.previousLocation);
                CaptureTheFlagApp.playerList.get(updatePlayer.clientID).setSetToDestroy(updatePlayer.setToDestroy);
                CaptureTheFlagApp.playerList.get(updatePlayer.clientID).setDestroyed(updatePlayer.destroyed);
                CaptureTheFlagApp.playerList.get(updatePlayer.clientID).b2Body.setLinearVelocity(updatePlayer.linearVelocity);

            }


        }
        if (o instanceof BufferDisconectPlayer) {
            System.out.println("OTHER PLAYER DISCONNECTED");
            BufferDisconectPlayer disconectPlayer = ((BufferDisconectPlayer) o);
            System.out.println("havent crashed yet");
            CaptureTheFlagApp.disconectedPlayers.add(CaptureTheFlagApp.playerList.get(disconectPlayer.id));
            CaptureTheFlagApp.playerList.get(disconectPlayer.id).setSetToDestroy(true);



        }
    }
}

