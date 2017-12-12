package com.amsta.puzzel.screens;

import com.amsta.puzzel.GdxStart;
import com.amsta.puzzel.components.Buttons.Button;
import com.amsta.puzzel.components.diffgame.DiffGameInfo;
import com.amsta.puzzel.events.ButtonClickedEvent;
import com.amsta.puzzel.interfaces.IButtonPressed;
import com.amsta.puzzel.manager.ScreenManager;
import com.amsta.puzzel.util.GlobalVals;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/**
 * Created by Yorick on 05-04-17.
 */
public class DiffLevelSelect extends BaseScreen {
    private Stage stage;

    private String fileNames[] = new String[]{
            "sint1",
            "test1",
            "frozen10"
    };

    private String levelNames[] = new String[]{
            "Sinterklaas",
            "Boerderij",
            "Frozen"
    };

    private final int MAX_SIZE = 100;
    private Texture bg;
    private com.amsta.puzzel.components.Buttons.TextButton btnBack;

    private final int PAD_LEFT_RIGHT = 20;
    private final int PAD_TOP = 70;
    private final int BUTTON_PAD_TOP = 10;
    private final int BUTTON_PAD_LEFT = 20;

    @Override
    public boolean initialize() {
        bg = new Texture("ui/background.jpg");
        btnBack = new com.amsta.puzzel.components.Buttons.TextButton(new Texture("ui/blue_button.png"), new Vector2(BUTTON_PAD_LEFT, GdxStart.V_HEIGHT - GlobalVals.getBtnHeight() - BUTTON_PAD_TOP), "Terug");
        btnBack.setListner(new IButtonPressed(){

            @Override
            public void buttonPressedHandler(ButtonClickedEvent event) {
                Gdx.input.setInputProcessor(null);
                ScreenManager.getInstance().goBackOneScreen();
            }
        });
        this.stage = new Stage(new StretchViewport(GdxStart.V_WIDTH, GdxStart.V_HEIGHT, GdxStart.camera));
        Gdx.input.setInputProcessor(this.stage);

        // Load font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/Roboto-Black.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = GlobalVals.FONT_SIZE_BTN;
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();

        // Create a style for the button. To use our texture and font
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.fontColor = Color.WHITE;
        textButtonStyle.up = new Image(new Texture("ui/blue_button.png")).getDrawable();

        // create labelstyle
        final Label.LabelStyle style = new Label.LabelStyle(font, Color.WHITE);
        final Table scrollTable = new Table();

        // Check all levels
        for (final String s : fileNames) {
            // load level info
            DiffGameInfo info = loadData(s);

            // Make label with name and total diff
            final Label levelName = new Label(info.lvlName + "\r\n" + info.getTotalDiff() + " verschillen", style);
            levelName.setAlignment(Align.center);
            levelName.setWrap(true);

            // Create image of level
            final Image img = new Image(new Texture("diff/" + s + "/top.png"));

            // Create play button and add change listener for onclick
            final TextButton play = new TextButton("Speel!", textButtonStyle);
            play.addListener(new ChangeListener() {

                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    System.out.println(s);
                    DiffGame game = new DiffGame();
                    game.setChosen(s);
                    Gdx.input.setInputProcessor(null);
                    ScreenManager.getInstance().changeScreen(game);
                }
            });

            // Add the layouyt components to the table
            scrollTable.add(img).maxWidth((GdxStart.V_WIDTH - PAD_LEFT_RIGHT) / 2);
            scrollTable.row().padTop(10);
            scrollTable.add(levelName).expandX().width((GdxStart.V_WIDTH - PAD_LEFT_RIGHT) / 2);
            scrollTable.row().padTop(10);
            scrollTable.add(play).expandX();
            scrollTable.row().padTop(10);
        }

        final Label title = new Label("Kies een puzzel", style);
        title.setAlignment(Align.center);
        title.setWrap(true);

        final ScrollPane scroller = new ScrollPane(scrollTable);
        // Without this the background will fade away
        scroller.setFadeScrollBars(false);
        final Table table = new Table();
        table.setFillParent(true);
        table.add(title).expandX().padLeft((GdxStart.V_WIDTH - PAD_LEFT_RIGHT) / 2).padTop(BUTTON_PAD_TOP).width((GdxStart.V_WIDTH - PAD_LEFT_RIGHT) / 2);
        table.row();

        table.add(scroller).padTop(PAD_TOP).padLeft(PAD_LEFT_RIGHT / 2).padRight(PAD_LEFT_RIGHT / 2).fill().expand();
        this.stage.addActor(table);
        return true;
    }

    @Override
    public void show()
    {
        Gdx.input.setInputProcessor(this.stage);
        isShowing = true;
    }


    @Override
    public void update() {
        btnBack.update();
    }

    @Override
    public void draw(SpriteBatch batch) {


        stage.act();

        // Use the stage batch. our batch will result in white blocks
        stage.getBatch().begin();
        stage.getBatch().draw(bg, 0, 0, GdxStart.V_WIDTH, GdxStart.V_HEIGHT);

        btnBack.draw((SpriteBatch)stage.getBatch());
        stage.getBatch().end();

        stage.draw();
    }

    @Override
    public void exitScreen() {

    }

    @Override
    public void dispose() {

    }

    private DiffGameInfo loadData(String file)
    {
        FileHandle handle = Gdx.files.internal("diff/"+file+"/info.json");
        if(handle.exists()) {
            Json json = new Json();
            DiffGameInfo info = json.fromJson(DiffGameInfo.class, handle);
            info.fileDir = file;
            String name = "";
            for(int i = 0; i < levelNames.length; i++)
            {
                if(fileNames[i].equals(file))
                {
                    name = levelNames[i];
                    break;
                }
            }

            info.lvlName = name;
            return info;
        }
        return null;
    }
}
