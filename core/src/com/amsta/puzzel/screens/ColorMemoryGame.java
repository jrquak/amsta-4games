package com.amsta.puzzel.screens;

import com.amsta.puzzel.GdxStart;
import com.amsta.puzzel.components.Buttons.TextButton;
import com.amsta.puzzel.components.ColorMemory.ColorTile;
import com.amsta.puzzel.events.ButtonClickedEvent;
import com.amsta.puzzel.interfaces.IButtonPressed;
import com.amsta.puzzel.manager.ScreenManager;
import com.amsta.puzzel.manager.SoundManager;
import com.amsta.puzzel.util.GlobalVals;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.amsta.puzzel.components.particle.ParticleEmmiter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * In deze class bevind zich het memoryspel.
 * @author Tom Noordeloos
 * @author Jordy Quak
 */

public class ColorMemoryGame extends BaseScreen {
    private BitmapFont font;
    private TextButton btnBack;
    private TextButton btnScore;
    private TextButton btnReset;
    private Texture background;
    private List<ColorTile> colorTiles;
    private int score = 0;
    private boolean matched = false;
    private boolean isFinished = false;
    private int counter;
    private int totaal = 0;
    private ArrayList<Color> colors;
    private final int easy = 1;
    private final int normal = 2;
    private final int hard = 3;

    private ParticleEmmiter emitterLeft;
    private ParticleEmmiter emitterCenter;
    private ParticleEmmiter emitterRight;

    @Override
    public boolean initialize() {

        background = new Texture("ui/background.jpg");
        btnBack = new TextButton(new Texture("ui/blue_button.png"), new Vector2(10, GdxStart.V_HEIGHT - GlobalVals.getBtnHeight()), "Terug");
        btnBack.setListner(new IButtonPressed() {
            @Override
            public void buttonPressedHandler(ButtonClickedEvent event) {
                SoundManager.getInstance().playSound(SoundManager.SoundType.CLICK);
                ScreenManager.getInstance().goBackOneScreen();
            }
        });

        btnReset = new TextButton(new Texture("ui/blue_button.png"), new Vector2(GdxStart.V_WIDTH/2 - GlobalVals.getBtnWidth()/2,
                GdxStart.V_HEIGHT - GlobalVals.getBtnHeight() - 150), "Opnieuw");
        btnReset.setListner(new IButtonPressed() {
            @Override
            public void buttonPressedHandler(ButtonClickedEvent event) {
                SoundManager.getInstance().playSound(SoundManager.SoundType.CLICK);
               reset();
            }
        });

        btnScore = new TextButton(new Texture("ui/blue_button.png"), new Vector2(GdxStart.V_WIDTH - 10 - btnBack.getWidth(),
                GdxStart.V_HEIGHT - GlobalVals.getBtnHeight()), score + "/" + this.totaal);

        emitterLeft = new ParticleEmmiter();
        emitterLeft.setEmitterPos(new Vector2(GdxStart.V_WIDTH/4, 800));
        emitterCenter = new ParticleEmmiter();
        emitterCenter.setEmitterPos(new Vector2(GdxStart.V_WIDTH/2, 400));
        emitterRight = new ParticleEmmiter();
        emitterRight.setEmitterPos(new Vector2(GdxStart.V_WIDTH/2 + GdxStart.V_WIDTH/4, 800));
        reset();

        return true;
    }


    @Override
    /**
     * update de knoppen en de kaartjes
     */
    public void update() {
        btnBack.update();
        btnReset.update();
        int selected = 0;
        for (ColorTile color : colorTiles) {
            if (color.isShowing() && !color.isMatched())
                selected++;
        }
        if (selected < 2) {
            for (ColorTile color : colorTiles) {
                color.update();
            }
        }
        colorMatch();
        emitterLeft.update();
        emitterCenter.update();
        emitterRight.update();
    }

    @Override
    public void draw(SpriteBatch batch) {

        batch.draw(background, 0, 0, GdxStart.V_WIDTH, GdxStart.V_HEIGHT);
        btnBack.draw(batch);
        btnScore.draw(batch);
        if (isFinished) {
            btnReset.draw(batch);
        }
        for (ColorTile color : colorTiles) {
            color.draw(batch);
            }
        emitterLeft.draw(batch);
        emitterCenter.draw(batch);
        emitterRight.draw(batch);
        }



    @Override
    public void exitScreen() {

    }

    @Override
    public void dispose() {

    }

    /**
     * Initialiseert de kleuren die mogelijk gekozen kunnen worden en de hoeveelheid daarvan
     */
    public void initColors() {
        colors = new ArrayList<Color>();
        switch (ColorMemoryDifficulty.currentDiff) {
            case easy:
                colors.add(Color.RED);
                colors.add(Color.RED);
                colors.add(Color.BLUE);
                colors.add(Color.BLUE);
                break;

            case normal:
                colors.add(Color.RED);
                colors.add(Color.RED);
                colors.add(Color.BLUE);
                colors.add(Color.BLUE);
                colors.add(Color.GREEN);
                colors.add(Color.GREEN);
                colors.add(Color.YELLOW);
                colors.add(Color.YELLOW);
                break;

            case hard:
                colors.add(Color.RED);
                colors.add(Color.RED);
                colors.add(Color.BLUE);
                colors.add(Color.BLUE);
                colors.add(Color.GREEN);
                colors.add(Color.GREEN);
                colors.add(Color.PINK);
                colors.add(Color.PINK);
                colors.add(Color.YELLOW);
                colors.add(Color.YELLOW);
                colors.add(Color.CYAN);
                colors.add(Color.CYAN);
                break;
        }

    }

    /**
     * Berekent een random getal voor het kiezen van de kleuren.
     *
     * @return random color
     */
    public Color getRandomColor() {
        Random rand = new Random();
        int index = rand.nextInt(colors.size());
        Color ret = colors.get(index);
        colors.remove(index);
        return ret;
    }

    /**
     * Een methode voor het regelen van de colormatch en deze checkt erop.
     */
    public void colorMatch() {

        for (ColorTile tile : colorTiles) {
            if (tile.isShowing && !tile.isMatched()) {
                for (ColorTile tile2 : colorTiles) {
                    if (!tile2.isMatched() && tile2.isShowing && tile != tile2) {
                        if (tile.getColor() == tile2.getColor()) {
                            score++;
                            btnScore.setText(score + "/" + this.totaal);
                            tile.isMatched = true;
                            tile2.isMatched = true;
                            SoundManager.getInstance().playSound(SoundManager.SoundType.RIGHT);
                            detectFinished();

                        } else {
                            counter++;
                            if (counter % 40 == 0) {
                                tile.isShowing = false;
                                tile2.isShowing = false;
                                SoundManager.getInstance().playSound(SoundManager.SoundType.WRONG);
                            }
                        }
                    }
                }
            }

        }
    }

    /**
     * Kijkt naar de moeilijkheid en pakt op basis daarvan paren random kleuren.
     */
    public void reset() {
        initColors();
        colorTiles = new ArrayList<ColorTile>();

        switch (ColorMemoryDifficulty.currentDiff) {
            case easy:

                colorTiles.add(new ColorTile(new Vector2(10, 50), getRandomColor()));
                colorTiles.add(new ColorTile(new Vector2(10, 315), getRandomColor()));
                colorTiles.add(new ColorTile(new Vector2(350, 50), getRandomColor()));
                colorTiles.add(new ColorTile(new Vector2(350, 315), getRandomColor()));
                score = 0;
                totaal = 2;
                btnScore.setText(score + "/" + this.totaal);
                isFinished = false;
                break;

            case normal:
                colorTiles.add(new ColorTile(new Vector2(110, 50), getRandomColor()));
                colorTiles.add(new ColorTile(new Vector2(110, 315), getRandomColor()));
                colorTiles.add(new ColorTile(new Vector2(110, 580), getRandomColor()));
                colorTiles.add(new ColorTile(new Vector2(110, 845), getRandomColor()));
                colorTiles.add(new ColorTile(new Vector2(600, 50), getRandomColor()));
                colorTiles.add(new ColorTile(new Vector2(600, 315), getRandomColor()));
                colorTiles.add(new ColorTile(new Vector2(600, 580), getRandomColor()));
                colorTiles.add(new ColorTile(new Vector2(600, 845), getRandomColor()));
                score = 0;
                totaal = 4;
                btnScore.setText(score + "/" + this.totaal);
                isFinished = false;
                break;

            case hard:
                colorTiles.add(new ColorTile(new Vector2(10, 50), getRandomColor()));
                colorTiles.add(new ColorTile(new Vector2(10, 315), getRandomColor()));
                colorTiles.add(new ColorTile(new Vector2(10, 580), getRandomColor()));
                colorTiles.add(new ColorTile(new Vector2(10, 845), getRandomColor()));
                colorTiles.add(new ColorTile(new Vector2(350, 50), getRandomColor()));
                colorTiles.add(new ColorTile(new Vector2(350, 315), getRandomColor()));
                colorTiles.add(new ColorTile(new Vector2(350, 580), getRandomColor()));
                colorTiles.add(new ColorTile(new Vector2(350, 845), getRandomColor()));
                colorTiles.add(new ColorTile(new Vector2(700, 50), getRandomColor()));
                colorTiles.add(new ColorTile(new Vector2(700, 315), getRandomColor()));
                colorTiles.add(new ColorTile(new Vector2(700, 580), getRandomColor()));
                colorTiles.add(new ColorTile(new Vector2(700, 845), getRandomColor()));
                score = 0;
                totaal = 6;
                btnScore.setText(score + "/" + this.totaal);
                isFinished = false;
                break;
        }

    }

    /**
     * Deze methode kijkt of het spel klaar is.
     */
    public void detectFinished() {
        if ((score == 2 && ColorMemoryDifficulty.currentDiff == easy) ||
                (score == 4 && ColorMemoryDifficulty.currentDiff == normal) ||
                (score == 6 && ColorMemoryDifficulty.currentDiff == hard)) {

            isFinished = true;
            SoundManager.getInstance().playSound(SoundManager.SoundType.WIN);
            for(int i = 0; i < 100; i++)
            {
                emitterLeft.addParticle();
                emitterCenter.addParticle();
                emitterRight.addParticle();
            }
        }
    }
}
