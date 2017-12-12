package com.amsta.puzzel.screens;

import com.amsta.puzzel.GdxStart;
import com.amsta.puzzel.components.Buttons.TextButton;
import com.amsta.puzzel.components.shufflegame.PuzzlePiece;
import com.amsta.puzzel.components.shufflegame.ShufflePuzzle;
import com.amsta.puzzel.events.ButtonClickedEvent;
import com.amsta.puzzel.interfaces.IButtonPressed;
import com.amsta.puzzel.manager.ScreenManager;
import com.amsta.puzzel.util.GlobalVals;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by kohaj on 23/03/2017.
 */
public class ShuffleGame extends BaseScreen {

    private TextButton backButton;
    private TextButton scoreButton;
    private ShufflePuzzle shufflePuzzle;
    private Texture background;
    private String puzzleTextureLocation;

    public final static int HEADER_SIZE = 50;

    @Override
    public boolean initialize() {
        PuzzlePiece.resetCounter();
        shufflePuzzle = new ShufflePuzzle(new Texture(puzzleTextureLocation));
        background = new Texture("ui/background.jpg");

        backButton  = new TextButton(new Texture("ui/blue_button.png"), new Vector2(10, GdxStart.V_HEIGHT - GlobalVals.getBtnHeight()), "Terug");
        scoreButton = new TextButton(new Texture("ui/blue_button.png"), new Vector2(GdxStart.V_WIDTH - backButton.getWidth(), GdxStart.V_HEIGHT - GlobalVals.getBtnHeight()), "0");
        backButton.setListner(new IButtonPressed() {
            @Override
            public void buttonPressedHandler(ButtonClickedEvent event) {
                ScreenManager.getInstance().goBackOneScreen();
            }
        });

        return true;
    }

    public void setPuzzleLocation(String location)
    {
        puzzleTextureLocation = location;
    }

    @Override
    public void update() {
        backButton.update();
        shufflePuzzle.update();
        scoreButton.setText("Aantal Zetten: " + PuzzlePiece.getCounter());
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(background, 0, 0, GdxStart.V_WIDTH, GdxStart.V_HEIGHT);
        backButton.draw(batch);
        shufflePuzzle.draw(batch);
        scoreButton.draw(batch);
    }

    @Override
    public void exitScreen() {

    }

    @Override
    public void dispose() {

    }
}
