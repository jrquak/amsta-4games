package com.amsta.puzzel.util;

import com.amsta.puzzel.GdxStart;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Yorick on 16-03-17.
 */
public class ScreenUtil {

    /**
     * Touch to screen pos vector2. Get the position of the first finger to touch or mouse
     *
     * @return the vector 2
     */
    public static Vector2 touchToScreenPos()
    {
        Vector3 input = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        input = GdxStart.camera.unproject(input);
        return new Vector2(input.x, input.y);
    }
}
