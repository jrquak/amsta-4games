package com.amsta.puzzel.screens;

import com.amsta.puzzel.GdxStart;
import com.amsta.puzzel.components.Buttons.TextButton;
import com.amsta.puzzel.components.bubblepopper.Bubble;
import com.amsta.puzzel.events.ButtonClickedEvent;
import com.amsta.puzzel.interfaces.IButtonPressed;
import com.amsta.puzzel.manager.InputManager;
import com.amsta.puzzel.manager.ScreenManager;
import com.amsta.puzzel.manager.SoundManager;
import com.amsta.puzzel.util.GlobalVals;
import com.amsta.puzzel.util.ScreenUtil;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by Yorick on 09-05-17.
 */
public class BubblePopperGame extends BaseScreen{

    /**
     * The Bubbles.
     */
    ArrayList<Bubble> bubbles;
    /**
     * The Rand.
     */
    Random rand;

    private Texture background;
    private TextButton scoreButton;
    private int points;
    private TextButton btnBack;

    @Override
    public boolean initialize() {
        Bubble.resetCounter();
        bubbles = new ArrayList<Bubble>();
        rand = new Random();
        background = new Texture("ui/background.jpg");
        btnBack = new TextButton(new Texture("ui/blue_button.png"), new Vector2(10, GdxStart.V_HEIGHT - GlobalVals.getBtnHeight()), "Terug");
        scoreButton = new TextButton(new Texture("ui/blue_button.png"), new Vector2(GdxStart.V_WIDTH - btnBack.getWidth(), GdxStart.V_HEIGHT - GlobalVals.getBtnHeight()), "0");

        btnBack.setListner(new IButtonPressed() {
            @Override
            public void buttonPressedHandler(ButtonClickedEvent event) {
                SoundManager.getInstance().playSound(SoundManager.SoundType.CLICK);
                ScreenManager.getInstance().goBackOneScreen();
            }
        });


        return true;
    }

    /**
     * Reset.
     */
    public void reset()
    {

    }

    @Override
    public void update() {
        btnBack.update();
        scoreButton.setText("Bellenteller: " + Bubble.getCounter());

        if(rand.nextInt(100) % 20 == 0)
        {
            bubbles.add(new Bubble());
        }

        for(int i = 0; i < bubbles.size(); i++)
        {
            Bubble b = bubbles.get(i);

            if(InputManager.getInstance().isJustPressed()) {
                Vector2 pos = ScreenUtil.touchToScreenPos();

                if (b.getBoundingBox().contains(pos)) {
                    b.pop();
                }
            }
            b.update();

            if(b.isDead())
            {
                if(b.isPopped())
                {
                    points++;
                }

                bubbles.remove(i);
                i--;
            }
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(background, 0, 0, GdxStart.V_WIDTH, GdxStart.V_HEIGHT);
        for (Bubble b: bubbles) {
            b.draw(batch);
        }
        btnBack.draw(batch);
        scoreButton.draw(batch);
    }

    @Override
    public void exitScreen() {

    }

    @Override
    public void dispose() {

    }
}
