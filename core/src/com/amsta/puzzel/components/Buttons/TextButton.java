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
 * Created by Yorick on 16-03-17.
 */
public class TextButton extends Button {
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
     * Instantiates a new Text button.
     *
     * @param img  the img
     * @param pos  the pos
     * @param text the text
     */
    public TextButton(Texture img, Vector2 pos, String text) {
        super(img, pos);
        font = new BitmapFont();
        this.text = text;

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/Roboto-Black.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = GlobalVals.FONT_SIZE_BTN;
        font = generator.generateFont(parameter);
        generator.dispose();


        layout = new GlyphLayout(); //dont do this every frame! Store it as member
        layout.setText(font, text);
        textWidth = layout.width;// contains the width of the current set text
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        font.draw(batch, text, pos.x + img.getWidth() / 2 - textWidth / 2, pos.y + img.getHeight() / 2 + layout.height / 2);
    }

    /**
     * Set the text of the button.
     *
     * @param txt The text that the button should show
     */
    public void setText(String txt)
    {
        text = txt;
        layout.setText(font, text);
        textWidth = layout.width;// contains the width of the current set text
    }

    /**
     * Get the width of the button
     *
     * @return width width
     */
    public int getWidth()
    {
        return img.getWidth();
    }

    /**
     * Get the height of the button
     *
     * @return height height
     */
    public int getHeight()
    {
        return img.getHeight();
    }
}
