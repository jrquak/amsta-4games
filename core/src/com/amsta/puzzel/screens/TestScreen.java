package com.amsta.puzzel.screens;

import com.amsta.puzzel.components.Buttons.Button;
import com.amsta.puzzel.components.particle.ParticleEmmiter;
import com.amsta.puzzel.events.ButtonClickedEvent;
import com.amsta.puzzel.interfaces.IButtonPressed;
import com.amsta.puzzel.manager.ScreenManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Yorick on 16-03-17.
 */
public class TestScreen extends BaseScreen {
    /**
     * The Tex.
     */
    Texture tex = new Texture("badlogic.jpg");
    /**
     * The B.
     */
    Button b;

    /**
     * The Emitter left.
     */
    ParticleEmmiter emitterLeft;
    /**
     * The Emitter right.
     */
    ParticleEmmiter emitterRight;


    @Override
    public boolean initialize() {
        b = new Button(tex, new Vector2(200, 200));
        b.setListner(new IButtonPressed() {
            @Override
            public void buttonPressedHandler(ButtonClickedEvent event) {
                ScreenManager.getInstance().goBackOneScreen();
            }
        });

        emitterLeft = new ParticleEmmiter();
        emitterLeft.setEmitterPos(new Vector2(150, 400));
        emitterRight = new ParticleEmmiter();
        emitterRight.setEmitterPos(new Vector2(450, 400));
        return true;
    }

    @Override
    public void update() {
        emitterLeft.update();
        emitterRight.update();
    }

    @Override
    public void draw(SpriteBatch batch) {
        emitterLeft.draw(batch);
        emitterRight.draw(batch);
    }

    @Override
    public void exitScreen() {

    }

    @Override
    public void dispose() {
        tex.dispose();
    }
}
