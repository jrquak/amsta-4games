package com.amsta.puzzel.screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Yorick on 16-03-17.
 */
public abstract class BaseScreen {
    /**
     * Instantiates a new Base screen.
     */
    public BaseScreen()
    {
        isShowing = true;
        isUpdating = true;
    }

    /**
     * Is the screen showing.
     */
    protected boolean isShowing;

    /**
     * Is the screen updating.
     */
    protected boolean isUpdating;

    /**
     * Initialize the screen.
     *
     * @return if initialize succeded or not
     */
    public abstract boolean initialize();

    /**
     * Update the screen.
     */
    public abstract void update();

    /**
     * Draw the screen.
     *
     * @param batch the batch
     */
    public abstract  void draw(SpriteBatch batch);

    /**
     * Show the screen.
     */
    public void show()
    {
        isShowing = true;
    }

    /**
     * Hide the screen.
     */
    public void hide()
    {
        isShowing = false;
    }

    /**
     * Pause the screen.
     */
    public  void pause()
    {
        isUpdating = false;
    }

    /**
     * Resume the screen.
     */
    public void resume()
    {
        isUpdating = true;
    }

    /**
     * Is showing boolean.
     *
     * @return the boolean
     */
    public boolean isShowing()
    {
        return isShowing;
    }

    /**
     * Is updating boolean.
     *
     * @return the boolean
     */
    public boolean isUpdating()
    {
        return isUpdating;
    }


    /**
     * Will be callled when the screen will become inactive or clossed.
     * Could be used to save data
     */
    public abstract void exitScreen();

    /**
     * Dispose.
     */
    public abstract void dispose();
}
