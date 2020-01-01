package com.alexzuzow.capturetheflagapp.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Joystick{
    private Touchpad touchpad;
    private static Touchpad.TouchpadStyle touchpadStyle;
    private Skin touchpadSkin;
    //probably takes stage or table
    public Joystick(Table gui) {
        touchpadSkin = new Skin();
        touchpadSkin.add("touchKnob", new Texture("touchKnob.png"));

        touchpadStyle = new Touchpad.TouchpadStyle();
        touchpadStyle.knob = touchpadSkin.getDrawable("touchKnob");
        touchpadStyle.knob.setMinWidth(16);
        touchpadStyle.knob.setMinHeight(16);
        Pixmap background = new Pixmap(300, 300, Pixmap.Format.RGBA8888);
        background.setBlending(Pixmap.Blending.None);
        background.setColor(1, 1, 1, .6f);
        background.fillCircle(150, 150, 150);

        touchpadStyle.background = new TextureRegionDrawable(new TextureRegion(new Texture(background)));

        touchpad = new Touchpad(5, touchpadStyle);
        gui.add(touchpad).bottom().left().pad(0f, 700f, 100f, 0f).size(Value.percentWidth(0.15f)).expandX();

    }
    public Touchpad getTouchpad(){
        return touchpad;

    }
    public void Dispose(){
        touchpadSkin.dispose();
    }
}