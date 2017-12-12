package com.amsta.puzzel.components.shufflegame;

import com.amsta.puzzel.manager.InputManager;
import com.amsta.puzzel.util.ScreenUtil;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by kohaj on 23/03/2017.
 */
public class PuzzlePiece {
    protected Rectangle boundingBox;
    protected boolean alreadyFired = false;
    private Texture tex;
    private TextureRegion texR;
    private static int counter;

    private static Vector2[] correctPositions = new Vector2[16];

    private Vector2 movePos, pos, oldPos;
    public static Vector2 targetPos;
    private int myCorrectPlace = 0;

    public boolean visible = true;
    private boolean isMoving = false;
    public boolean isInPosition = false;

    private final int PUZZLE_PIECE_SIZE = 225;

    private int texX = 0;
    private int texY = 0;

    public PuzzlePiece(Vector2 pos, int correctPlace, TextureRegion tr) {
        myCorrectPlace = correctPlace;
        texR = tr;
        int count = 0;

        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++) {
                correctPositions[count] = new Vector2(63 + (i * PUZZLE_PIECE_SIZE), 63 + (j * PUZZLE_PIECE_SIZE));
                count++;
            }

        this.pos = new Vector2(pos.x, pos.y);
        movePos = new Vector2(pos.x, pos.y);
        oldPos = new Vector2(425, 50);

        if (targetPos == null)
            targetPos = new Vector2(425, 50);

        boundingBox = new Rectangle(pos.x, pos.y, texR.getRegionWidth(), texR.getRegionHeight());
    }

    public  void update() {
        boundingBox = new Rectangle(pos.x, pos.y, texR.getRegionWidth(), texR.getRegionHeight());

        isInPosition = pos.x == correctPositions[myCorrectPlace].x && pos.y == correctPositions[myCorrectPlace].y;

        if (isMoving){
            pos.x = (float)Math.round(lerp(pos.x, movePos.x, 0.6f));
            pos.y = (float)Math.round(lerp(pos.y, movePos.y, 0.6f));

            if (pos.x == movePos.x && pos.y == movePos.y) {
                isMoving = false;
            }
        }

        if(InputManager.getInstance().isJustPressed() && !isMoving)
        {
            Vector2 touchPos = ScreenUtil.touchToScreenPos();

            if(boundingBox.contains(touchPos.x, touchPos.y) && !alreadyFired)
            {
                piecePressed();
                alreadyFired = true;
            }
        }
        else
        {
            alreadyFired = false;
        }
    }

    private float lerp(float x1, float x2, float t1) {
        return x1 + (x2 - x1) * t1;
    }

    private void piecePressed()
    {
        //Gdx.app.log("targetPos", "target.x = " + targetPos.x + " target.y = " + targetPos.y);
        if (Vector2.dst(pos.x, pos.y, targetPos.x, targetPos.y) <= PUZZLE_PIECE_SIZE && pos.x == movePos.x && pos.y == movePos.y && visible) {
            oldPos = new Vector2(pos.x, pos.y);
            movePos = new Vector2(targetPos.x, targetPos.y);
            targetPos = new Vector2(oldPos.x, oldPos.y);

            isMoving = true;
            counter++;
        }
    }

    public void solve() {
        pos = new Vector2(correctPositions[myCorrectPlace].x, correctPositions[myCorrectPlace].y);
        visible = true;
    }

    public void draw(SpriteBatch batch) {
        if (isInPosition) {
            batch.setColor(Color.WHITE);
        } else {
            batch.setColor(Color.LIGHT_GRAY);
        }

        if (visible) {
            batch.draw(texR, pos.x, pos.y);
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
