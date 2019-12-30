package com.alexzuzow.capturetheflagapp.Listeners;



import com.alexzuzow.capturetheflagapp.Network.ServerLobby;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;


public class ServerLobbyListener extends Listener {

    private ServerLobby server;

    public ServerLobbyListener(ServerLobby server) {
        this.server = server;
    }

    @Override
    public void connected(Connection connection) {
        server.connect();
        super.connected(connection);
    }

    @Override
    public void disconnected(Connection connection) {
        server.disconnect();
        super.disconnected(connection);
    }
}

// currently trying to finish making server lobby work and connect to server lobby