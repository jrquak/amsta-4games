package com.amsta.puzzel.components.diffgame;

import com.amsta.puzzel.GdxStart;
import com.amsta.puzzel.manager.InputManager;
import com.amsta.puzzel.manager.SoundManager;
import com.amsta.puzzel.screens.DiffGame;
import com.amsta.puzzel.util.GlobalVals;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Yorick on 21-03-17.
 */

/**
 * Diff image with all info and abbility to scale
 */
public class DiffImage {
    private Texture top;
    private Texture bottom;

    private Vector2 posTop;
    private Vector2 posBottom;

    private boolean leftRight;

    private ArrayList<LocationPair> locations;

    private float scale = 1.0f;

    private int lastFound = 0;

    /**
     * Constructor that tries to load the images
     *
     * @param start The file name that needs to be loaded
     */
    public DiffImage(String start)
    {
        this.leftRight = leftRight;
        lastFound = 0;
        top = new Texture("diff/" + start + "/top.png");
        bottom = new Texture("diff/" + start + "/bottom.png");
        locations = new ArrayList<LocationPair>();

        loadPoints(start);
    }

    /**
     * Tries to get the best scale for the images
     */
    private void setScale()
    {
        if(leftRight)
        {
            scale = (float)(GdxStart.V_HEIGHT - GlobalVals.getBtnHeight()) / bottom.getHeight();
            checkScale();

            float yoffset = GdxStart.V_HEIGHT - (bottom.getHeight() * scale);
            yoffset /= 2;

            posTop = new Vector2(0, yoffset);
            posBottom = new Vector2(top.getWidth(), yoffset);

        }
        else
        {
            scale = (float)(GdxStart.V_WIDTH  - GlobalVals.getBtnHeight()) / bottom.getWidth();
            checkScale();

            float xoffset = GdxStart.V_WIDTH - (bottom.getWidth() * scale);
            xoffset /= 4;

            posTop = new Vector2(xoffset, bottom.getHeight());
            posBottom = new Vector2(xoffset, 0);
        }
    }

    /**
     * Will adjust the scale until it is right
     */
    private void checkScale(){
        if(leftRight)
        {
            if((bottom.getWidth() * scale) * 2 > GdxStart.V_WIDTH)
            {
                scale -= 0.01;
                checkScale();
            }
        }
        else
        {
            if((bottom.getHeight() * scale) * 2 > GdxStart.V_HEIGHT - GlobalVals.getBtnHeight() * 2)
            {
                scale -= 0.01;
                checkScale();
            }
        }
    }

    /**
     * Loads the info file about
     * @param file Part of the directory that should be looked at
     */
    private void loadPoints(String file)
    {
        FileHandle handle = Gdx.files.internal("diff/"+file+"/info.json");
        if(handle.exists()) {
            Json json = new Json();
            DiffGameInfo info = json.fromJson(DiffGameInfo.class, handle);
            leftRight = info.leftRight;
            setScale();
            for (Vector2 vec : info.bases) {
                addLocationPair(vec);
            }
        }
        else {
            // TODO: show error that file doesn't exist
            // TEST DATA
            // ERROR
            // Level info kan niet geladen worden
        }
    }

    /**
     * Add an location add base position. This will add the to the right place with scaling
     * @param base The base position
     */
    private void addLocationPair(Vector2 base)
    {
        base = base.scl(scale);

        Vector2 pos2 = new Vector2(base.x + posBottom.x * scale, base.y + posBottom.y * scale);
        Vector2 pos  = new Vector2(base.x + posTop.x * scale, base.y + posTop.y * scale);

        locations.add(new LocationPair(new DifferencesLocation(pos), new DifferencesLocation(pos2)));
    }

    /**
     * Update the puzzle
     */
    public void update()
    {
        if(InputManager.getInstance().isJustPressed()) {
            lastFound = getTotalFound();

            for (LocationPair pair : locations) {
                pair.update();
            }

            if(getTotalFound() == lastFound)
            {
                //SoundManager.getInstance().playSound(SoundManager.SoundType.WRONG);
            }
            else
            {
                SoundManager.getInstance().playSound(SoundManager.SoundType.RIGHT);
            }
        }
    }

    /**
     * Draw the puzzle to the screen
     *
     * @param batch Batch to draw the image to
     */
    public void draw(SpriteBatch batch)
    {
        batch.draw(top, posTop.x * scale, posTop.y * scale, top.getWidth() * scale, top.getHeight() * scale);
        batch.draw(bottom, posBottom.x * scale, posBottom.y * scale, bottom.getWidth() * scale, bottom.getHeight() * scale);

        for (LocationPair pair : locations) {
            pair.draw(batch);
        }
    }

    /**
     * Total differences in this puzzle
     *
     * @return The total amount of differences
     */
    public int getTotalDifferences()
    {
        return locations.size();
    }

    /**
     * Get the total amount of found differences
     *
     * @return The amount found
     */
    public int getTotalFound()
    {
        int i = 0;

        for (LocationPair pair : locations) {
            i = pair.isFound() ? i + 1 : i;
        }
        return i;
    }
}
