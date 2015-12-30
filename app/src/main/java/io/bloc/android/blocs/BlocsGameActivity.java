package io.bloc.android.blocs;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.util.FPSLogger;
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

    private ITexture mAvatarTexture;
    private ITextureRegion mAvatarTR;


        //make a square/rectangular mazeMap
    private int[][] mazeMap = {
                    {1,1,1,1,1,1},
                    {1,1,0,0,1,1},
                    {1,0,0,0,0,1},
                    {1,0,0,9,0,1},
                    {1,0,1,1,0,1},
                    {1,1,1,1,1,1}
    };

    private Avatar mPlayerAvatar = null;



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

            mAvatarTexture = new BitmapTexture(getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("gtx/ph_avatar.png");
                }
            });

            mAvatarTexture.load();
            mAvatarTR = TextureRegionFactory.extractFromTexture(mAvatarTexture);

        } catch(IOException e) {
            Debug.e(e);
        }


    }

    @Override
    protected Scene onCreateScene() {

        Scene scene = createMaze();

        return scene;
    }

    private Scene createMaze() {

        this.getEngine().registerUpdateHandler(new FPSLogger());

            //get mazeMap
        int[][] map = mazeMap;

            //get the dimensions of the map
        int mapHeight = map.length;
        int mapLength = map[0].length;

            //get the screen height and length
        float screenLength = CAMERA_WIDTH;
        float screenHeight = CAMERA_HEIGHT;

            //divide the screen into equal parts for the mazeMap sprites
        float spriteHeight = screenHeight/mapHeight;
        float spriteLength = screenLength/mapLength;

            //assign sprite placement offset
        float spriteHeightOffset = spriteLength/2;
        float spriteLengthOffset = spriteHeight/2;

            //create a new scene for the map
        Scene scene = new Scene();

            //set the background color
        scene.setBackground(new Background(Color.CYAN));

            //place mazeMap sprites based on mazeMap array
        for(int i = mapHeight-1 ; i >= 0; i--) {
            for(int j  = 0; j < mapLength; j++) {

                if(map[i][j] ==  9 && mPlayerAvatar == null) {
                    mPlayerAvatar = makeAvatar(spriteLengthOffset + (j * spriteLength),
                            spriteHeightOffset + (((mapHeight - 1) - i) * spriteHeight));
                    mPlayerAvatar.setSize(spriteLength/2, spriteHeight/2);
                    mPlayerAvatar.setAvatarVelocity(1f,1f);
                    scene.attachChild(mPlayerAvatar);
                } else {

                    Block block = getBlock(
                            map[i][j],
                            spriteLengthOffset + (j * spriteLength),
                            spriteHeightOffset + (((mapHeight - 1) - i) * spriteHeight),
                            spriteLength,
                            spriteHeight);

                    if (block != null) {
                        scene.attachChild(block);
                    }
                }

            }
        }
            //return resulting scene
        return scene;
    }

    private Block getBlock(int spriteId, float spritePX, float spritePY, float spriteLength, float spriteHeight) {
        Block sprite = null;
        if(spriteId == 9) return null;

        switch (spriteId) {
            case 0:
                break;

            case 1:
                sprite = new Block(spriteId, spritePX, spritePY, mGreenBlock, getVertexBufferObjectManager() ) {
                    @Override
                    void onOverlap(Avatar overlappingAvatar) {
                        //null
                    }
                };
                sprite.setSize(spriteLength, spriteHeight);
                return sprite;

            default:
                break;


        }
        return sprite;
    }

    private Avatar makeAvatar(float pX, float pY) {

        return new Avatar(pX, pY, mAvatarTR, getVertexBufferObjectManager());

    }

    private void updateScene() {
        //get mazeMap
        int[][] map = mazeMap;

        //get the dimensions of the map
        int mapHeight = map.length;
        int mapLength = map[0].length;

        //get the screen height and length
        float screenLength = CAMERA_WIDTH;
        float screenHeight = CAMERA_HEIGHT;

        //divide the screen into equal parts for the mazeMap sprites
        float spriteHeight = screenHeight/mapHeight;
        float spriteLength = screenLength/mapLength;

        //assign sprite placement offset
        float spriteHeightOffset = spriteLength/2;
        float spriteLengthOffset = spriteHeight/2;

        //create a new scene for the map
        Scene scene = new Scene();

        //set the background color
        scene.setBackground(new Background(Color.CYAN));

        //place mazeMap sprites based on mazeMap array
        for(int i = mapHeight-1 ; i >= 0; i--) {
            for(int j  = 0; j < mapLength; j++) {

                Block block = getBlock(
                        map[i][j],
                        spriteLengthOffset + (j * spriteLength),
                        spriteHeightOffset + (((mapHeight - 1) - i) * spriteHeight),
                        spriteLength,
                        spriteHeight);

                if (block != null) {
                    scene.attachChild(block);
                }


            }
        }

        mPlayerAvatar.setPosition(
                mPlayerAvatar.getX() + mPlayerAvatar.getXVelocity(),
                mPlayerAvatar.getY() + mPlayerAvatar.getYVelocity());

        scene.attachChild(mPlayerAvatar);
    }

}
