package com.amsta.puzzel.manager;

import com.amsta.puzzel.screens.BaseScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

/**
 * Created by Yorick on 16-03-17.
 */
public class ScreenManager {
    private static ScreenManager instance;

    private Stack<BaseScreen> screens;

    private BaseScreen screenToLoad;
    private boolean goBack;
    private boolean goToMainMenu;

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static ScreenManager getInstance()
    {
        if(instance == null)
        {
            instance = new ScreenManager();
        }
        return instance;
    }

    /**
     * private constructor. This class can only create it self with the help of getInstance.
     * Only one object can exist at a time
     */
    private ScreenManager()
    {
        screens = new Stack<BaseScreen>();
    }

    /**
     * Update.
     */
    public void update()
    {
        // If go back was called after last update
        if(goBack)
        {
            if(screens.size() > 1)
            {
                screens.peek().exitScreen();
                screens.pop();
                screens.peek().show();
                screens.peek().resume();
            }
            goBack = false;
        }

        if(goToMainMenu)
        {
            while(screens.size() > 1)
            {
                // Call exit screen for all screens on stack
                screens.peek().exitScreen();
                // Remove them from stack
                screens.pop();
            }
            //show the last screen
            screens.peek().show();
            //resume the last screen
            screens.peek().resume();

            goToMainMenu = false;
        }

        // if a new screen needs to be loaded since last update
        if(screenToLoad != null)
        {
            screenToLoad.initialize();
            if(screens.size() > 0) {
                screens.peek().pause();
                screens.peek().hide();
            }
            screens.add(screenToLoad);
            screenToLoad = null;
        }

        // Update all the screens
        for (BaseScreen screen: screens) {
            if(screen.isUpdating())
                screen.update();
        }
    }

    /**
     * Draw.
     *
     * @param batch the batch
     */
    public void draw(SpriteBatch batch)
    {
        for (BaseScreen screen: screens) {
            if(screen.isShowing())
                screen.draw(batch);
        }
    }

    /**
     * Change screen.
     *
     * @param screen the screen
     */
    public void changeScreen(BaseScreen screen)
    {
        screenToLoad = screen;
    }

    /**
     * Go back one screen.
     */
    public void goBackOneScreen()
    {
        goBack = true;
    }

    /**
     * Get the amount of screens on the stack
     *
     * @return The amount of screens on the stack
     */
    public int getScreenStackSize()
    {
        return screens.size();
    }

    /**
     * Go back to the main menu/first screen on the stack
     */
    public void goToMainMenu()
    {
        goToMainMenu = true;
    }

    /**
     * Dispose.
     */
    public void dispose() {
        for (BaseScreen screen : screens) {
            screen.dispose();
        }
    }
}
