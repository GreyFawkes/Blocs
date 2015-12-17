package io.bloc.android.blocs;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.color.Color;

import java.io.IOException;

public class BlocsGameActivity extends SimpleBaseGameActivity{

    private static final int CAMERA_WIDTH = 400;
    private static final int CAMERA_HEIGHT = 400;

    @Override
    public EngineOptions onCreateEngineOptions() {
        final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
        return new EngineOptions(
                true, ScreenOrientation.LANDSCAPE_FIXED,
                new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT),
                camera);
    }

    @Override
    protected void onCreateResources() throws IOException {

    }

    @Override
    protected Scene onCreateScene() {
        Scene scene = new Scene();
        scene.setBackground(new Background(Color.RED));
        return scene;
    }
}
