package com.amsta.puzzel.components.Buttons;

import com.amsta.puzzel.util.GlobalVals;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by jacco on 06/04/2017.
 */


/**
 * Popup for on the screen
 */
public class TextPopup {

    /**
     * The Font.
     */
    protected BitmapFont font;
    /**
     * The Text.
     */
    protected String text;
    /**
     * The Text width.
     */
    protected float textWidth;
    /**
     * The Layout.
     */
    protected GlyphLayout layout;
    /**
     * The Img.
     */
    protected Texture img;
    /**
     * The Pos.
     */
    protected Vector2 pos;

    /**
     * Instantiates a new Text popup.
     *
     * @param img  The background image
     * @param pos  The position
     * @param text Text of the popup
     */
    public TextPopup(Texture img, Vector2 pos, String text) {
        font = new BitmapFont();
        this.text = text;
        this.img = img;
        this.pos = pos;

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/Roboto-Black.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = GlobalVals.FONT_SIZE_BTN;
        font = generator.generateFont(parameter);
        generator.dispose();

        layout = new GlyphLayout(); //dont do this every frame! Store it as member
        layout.setText(font, text);
        textWidth = layout.width;// contains the width of the current set text
    }

    /**
     * Draw the popup
     *
     * @param batch The spritebatch to draw to
     */
    public void draw(SpriteBatch batch) {
        batch.draw(img, pos.x, pos.y);
        font.draw(batch, text, pos.x + img.getWidth() / 2 - textWidth / 2, pos.y + img.getHeight() / 2 + layout.height / 2);
    }

    /**
     * Set the text of the popup
     *
     * @param txt The text to show
     */
    public void setText(String txt) {
        text = txt;
        layout.setText(font, text);
        textWidth = layout.width;
    }

    /**
     * Get the width of the background of the popup
     *
     * @return The width of the background image
     */
    public int getWidth() {
        return img.getWidth();
    }

    /**
     * Get the height of the background of the popup
     *
     * @return The height of the background image
     */
    public int getHeight() {
        return img.getHeight();
    }
}
