package com.amsta.puzzel.components.Buttons;

import com.amsta.puzzel.events.ButtonClickedEvent;
import com.amsta.puzzel.interfaces.IButtonPressed;
import com.amsta.puzzel.manager.InputManager;
import com.amsta.puzzel.util.ScreenUtil;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Yorick on 16-03-17.
 */

/**
 * Base class to represent a button in game
 */
public class Button {
    /**
     * The Listner.
     */
    protected IButtonPressed listner;
    /**
     * The Bounding box.
     */
    protected Rectangle boundingBox;
    /**
     * The Img.
     */
    protected Texture img;
    /**
     * The Pos.
     */
    protected Vector2 pos;
    /**
     * The Already fired.
     */
    protected boolean alreadyFired = false;

    /**
     * Constructor for the button
     *
     * @param img The image of the button
     * @param pos Position of the button
     */
    public Button(Texture img, Vector2 pos)
    {
        this.img = img;
        this.pos = pos;
        boundingBox = new Rectangle(pos.x, pos.y, img.getWidth(), img.getHeight());
    }

    /**
     * Update of the button, will check if the button is pressed
     */
    public void update()
    {
        if(InputManager.getInstance().isJustPressed())
        {
            Vector2 pos = ScreenUtil.touchToScreenPos();

            if(boundingBox.contains(pos.x, pos.y) && alreadyFired == false)
            {
                buttonPressed();
            }
        }
        else
        {
            alreadyFired = false;
        }
    }

    /**
     * Will be called when the button is pressed
     */
    public void buttonPressed()
    {
        if(this.listner != null) {
            listner.buttonPressedHandler(new ButtonClickedEvent(this));
            alreadyFired = true;
        }
    }

    /**
     * Draw the button
     *
     * @param batch Spritebatch to draw to
     */
    public void draw(SpriteBatch batch)
    {
        batch.draw(img, pos.x, pos.y);
    }

    /**
     * Set the listner for the button
     *
     * @param list listner that is listening to the button
     */
    public void setListner(IButtonPressed list)
    {
        listner = list;
    }
}
