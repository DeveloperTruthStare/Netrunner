package com.smith.netrunner.Corporation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.smith.netrunner.BaseGameObject;
import com.smith.netrunner.GameData.Server;
import com.smith.netrunner.GameData.ServerType;
import com.smith.netrunner.RootApplication;
import com.smith.netrunner.Target;
import com.smith.netrunner.UI.ClickCallbackListener;
import com.smith.netrunner.UIState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RunTarget extends BaseGameObject {
    public static final Map<ServerType, Texture> images;
    static {
        Map<ServerType, Texture> aMap = new HashMap<>();
        aMap.put(ServerType.BANK_RECORDS, new Texture("corporation/icons/bankIcon.png"));
        aMap.put(ServerType.RND, new Texture("corporation/icons/fileIcon.png"));
        aMap.put(ServerType.HONEY_POT, new Texture("corporation/icons/bankIcon.png"));
        aMap.put(ServerType.KEY_DATABASE, new Texture("corporation/icons/fileIcon.png"));

        images = Collections.unmodifiableMap(aMap);
    }

    private final Image serverImage;
    private final Stage stage;
    private final Target target;
    private final Server server;
    private final Animation<TextureRegion> playerIndicatorAnim;
    private float stateTime = 0.0f;
    private Texture iceTexture;
    private ArrayList<Image> iceImages;
    public RunTarget(RootApplication app, Server server) {
        super(app);
        this.server = server;

        serverImage = new Image( new Texture("corporation/icons/unknownServerIcon.png"));

        this.stage = new Stage();
        stage.addActor(serverImage);

        setSize((int)serverImage.getWidth(), (int)serverImage.getHeight());

        target = new Target(app);
        target.setPosition(1320, 250);
        target.setActive(false);
        addChild(target);

        Texture playerIndicator = new Texture("BattleSelect/playerIndicatorSpriteSheet.png");
        TextureRegion[][] tmp = TextureRegion.split(playerIndicator, 115, 115);
        TextureRegion[] sprites = new TextureRegion[16];
        int index = 0;
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                sprites[index++] = tmp[i][j];
            }
        }
        playerIndicatorAnim = new Animation<>((float) 1 / 24, sprites);

        iceTexture = new Texture("iceIcons/ice.png");
    }
    public Server getServer() {
        return this.server;
    }
    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        serverImage.setSize(width, height);
    }
    @Override
    public void setPosition(int x, int y) {
        super.setPosition(x, y);
        serverImage.setPosition(x, y);
        target.setPosition(1320, y + this.height/2);
    }

    @Override
    public void draw(float dt) {
        if (!isActive) return;
        stage.draw();

        if (isDragging && !server.hacked) {
            stateTime += dt;
            app.batch.draw(playerIndicatorAnim.getKeyFrame(stateTime, true),
                    this.x + (float)this.width/2, this.y + (float)this.height/2);
        }

        super.draw(dt);
    }
    public void targetServer() {
        target.LerpSize(1000, 200, 0.25f);
        target.setActive(true);
        server.revealed = true;
        this.serverImage.setDrawable(new SpriteDrawable(new Sprite(images.get(server.serverType))));
    }
    public void unTarget() {
        target.setActive(false);
    }
    @Override
    public void onDragEnter() {
        super.onDragEnter();
        UIState.hoveredServer = this;
    }
    @Override
    public void onDragExit() {
        super.onDragExit();
        if (UIState.hoveredServer == this) {
            UIState.hoveredServer = null;
        }
    }
}
