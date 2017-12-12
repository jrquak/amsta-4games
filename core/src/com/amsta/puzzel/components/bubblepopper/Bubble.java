package com.amsta.puzzel.components.bubblepopper;

import com.amsta.puzzel.util.GlobalVals;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Created by Yorick on 09-05-17.
 */
public class Bubble {
    private Texture tex;
    private Vector2 pos;

    private int frame = 0;
    private int frameTimer = 0;
    private boolean isDead = false;
    private boolean isStarted = false;
    private boolean isPopped = false;
    private Rectangle boundingBox;
    private Random rand;
    private int random;
    private float time = 0.0f;
    private float Y_SPEED = 1.0f;
    private static int counter;

    private Sound lowpop = Gdx.audio.newSound(Gdx.files.internal("audio/low_pop.ogg"));
    private Sound midpop = Gdx.audio.newSound(Gdx.files.internal("audio/medium_pop.ogg"));
    private Sound highpop = Gdx.audio.newSound(Gdx.files.internal("audio/high_pop.ogg"));

    /**
     * Instantiates a new Bubble.
     */
    public Bubble() {
        rand = new Random();
        random = rand.nextInt(4);
        tex = new Texture("bubblepopper/bubblesprite.png");
        pos = new Vector2(rand.nextFloat() * GlobalVals.V_WIDTH - GlobalVals.BUBBLE_WIDTH / 2, 10);
        boundingBox = new Rectangle(pos.x, pos.y, GlobalVals.BUBBLE_WIDTH, GlobalVals.BUBBLE_HEIGHT);
        Y_SPEED = rand.nextFloat() * 2;
    }

    /**
     * Gets bounding box.
     *
     * @return the bounding box
     */
    public Rectangle getBoundingBox() {
        return boundingBox;
    }

    /**
     * Pop.
     */
    public void pop() {
        isStarted = true;
        isPopped = true;
        counter++;

        //Play a random pop sound when a bubble is popped
        switch (rand.nextInt(3)) {
            case 0:
                lowpop.play(1.0f);
                midpop.stop();
                midpop.dispose();
                highpop.stop();
                highpop.dispose();
                break;
            case 1:
                midpop.play(1.0f);
                highpop.stop();
                highpop.dispose();
                lowpop.stop();
                lowpop.dispose();
                break;
            case 2:
                highpop.play(1.0f);
                lowpop.stop();
                lowpop.dispose();
                midpop.stop();
                midpop.dispose();
                break;
        }
    }

    /**
     * Is dead boolean.
     *
     * @return the boolean
     */
    public boolean isDead() {
        return isDead;
    }

    /**
     * Is popped boolean.
     *
     * @return the boolean
     */
    public boolean isPopped() {
        return isPopped;
    }

    /**
     * Update.
     */
    public void update() {
        time += Gdx.graphics.getDeltaTime();

        pos.add((float) Math.cos(time), Y_SPEED);

        boundingBox.setPosition(pos);

        if (pos.y > GlobalVals.V_HEIGHT) {
            isDead = true;
        }

        if (isStarted && !isDead) {
            frameTimer++;
            if (frameTimer % 5 == 0) {
                frame++;
                if (frame % 6 == 0) {
                    isDead = true;
                }
            }
        }
    }

    /**
     * Draw.
     *
     * @param batch the batch
     */
    public void draw(SpriteBatch batch) {
        if (!isDead) {
            switch(random){
                case 0:
                    batch.setColor(Color.YELLOW);
                    break;
                case 1:
                    batch.setColor(Color.BLUE);
                    break;
                case 2:
                    batch.setColor(Color.GREEN);
                    break;
                case 3:
                    batch.setColor(Color.WHITE);
            }


            batch.draw(tex, pos.x, pos.y, 0, frame * GlobalVals.BUBBLE_HEIGHT, GlobalVals.BUBBLE_WIDTH, GlobalVals.BUBBLE_WIDTH);
        }
        batch.setColor(Color.WHITE);
    }

    public static int getCounter(){
        return counter;
    }

    public static void resetCounter(){
        counter = 0;
    }

}
