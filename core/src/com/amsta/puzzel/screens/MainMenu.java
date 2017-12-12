package com.amsta.puzzel.screens;

import com.amsta.puzzel.GdxStart;
import com.amsta.puzzel.components.Buttons.Button;
import com.amsta.puzzel.components.Buttons.ImageButton;
import com.amsta.puzzel.components.Buttons.SoundButton;
import com.amsta.puzzel.components.Buttons.TextButton;
import com.amsta.puzzel.events.ButtonClickedEvent;
import com.amsta.puzzel.interfaces.IButtonPressed;
import com.amsta.puzzel.manager.InputManager;
import com.amsta.puzzel.manager.ScreenManager;
import com.amsta.puzzel.manager.SoundManager;
import com.amsta.puzzel.util.GlobalVals;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

/**
 * Created by Yorick on 16-03-17.
 */
public class MainMenu extends BaseScreen implements IButtonPressed {

    private Texture background;
    private ImageButton btnDiff;
    private ImageButton btnMemory;
    private ImageButton btnTicTacToe;
    private ImageButton btnPuzzle;
    private TextButton btnExit;
    private Button btnSound;
    private Button btnCredit;

    private ArrayList<Button> buttons;

    @Override
    public boolean initialize() {
        isShowing = true;
        isUpdating = true;

        background = new Texture("ui/background.jpg");

        btnDiff = new ImageButton(new Texture("ui/blue_button.png"), new Vector2(
                GdxStart.V_WIDTH/4 - GlobalVals.getBtnWidth()/2, GdxStart.V_HEIGHT - GlobalVals.getBtnHeight() - GdxStart.V_HEIGHT/10),
                "Verschil", new Texture("ui/DifferenceGame.png"), false);
        btnDiff.setListner(this);

        btnMemory = new ImageButton(new Texture("ui/blue_button.png"), new Vector2(
                GdxStart.V_WIDTH/4 - GlobalVals.getBtnWidth()/2, GdxStart.V_HEIGHT - GlobalVals.getBtnHeight() - (GdxStart.V_HEIGHT/2) - GdxStart.V_HEIGHT/4),
                "Kleuren Memory", new Texture("ui/ColorMem.png"), true);
        btnMemory.setListner(this);

        btnTicTacToe = new ImageButton(new Texture("ui/blue_button.png"), new Vector2(
                GdxStart.V_WIDTH/4 + GlobalVals.getBtnWidth() - 50, GdxStart.V_HEIGHT - GlobalVals.getBtnHeight() - GdxStart.V_HEIGHT/10),
                "Bellen Spel", new Texture("ui/BoterKaas.png"), false);
        btnTicTacToe.setListner(this);

        btnPuzzle = new ImageButton(new Texture("ui/blue_button.png"), new Vector2(
                GdxStart.V_WIDTH/4 + GlobalVals.getBtnWidth() - 50, GdxStart.V_HEIGHT - GlobalVals.getBtnHeight() - GdxStart.V_HEIGHT/2 - GdxStart.V_HEIGHT/4),
                "Schuif Puzzel", new Texture("ui/Schuifpuzzel.png"), true);
        btnPuzzle.setListner(this);

        btnExit = new TextButton(new Texture("ui/blue_button.png"), new Vector2(
                GdxStart.V_WIDTH/2  - GlobalVals.getBtnWidth()/2, 0), "Stop");
        btnExit.setListner(this);

        Texture audioOn = new Texture("ui/audioOn.png");
        btnSound = new SoundButton(audioOn, new Texture("ui/audioOff.png"), new Vector2(
                GdxStart.V_WIDTH - audioOn.getWidth(), GdxStart.V_HEIGHT - audioOn.getHeight()));
        btnSound.setListner(this);

        btnCredit = new Button(new Texture("ui/about.png"), new Vector2(20, 10));
        btnCredit.setListner(this);

        buttons = new ArrayList<Button>();
        buttons.add(btnDiff);
        buttons.add(btnMemory);
        buttons.add(btnPuzzle);
        buttons.add(btnTicTacToe);
        buttons.add(btnSound);
        buttons.add(btnExit);
        buttons.add(btnCredit);

        return true;
    }

    @Override
    public void update() {
        for (Button b : buttons) {
            b.update();
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(background, 0, 0, GdxStart.V_WIDTH, GdxStart.V_HEIGHT);

        for (Button b : buttons) {
            b.draw(batch);
        }

        // TODO: remove testcode
        if(Gdx.input.isKeyJustPressed(Input.Keys.T))
        {
            ScreenManager.getInstance().changeScreen(new TestScreen());
        }
    }

    @Override
    public void exitScreen() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void buttonPressedHandler(ButtonClickedEvent event) {
        SoundManager.getInstance().playSound(SoundManager.SoundType.CLICK);
        if(event.getSource() == btnDiff) {
            ScreenManager.getInstance().changeScreen(new DiffLevelSelect());
        }
        else if (event.getSource() == btnPuzzle)
        {
            ScreenManager.getInstance().changeScreen(new ShuffleImageSelect());
        }
        else if (event.getSource() == btnMemory)
        {
            ScreenManager.getInstance().changeScreen(new ColorMemoryDifficulty());
        }
        else if (event.getSource() == btnTicTacToe)
        {
            ScreenManager.getInstance().changeScreen(new BubblePopperGame());
        }
        else if (event.getSource() == btnCredit)
        {
            ScreenManager.getInstance().changeScreen(new CreditScreen());
        }
        else if(event.getSource() == btnExit)
        {
            Gdx.app.exit();
        }
    }
}
