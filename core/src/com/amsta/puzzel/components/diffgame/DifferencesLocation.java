package com.amsta.puzzel.components.diffgame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Yorick on 21-03-17.
 */

/**
 * Holds info about where the difference location i
 */
public class DifferencesLocation {
    private boolean found;
    private Vector2 location;
    private Texture tex;

    // The radius around the finger the
    private final int CLICK_RADIUS = 32;

    /**
     * The loaction of an diff
     *
     * @param location The location as vector2
     */
    public DifferencesLocation(Vector2 location)
    {
        tex = new Texture("diff/found.png");
        this.location = location.sub(tex.getWidth() / 2, tex.getHeight() / 2);
        found = false;
    }

    /**
     * Check if the location is clicked
     *
     * @param pos The position where the click is
     * @return If the click was on the location
     */
    public boolean click(Vector2 pos)
    {
        if(getCenter().dst(pos) < CLICK_RADIUS)
        {
            found = true;
            return true;
        }
        return false;
    }

    /**
     * Set if the diff is found
     *
     * @param fnd The val if it is found
     */
    public void setIsFound(boolean fnd)
    {
        this.found = fnd;
    }

    /**
     * Getter for found
     *
     * @return return if the diff is already found
     */
    public boolean isFound()
    {
        return found;
    }

    /**
     * Get the location of the diff
     *
     * @return The location of the diff;
     */
    public Vector2 getLocation()
    {
        return location;
    }

    /**
     * Get the center of the diff
     * @return The center of the diff
     */
    private Vector2 getCenter()
    {
        return new Vector2(location.x + tex.getWidth() / 2, location.y + tex.getHeight() / 2);
    }

    /**
     * Draw the diff it is found
     *
     * @param batch The batch to draw to
     */
    public void draw(SpriteBatch batch)
    {
        if(found) {
            batch.draw(tex, location.x, location.y);
        }
    }
}
