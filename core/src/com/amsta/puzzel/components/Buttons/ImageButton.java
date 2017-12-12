package com.amsta.puzzel.components.Buttons;

import com.amsta.puzzel.events.ButtonClickedEvent;
import com.amsta.puzzel.manager.InputManager;
import com.amsta.puzzel.util.GlobalVals;
import com.amsta.puzzel.util.ScreenUtil;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Yorick on 17-03-17.
 */

/**
 * Button with an image. image can be on the top or bottom.
 */
public class ImageButton extends TextButton {
    private boolean top;
    /**
     * The Desc texture.
     */
    protected Texture descTexture;
    /**
     * The Desc image bounds.
     */
    protected Rectangle descImageBounds;

    /**
     * Constructor for image button
     *
     * @param btnimg The image for the button
     * @param pos    The position for the button
     * @param text   Text on the button
     * @param image  Image under or above the button
     * @param top    If the image should be above the button
     */
    public ImageButton(Texture btnimg, Vector2 pos, String text, Texture image, boolean top) {
        super(btnimg, pos, text);
        descTexture = image;
        if(top)
            descImageBounds = new Rectangle(pos.x, pos.y + btnimg.getHeight(), descTexture.getWidth(), descTexture.getHeight());
        else
            descImageBounds = new Rectangle(pos.x, pos.y - descTexture.getHeight(), descTexture.getWidth(), descTexture.getHeight());
    }

    @Override
    public void update()
    {
        super.update();
        if(InputManager.getInstance().isJustPressed())
        {
            Vector2 pos = ScreenUtil.touchToScreenPos();

            if(descImageBounds.contains(pos.x, pos.y) && alreadyFired == false)
            {
                if(this.listner != null) {
                    listner.buttonPressedHandler(new ButtonClickedEvent(this));
                    alreadyFired = true;
                }
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
        batch.draw(descTexture, descImageBounds.getX(), descImageBounds.getY());
        super.draw(batch);
    }
}
