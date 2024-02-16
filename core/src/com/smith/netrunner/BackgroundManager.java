package com.smith.netrunner;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BackgroundManager extends BaseGameObject {
    private final Stage stage;
    public BackgroundManager(RootApplication app) {
        super(app);
        stage = new Stage();
        Image background = new Image(new Texture("BattleScreen/background/backgroundPerspective.png"));
        stage.addActor(background);
    }
    @Override
    public void draw(float dt) {
        super.draw(dt);
        if (!isActive) return;
        stage.draw();
    }
}
