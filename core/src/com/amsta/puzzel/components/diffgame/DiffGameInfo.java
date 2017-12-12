package com.amsta.puzzel.components.diffgame;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

/**
 * Created by Yorick on 28-03-17.
 */

/**
 * Holde the information about the diff image and the difference locations
 */
public class DiffGameInfo {
    /**
     * The Lvl name.
     */
    public String lvlName;
    /**
     * The File dir.
     */
    public String fileDir;
    /**
     * The Bases.
     */
    public ArrayList<Vector2> bases;
    /**
     * The Left right.
     */
    public boolean leftRight;

    /**
     * Instantiates a new Diff game info.
     */
    public DiffGameInfo()
    {
        bases = new ArrayList<Vector2>();
        leftRight = true;
    }

    /**
     * get the total amount of differences in this image
     *
     * @return the total amount of differences to be found
     */
    public int getTotalDiff()
    {
        return bases.size();
    }
}
