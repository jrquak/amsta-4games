package com.amsta.puzzel.components.shufflegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by kohaj on 23/03/2017.
 */
public class ShufflePuzzle {
    private  ArrayList<PuzzlePiece> puzzlePieces;
    private ArrayList<Integer> randomPieces;

    private boolean gameFinished = false;
    private PuzzlePiece finalPiece;

    private final int PUZZLE_WIDTH_AND_HEIGHT = 4;
    private final int STANDARD_OFFSET = 63;
    private final int PUZZLE_PIECE_SIZE = 225;

    public static Texture puzzleImg;
    public static TextureRegion[] puzzlePieceImg = new TextureRegion[16];

    public ShufflePuzzle(Texture tex) {
        randomPieces = new ArrayList<Integer>();

        for (int i = 0; i < 16; i++)
            randomPieces.add(i);

        puzzleImg = tex;

        int count = 0;

        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++) {
                puzzlePieceImg[count] = new TextureRegion(puzzleImg, i * PUZZLE_PIECE_SIZE, (PUZZLE_PIECE_SIZE * 3) - (j * PUZZLE_PIECE_SIZE), PUZZLE_PIECE_SIZE, PUZZLE_PIECE_SIZE);
                count++;
            }

        createPuzzle();
    }

    public void createPuzzle() {
        puzzlePieces = new ArrayList<PuzzlePiece>();
        Collections.shuffle(randomPieces);

        int count = 0;

        for (int i = 0; i < PUZZLE_WIDTH_AND_HEIGHT; i++) {
            for (int j = 0; j < PUZZLE_WIDTH_AND_HEIGHT; j++) {
                Vector2 pos;
                pos = new Vector2(STANDARD_OFFSET + (i * PUZZLE_PIECE_SIZE), STANDARD_OFFSET + (j * PUZZLE_PIECE_SIZE));

                PuzzlePiece puzzlePiece = new PuzzlePiece(pos,randomPieces.get(count), puzzlePieceImg[randomPieces.get(count)]);

                if (randomPieces.get(count) == 12) {
                    puzzlePiece.visible = false;
                    puzzlePiece.targetPos = new Vector2(pos.x, pos.y);
                    finalPiece = puzzlePiece;
                }

                puzzlePieces.add(puzzlePiece);
                count++;
            }
        }

        //Check if there is a piece already in place, if so remake the puzzle
        for (PuzzlePiece puzzlepiece : puzzlePieces) {
            puzzlepiece.update();

            if (puzzlepiece.isInPosition) {
                createPuzzle();
                break;
            }
        }
    }

    public void update() {
        int piecesInPlace = 0;

        if (!gameFinished)
            for (PuzzlePiece puzzlepiece : puzzlePieces) {
                puzzlepiece.update();

                if (puzzlepiece.isInPosition)
                    piecesInPlace++;
            }

        if (Gdx.input.isKeyJustPressed(Input.Keys.D))
            SolveMyGamePlease();

        if (piecesInPlace == 15 && !gameFinished) {
            finishTheGame();
            gameFinished = true;
        }
    }

    private void SolveMyGamePlease() {
        for (PuzzlePiece puzzlepiece : puzzlePieces) {
            puzzlepiece.solve();
        }
    }

    private void finishTheGame() {
        SolveMyGamePlease();
        finalPiece.visible = true;
    }

    public void draw(SpriteBatch batch) {
        for (PuzzlePiece puzzlepiece : puzzlePieces) {
            puzzlepiece.draw(batch);
        }
    }
}
