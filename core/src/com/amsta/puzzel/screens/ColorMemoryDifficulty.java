package com.amsta.puzzel.screens;

import com.amsta.puzzel.GdxStart;
import com.amsta.puzzel.events.ButtonClickedEvent;
import com.amsta.puzzel.interfaces.IButtonPressed;
import com.amsta.puzzel.manager.ScreenManager;
import com.amsta.puzzel.manager.SoundManager;
import com.amsta.puzzel.util.GlobalVals;
import com.badlogic.gdx.graphics.Texture;
import com.amsta.puzzel.components.Buttons.TextButton;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * In deze class wordt de moeilijkheidsgraad voor memory geselecteerd.
 * @author Jordy Quak
 */
public class ColorMemoryDifficulty extends BaseScreen {

    private TextButton btnEasy;
    private TextButton btnNormal;
    private TextButton btnHard;
    private TextButton btnBack;
    private Texture background;
    private int easy = 1;
    private int normal = 2;
    private int hard = 3;
    public static int currentDiff;


    @Override
    public boolean initialize() {
        background = new Texture("ui/background.jpg");

        //Knoppen voor de moeilijkheidsgraad
        btnEasy = new TextButton(new Texture("ui/blue_button.png"), new Vector2(GdxStart.V_WIDTH/2 - GlobalVals.getBtnWidth()/2, GdxStart.V_HEIGHT - GlobalVals.getBtnHeight() - 200), "Makkelijk");
        btnNormal = new TextButton(new Texture("ui/blue_button.png"), new Vector2(GdxStart.V_WIDTH/2 - GlobalVals.getBtnWidth()/2, GdxStart.V_HEIGHT - GlobalVals.getBtnHeight() - 400), "Normaal");
        btnHard = new TextButton(new Texture("ui/blue_button.png"), new Vector2(GdxStart.V_WIDTH/2 - GlobalVals.getBtnWidth()/2, GdxStart.V_HEIGHT - GlobalVals.getBtnHeight() - 600), "Moeilijk");
        btnBack = new TextButton(new Texture("ui/blue_button.png"), new Vector2(GdxStart.V_WIDTH/2 - GlobalVals.getBtnWidth()/2, GdxStart.V_HEIGHT - GlobalVals.getBtnHeight() - 1000), "Terug");

        btnEasy.setListner(new IButtonPressed() {
            @Override
            public void buttonPressedHandler(ButtonClickedEvent event) {
                currentDiff = easy;
                SoundManager.getInstance().playSound(SoundManager.SoundType.CLICK);
                ScreenManager.getInstance().changeScreen(new ColorMemoryGame());
            }
        });

        btnNormal.setListner(new IButtonPressed() {
            @Override
            public void buttonPressedHandler(ButtonClickedEvent event) {
                currentDiff = normal;
                SoundManager.getInstance().playSound(SoundManager.SoundType.CLICK);
                ScreenManager.getInstance().changeScreen(new ColorMemoryGame());
            }
        });

        btnHard.setListner(new IButtonPressed() {
            @Override
            public void buttonPressedHandler(ButtonClickedEvent event) {
                currentDiff = hard;
                SoundManager.getInstance().playSound(SoundManager.SoundType.CLICK);
                ScreenManager.getInstance().changeScreen(new ColorMemoryGame());
            }
        });

        btnBack.setListner(new IButtonPressed() {
            @Override
            public void buttonPressedHandler(ButtonClickedEvent event) {
                SoundManager.getInstance().playSound(SoundManager.SoundType.CLICK);
                ScreenManager.getInstance().goBackOneScreen();
            }
        });

        return true;
    }

    @Override
    public void update() {
        btnEasy.update();
        btnNormal.update();
        btnHard.update();
        btnBack.update();

    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(background, 0, 0, GdxStart.V_WIDTH, GdxStart.V_HEIGHT);
        btnEasy.draw(batch);
        btnNormal.draw(batch);
        btnHard.draw(batch);
        btnBack.draw(batch);

    }

    @Override
    public void exitScreen() {

    }

    @Override
    public void dispose() {

    }
}
