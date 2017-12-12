package com.amsta.puzzel.components.ColorMemory;

import com.amsta.puzzel.events.ButtonClickedEvent;
import com.amsta.puzzel.interfaces.IButtonPressed;
import com.amsta.puzzel.manager.InputManager;
import com.amsta.puzzel.manager.SoundManager;
import com.amsta.puzzel.util.ScreenUtil;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Vector;

/**
 * Created by Tom on 23-3-2017.
 */


public class ColorTile {
    private Vector2 position;
    /**
     * The Colision box.
     */
    public Rectangle colisionBox;
    /**
     * The Color.
     */
    public Color color;
    /**
     * The Texture.
     */
    public Texture texture;
    /**
     * The Back card.
     */
    public Texture backCard;
    /**
     * The Is showing.
     */
    public boolean isShowing= false;
    /**
     * The Already fired.
     */
    boolean alreadyFired= false;
    /**
     * The Listner.
     */
    protected IButtonPressed listner;
    /**
     * The Is matched.
     */
    public boolean isMatched=false;

    /**
     * Het maken van de colortile en zijn eigenschappen
     *
     * @param position the position
     * @param color    the color
     */
    public ColorTile( Vector2 position, Color color)
    {
        this.color= color;
        this.position= position;
        colisionBox= new Rectangle(position.x, position.y, 300, 200);
        texture = new Texture("colormem/colortile.png");
        backCard= new Texture("colormem/question.png");
    }

    /**
     * Verschillen in draw afhankelijk van voor en achterkant
     *
     * @param batch the batch
     */
    public void draw (SpriteBatch batch)
    {

        if (isShowing) {
            Color oldColor = batch.getColor();
            texture = new Texture("colormem/colortile.png");
            batch.setColor(color);
            batch.draw(texture, colisionBox.getX(), colisionBox.getY(), colisionBox.getWidth(), colisionBox.getHeight());
            batch.setColor(oldColor);
        }
        else {
            Color oldColor = batch.getColor();
            texture= new Texture("colormem/question.png");
            batch.draw(texture, colisionBox.getX(), colisionBox.getY(), colisionBox.getWidth(), colisionBox.getHeight());
            batch.setColor(oldColor);
        }
    }

    /**
     * Checkt op collision zo ja word het kaartje omgedraaid.
     */
    public void update()
    {

        if(InputManager.getInstance().isJustPressed()&&!isMatched())
        {
            Vector2 pos = ScreenUtil.touchToScreenPos();

            if(colisionBox.contains(pos.x, pos.y) && alreadyFired == false)
            {
                buttonPressed();
            }
        }
        else
        {
            alreadyFired = false;
        }
    }

    /**
     * Button pressed.
     */
    public void buttonPressed()
    {
        isShowing = !isShowing;
        alreadyFired = true;
        SoundManager.getInstance().playSound(SoundManager.SoundType.CARD);
    }

    /**
     * Gets color.
     *
     * @return the color
     */
    public Color getColor()
    {
        return color;
    }

    /**
     * Is showing boolean.
     *
     * @return the boolean
     */
    public boolean isShowing()
    {
        return isShowing;
    }

    /**
     * Is matched boolean.
     *
     * @return the boolean
     */
    public boolean isMatched()
    {
        return isMatched;
    }


    /**
     * Opnieuw button.
     */
    void OpnieuwButton ()
    {
        isShowing=false;
    }


}


