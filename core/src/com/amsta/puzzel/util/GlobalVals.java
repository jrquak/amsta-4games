package com.amsta.puzzel.util;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Yorick on 09-05-17.
 */
public class GlobalVals {
    private static Texture btnImg;

    private static void loadBtn()
    {
        btnImg = new Texture("ui/blue_button.png");
    }

    /**
     * Gets btn width.
     *
     * @return the btn width
     */
    public static int getBtnWidth()
    {
        if(btnImg == null)
        {
            loadBtn();
        }
        return btnImg.getWidth();
    }

    /**
     * Gets btn height.
     *
     * @return the btn height
     */
    public static int getBtnHeight()
    {
        if(btnImg == null)
        {
            loadBtn();
        }
        return btnImg.getHeight();
    }

    /**
     * The constant FONT_SIZE_BTN.
     */
    public final static int FONT_SIZE_BTN = 32;
    /**
     * The constant V_WIDTH.
     */
    public final static int V_WIDTH = 1024;
    /**
     * The constant V_HEIGHT.
     */
    public final static int V_HEIGHT = 1366;

    /**
     * The constant BUBBLE_WIDTH.
     */
    public final static int BUBBLE_WIDTH = 98;
    /**
     * The constant BUBBLE_HEIGHT.
     */
    public final static int BUBBLE_HEIGHT = 98;
}
