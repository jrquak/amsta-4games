package com.amsta.puzzel.components.particle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Created by Yorick on 18-04-17.
 */

/**
 * Particle
 */
public class Particle {
    private Color color;
    private Vector2 pos;
    private Vector2 deltaMovement;
    private Texture img;
    private float rotation;
    private float ttl; // Time to live. Amount of frames this particle will exist

    private final float START_TTL = 200;
    private final float ROTATION_SPEED = 10;

    private final float GRAVITY = 0.04f;
    private Random rand;

    /**
     * Constructor for particle. It will set the color, position, texture and movement
     *
     * @param pos   Start position of the particle
     * @param color The color of the particle
     * @param tex   The texture of the particle
     */
    public Particle(Vector2 pos, Color color, Texture tex)
    {
        this(pos, color, tex, false);
    }

    /**
     * Constructor for particle. It will set the color, position, texture and movement
     *
     * @param pos    Start position of the particle
     * @param color  The color of the particle
     * @param tex    The texture of the particle
     * @param offset if there should be an random offset
     */
    public Particle(Vector2 pos, Color color, Texture tex, boolean offset)
    {
        rand = new Random();
        this.pos = pos;
        this.color = color;
        this.img = tex;
        this.deltaMovement = new Vector2(rand.nextFloat() * 6 - 3,  rand.nextFloat() * 10 - 3);
        ttl = START_TTL;

        if(offset)
        {
            this.pos.add(new Vector2(rand.nextFloat() * 20 - 10,  rand.nextFloat() * 20 - 10));
        }
    }

    /**
     * Update the particle
     */
    public void update()
    {
        deltaMovement.sub(new Vector2(0, GRAVITY));
        pos.add(deltaMovement);
        rotation += ROTATION_SPEED;

        ttl--;
    }

    /**
     * Draw the particle
     *
     * @param batch Batch to draw image to
     */
    public void draw(SpriteBatch batch)
    {
        //batch.setColor(color);
        batch.setColor(color.r, color.g, color.b, ttl/ START_TTL);//set alpha to 0.3
        batch.draw(img, pos.x, pos.y, img.getWidth() / 2, img.getHeight() / 2, img.getWidth(),
                img.getHeight(), ttl / START_TTL, ttl / START_TTL, rotation, 0, 0, img.getWidth(), img.getHeight(), false, false);
        batch.setColor(Color.WHITE);
    }

    /**
     * Check if the particle is alive
     *
     * @return if the partile is alive
     */
    public boolean isAlive()
    {
        return ttl > 0;
    }
}
