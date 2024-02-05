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
    private ArrayList<Image> images;
    private final Stage stage;
    private final Image playerBackground;
    private final Image corpBackground;
    private final Image semiTransBlack;
    private int corpPos = 1920;
    private int corpSpeed = 1000;
    private ArrayList<Integer> sizes;
    private Random rand;
    public BackgroundManager(RootApplication app) {
        super(app);
        // Load images;
        rand = new Random();
        stage = new Stage();
        sizes = new ArrayList<>();
        playerBackground = new Image(new Texture("BattleScreen/background/greenbackground.png"));
        corpBackground = new Image(new Texture("BattleScreen/background/blueHalfBackground.png"));
        semiTransBlack = new Image(new Texture("BattleScreen/background/semiTransparentBlack.png"));

        stage.addActor(playerBackground);

        images = new ArrayList<Image>();
        for(String file : listFilesUsingJavaIO("BattleScreen/backgroundIcons/")) {
            Image image = new Image(new Texture("BattleScreen/backgroundIcons/" + file));
            stage.addActor(image);
            images.add(image);
            image.setPosition(0, 0);
            sizes.add(0);
        }
        stage.addActor(corpBackground);
        corpBackground.setPosition(1920*5, 0);
        stage.addActor(semiTransBlack);

        reset();
    }
    public void reset() {
        for(int i = 0; i < images.size(); ++i) {
            int size = rand.nextInt(1, 200);
            images.get(i).setSize(1920 * (100 + size)/100, 1080 * (100 + size)/100);
            sizes.set(i, size);
        }
        corpBackground.setPosition(1920*5, 0);
        corpPos = 1920*2;
    }
    @Override
    public void draw(float dt) {
        stage.draw();
        updateCorpBackground(dt);
        for(int i = 0; i < sizes.size(); ++i) {
            int size = sizes.get(i);

            size -= 2   ;
            if (size < 0)
                size = 0;
            sizes.set(i, size);

            images.get(i).setSize(1920 * (100 + size)/100, 1080 * (100 + size)/ 100);
        }
    }

    private void updateCorpBackground(float dt) {
        if (corpPos > 0) {
            corpPos -= dt * corpSpeed;
            if (corpPos < 0) {
                corpPos = 0;
            }
            corpBackground.setPosition(corpPos, 0);
        }

    }

    public Set<String> listFilesUsingJavaIO(String dir) {
        return Stream.of(new File(dir).listFiles())
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .collect(Collectors.toSet());
    }
}
