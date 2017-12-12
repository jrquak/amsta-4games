package com.amsta.puzzel.components.particle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Yorick on 18-04-17.
 */

/**
 * Emitter that will controll the particles
 */
public class ParticleEmmiter {

    private ArrayList<Particle> particles;
    private ArrayList<Texture> particleTextures;
    private Vector2 emmiterPos;
    private Random rand;

    /**
     * Constructor for the particle emitter
     */
    public ParticleEmmiter()
    {
        particles = new ArrayList<Particle>();
        particleTextures = new ArrayList<Texture>();
        particleTextures.add(new Texture("particle/circle.png"));
        particleTextures.add(new Texture("particle/diamond.png"));
        particleTextures.add(new Texture("particle/star.png"));
        emmiterPos = new Vector2(300, 400);
        rand = new Random();
    }

    /**
     * Set the position of the emitter
     *
     * @param pos The new position
     */
    public void setEmitterPos(Vector2 pos)
    {
        emmiterPos = pos;
    }

    /**
     * Update the emitter and particles
     */
    public void update()
    {
        // for instead of foreach because something can get removed from the list
        // foreach will give an error
        for(int i = 0; i < particles.size(); i++)
        {
            Particle p = particles.get(i);
            p.update();

            // if the particle isn't alive remove it from the list and go back 1
            // because everything will shift one spot.
            if(!p.isAlive())
            {
                particles.remove(i);
                i--;
            }
        }

        //TODO: remove test code
        if(Gdx.input.isKeyPressed(Input.Keys.F))
        {
            for(int i = 0; i < 100; i++)
            {
                addParticle();
            }
        }

    }

    /**
     * Add an random particle to the emitter
     */
    public void addParticle()
    {
        particles.add(new Particle(emmiterPos.cpy(), new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), 1f), particleTextures.get(rand.nextInt(particleTextures.size())), true));
    }

    /**
     * Draw the particles
     *
     * @param batch The batch to draw the images to
     */
    public void draw(SpriteBatch batch)
    {
        for (Particle p : particles) {
            p.draw(batch);
        }
    }
}
