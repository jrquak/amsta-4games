package com.amsta.puzzel.components.diffgame;

import com.amsta.puzzel.manager.InputManager;
import com.amsta.puzzel.manager.SettingManager;
import com.amsta.puzzel.manager.SoundManager;
import com.amsta.puzzel.util.ScreenUtil;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Yorick on 21-03-17.
 */

/**
 * The pair of differences
 */
public class LocationPair {

    private DifferencesLocation loc1;
    private DifferencesLocation loc2;

    /**
     * Constructor
     *
     * @param loc1 Location 1 of the difference
     * @param loc2 Location 2 of the difference
     */
    public LocationPair(DifferencesLocation loc1, DifferencesLocation loc2)
    {
        this.loc1 = loc1;
        this.loc2 = loc2;
    }

    /**
     * Update the loactions
     */
    public void update()
    {
        boolean found = false;
        if(loc1.click(ScreenUtil.touchToScreenPos()) || loc2.click(ScreenUtil.touchToScreenPos()))
        {
            loc2.setIsFound(true);
            loc1.setIsFound(true);
        }
    }

    /**
     * Check if one of the diffs is found
     *
     * @return boolean boolean
     */
    public boolean isFound()
    {
        return loc1.isFound() || loc2.isFound();
    }

    /**
     * Draw the differences
     *
     * @param batch Batch to draw to
     */
    public void draw(SpriteBatch batch)
    {
        loc1.draw(batch);
        loc2.draw(batch);
    }
}
