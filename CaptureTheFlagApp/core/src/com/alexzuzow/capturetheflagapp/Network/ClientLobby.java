package com.alexzuzow.capturetheflagapp.Network;

import com.alexzuzow.capturetheflagapp.Listeners.ClientLobbyListener;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.esotericsoftware.kryonet.Client;



public class ClientLobby extends Stage {
    private TextField portTextField, ipTextField;
    private Label titleLabel, ipLabel;
    private TextButton textButton;
    private Client client;
    private ClientLobbyListener clientLobbyListener;
    //UNDO COMMENT
//    private final MainMenuScreen mainMenu;
//    private final Skin skin;
    FileHandle h;
    }

