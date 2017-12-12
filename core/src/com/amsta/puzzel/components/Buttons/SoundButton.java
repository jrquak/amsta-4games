package com.amsta.puzzel.components.Buttons;

import com.amsta.puzzel.manager.InputManager;
import com.amsta.puzzel.manager.SettingManager;
import com.amsta.puzzel.manager.SoundManager;
import com.amsta.puzzel.util.ScreenUtil;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Yorick on 04-04-17.
 */

/***
 * Button to manage the sound
 */
public class SoundButton extends Button {
    private Texture imgOff;

    /**
     * Constructor of the button
     *
     * @param imgOn  The image to show when sound is on
     * @param imgOff The image to show when sound is off
     * @param pos    The position of the button
     */
    public SoundButton(Texture imgOn, Texture imgOff, Vector2 pos) {
        super(imgOn, pos);
        this.imgOff = imgOff;
    }

    @Override
    public void update()
    {
        if(InputManager.getInstance().isJustPressed())
        {
            Vector2 pos = ScreenUtil.touchToScreenPos();

            if(boundingBox.contains(pos.x, pos.y) && alreadyFired == false)
            {
                SoundManager.toggleSound();
            }
        }
        else
        {
            alreadyFired = false;
        }
    }

    @Override
    public void draw(SpriteBatch batch)
    {
        if(SoundManager.getInstance().isSoundEnabled())
        {
            batch.draw(img, pos.x, pos.y);
        }
        else
        {
            batch.draw(imgOff, pos.x, pos.y);
        }
    }
}
