package com.amsta.puzzel.screens;

import com.amsta.puzzel.GdxStart;
import com.amsta.puzzel.components.Buttons.TextButton;
import com.amsta.puzzel.components.diffgame.DiffImage;
import com.amsta.puzzel.components.particle.ParticleEmmiter;
import com.amsta.puzzel.events.ButtonClickedEvent;
import com.amsta.puzzel.interfaces.IButtonPressed;
import com.amsta.puzzel.manager.ScreenManager;
import com.amsta.puzzel.manager.SoundManager;
import com.amsta.puzzel.util.GlobalVals;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Created by Yorick on 21-03-17.
 */
public class DiffGame extends BaseScreen {
    private BitmapFont font;
    private DiffImage diffImage;
    private TextButton backButton;
    private TextButton scoreButton;
    private TextButton resetButton;
    private Texture background;
    private boolean isFinished;
    private boolean reset;
    private Random rand;
    private String chosen = "";

    private int foundLastFrame = 0;

    private ParticleEmmiter emitterLeft;
    private ParticleEmmiter emitterCenter;
    private ParticleEmmiter emitterRight;

    /**
     * The names of the available levels
     */
    private String levelNames[] = new String[]{
            "sint1",
            "test1",
            "frozen10"
    };

    @Override
    public boolean initialize() {
        rand = new Random();
        background = new Texture("ui/background.jpg");

        emitterLeft = new ParticleEmmiter();
        emitterLeft.setEmitterPos(new Vector2(GdxStart.V_WIDTH/4, 800));
        emitterCenter = new ParticleEmmiter();
        emitterCenter.setEmitterPos(new Vector2(GdxStart.V_WIDTH/2, 400));
        emitterRight = new ParticleEmmiter();
        emitterRight.setEmitterPos(new Vector2(GdxStart.V_WIDTH/2 + GdxStart.V_WIDTH/4, 800));

        if(chosen.equals(""))
        {
            diffImage = new DiffImage(getRandomLevelName());
        }
        else
        {
            diffImage = new DiffImage(chosen);
        }


        backButton  = new TextButton(new Texture("ui/blue_button.png"), new Vector2(10, GdxStart.V_HEIGHT - GlobalVals.getBtnHeight()), "Terug");
        resetButton = new TextButton(new Texture("ui/blue_button.png"), new Vector2(GdxStart.V_WIDTH/2 - GlobalVals.getBtnWidth()/2,
                GdxStart.V_HEIGHT - GlobalVals.getBtnHeight() - 150), "Opnieuw");
        scoreButton = new TextButton(new Texture("ui/blue_button.png"), new Vector2(GdxStart.V_WIDTH - 10 - backButton.getWidth(), GdxStart.V_HEIGHT - GlobalVals.getBtnHeight()), "0/0");
        // Set the eventlistner. if the button is pressed
        backButton.setListner(new IButtonPressed() {
            @Override
            public void buttonPressedHandler(ButtonClickedEvent event) {
                ScreenManager.getInstance().goBackOneScreen();
            }
        });

        //listner voor de resetbutton
        resetButton.setListner(new IButtonPressed() {
            @Override
            public void buttonPressedHandler(ButtonClickedEvent event) {
                System.out.println("Reset Pressed!");
                reset = true;
            }
        });

        return true;
    }

    /**
     * Sets chosen.
     *
     * @param chose the chose
     */
    public void setChosen(String chose)
    {
        chosen = chose;
    }

    /**
     * Get a random level name to load
     * @return A random level name
     */
    private String getRandomLevelName()
    {
        return levelNames[rand.nextInt(levelNames.length)];
    }

    @Override
    public void update() {

        if(foundLastFrame != diffImage.getTotalFound())
        {
            foundLastFrame = diffImage.getTotalFound();
            if(foundLastFrame == diffImage.getTotalDifferences())
            {
                SoundManager.getInstance().playSound(SoundManager.SoundType.WIN);

                for(int i = 0; i < 100; i++)
                {
                    emitterLeft.addParticle();
                    emitterCenter.addParticle();
                    emitterRight.addParticle();
                }
            }
        }
        emitterLeft.update();
        emitterRight.update();
        emitterCenter.update();

        backButton.update();
        diffImage.update();
        if (isFinished){
            resetButton.update();
        }
        scoreButton.setText(diffImage.getTotalFound() + "/" + diffImage.getTotalDifferences());
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(background, 0,0, GdxStart.V_WIDTH, GdxStart.V_HEIGHT);

        diffImage.draw(batch);
        backButton.draw(batch);
        scoreButton.draw(batch);
        detectFinished(batch);
        emitterLeft.draw(batch);
        emitterCenter.draw(batch);
        emitterRight.draw(batch);
        if (reset){
            reset(batch);
        }
    }

    @Override
    public void exitScreen() {

    }

    @Override
    public void dispose() {

    }


    /**
     * Detect finished.
     *
     * @param batch the batch
     */
//kijk of alle verschillen zijn gevonden
    public void detectFinished(SpriteBatch batch){
        if (diffImage.getTotalFound() == diffImage.getTotalDifferences()){
            this.resetButton.draw(batch);
            isFinished = true;
        }
    }


    /**
     * Reset.
     *
     * @param batch the batch
     */
//reset de puzzel
    public void reset(SpriteBatch batch){
        diffImage = new DiffImage(chosen);
        diffImage.draw(batch);
        backButton.draw(batch);
        scoreButton.draw(batch);
        isFinished = false;
        reset = false;
    }
}
