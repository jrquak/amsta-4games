package com.amsta.puzzel.manager;

import com.badlogic.gdx.Gdx;

/**
 * Created by Yorick on 16-03-17.
 */
public class InputManager {
    private static InputManager instance;

    private boolean isTouched;
    private boolean isTouchedPrev;

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static InputManager getInstance()
    {
        if(instance == null)
        {
            instance = new InputManager();
        }
        return instance;
    }

    private InputManager()
    {

    }

    /**
     * Update.
     */
    public void update()
    {
        isTouchedPrev = isTouched;
        isTouched = Gdx.input.isTouched();
    }

    /**
     * Is just pressed boolean.
     *
     * @return the boolean
     */
    public boolean isJustPressed()
    {
        return !isTouchedPrev && isTouched;
    }

    /**
     * Is released this frame boolean.
     *
     * @return the boolean
     */
    public boolean isReleasedThisFrame()
    {
        return isTouchedPrev && !isTouched;
    }

    /**
     * Is down boolean.
     *
     * @return the boolean
     */
    public boolean isDown()
    {
        return isTouched;
    }
}
