package io.bloc.android.blocs;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.color.Color;
import org.andengine.util.adt.io.in.IInputStreamOpener;
import org.andengine.util.debug.Debug;

import java.io.IOException;
import java.io.InputStream;

public class BlocsGameActivity extends SimpleBaseGameActivity{


    // ============ Constants ============
    private static final int CAMERA_WIDTH = 400;
    private static final int CAMERA_HEIGHT = 400;

    // ============ field variables =================
    private ITexture mGreen;
    private ITextureRegion mGreenBlock;


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
        try {
            mGreen = new BitmapTexture(getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("gtx/green_block_with_dot.png");
                }
            });

            mGreen.load();
            mGreenBlock = TextureRegionFactory.extractFromTexture(mGreen);

        } catch(IOException e) {
            Debug.e(e);
        }


    }

    @Override
    protected Scene onCreateScene() {
        Scene scene = new Scene();
        scene.setBackground(new Background(Color.RED));

        Sprite sprite = new Sprite(200, 200, mGreenBlock, getVertexBufferObjectManager());
        sprite.setSize(50,50);
        scene.attachChild(sprite);

        return scene;
    }
}
