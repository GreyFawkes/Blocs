package io.bloc.android.blocs;

import android.hardware.SensorManager;
import android.util.Log;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.util.FPSLogger;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.sensor.acceleration.AccelerationData;
import org.andengine.input.sensor.acceleration.IAccelerationListener;
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

public class BlocsGameActivity extends SimpleBaseGameActivity implements IAccelerationListener {


    // ============ Constants ============
    private static final int CAMERA_WIDTH = 700;
    private static final int CAMERA_HEIGHT = 400;

    // ============ field variables =================
    private ITexture mGreen;
    private ITextureRegion mGreenBlock;

    private ITexture mAvatarTexture;
    private ITextureRegion mAvatarTR;


        //make a square/rectangular mazeMap
    private int[][] mazeMap = {
                    {1,1,1,1,1,1,1,1,1,1,1},
                    {1,1,0,0,0,0,0,0,1,0,1},
                    {1,0,0,0,0,1,1,0,1,0,1},
                    {1,0,0,9,0,1,1,0,0,0,1},
                    {1,0,1,1,0,0,0,0,0,0,1},
                    {1,1,1,1,1,1,1,1,1,1,1}
    };

    private Avatar mPlayerAvatar = null;
    private PhysicsWorld mPhysicsWorld;

    private FixtureDef wallFixtureDefaultDef = PhysicsFactory.createFixtureDef(1,0.5f,0.5f);
    private FixtureDef avatarFixtureDef = PhysicsFactory.createFixtureDef(0.5f, 0.5f, 0.5f);

    private Body mAvatar;

    private float mXAccel;
    private float mYAccel;


    //// TODO: 12/30/2015 bodies and physics


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


        mPhysicsWorld = new PhysicsWorld(new Vector2(0, 0), false);

        Scene scene = createMaze();

        Log.i("Test", "test");

        scene.registerUpdateHandler(mPhysicsWorld);

        return scene;
    }

    @Override
    public void onAccelerationChanged(AccelerationData pAccelerationData) {
        setAccel(pAccelerationData.getX(), pAccelerationData.getY());
        calcAvatarVelocity(.5f);

    }

    @Override
    public void onAccelerationAccuracyChanged(AccelerationData pAccelerationData) {
        Log.i("Test", "test2");
    }

    @Override
    public synchronized void onResumeGame() {
        super.onResumeGame();
        this.enableAccelerationSensor(this);
    }

    @Override
    public synchronized void onPauseGame() {
        super.onPauseGame();
        this.disableAccelerationSensor();
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


//        mPlayerAvatar = makeAvatar(200, 200);
//        mPlayerAvatar.setSize(spriteLength/2, spriteHeight/2);
////
//
//        scene.attachChild(mPlayerAvatar);


            //place mazeMap sprites based on mazeMap array
        for(int i = mapHeight-1 ; i >= 0; i--) {
            for(int j  = 0; j < mapLength; j++) {

                if(map[i][j] ==  9 && mPlayerAvatar == null) {
                    mPlayerAvatar = makeAvatar(spriteLengthOffset + (j * spriteLength),
                            spriteHeightOffset + (((mapHeight - 1) - i) * spriteHeight));
                    mPlayerAvatar.setSize(spriteLength/2, spriteHeight/2);

                    mAvatar = PhysicsFactory.createCircleBody(mPhysicsWorld, mPlayerAvatar, BodyDef.BodyType.DynamicBody, avatarFixtureDef);
                    mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(mPlayerAvatar, mAvatar, true, true));
                    scene.attachChild(mPlayerAvatar);
                } else {

                    BlocSprite block = getBlocSprite(
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

    private BlocSprite getBlocSprite(int spriteId, float spritePX, float spritePY, float spriteLength, float spriteHeight) {
        BlocSprite sprite = null;
        if(spriteId == 9) return null;

        switch (spriteId) {
            case 0:
                break;

            case 1:
                sprite = new BlocSprite(spriteId, spritePX, spritePY, mGreenBlock, getVertexBufferObjectManager());
                sprite.setSize(spriteLength, spriteHeight);

                    //create a body for the wall
                Body wallBody = PhysicsFactory.createBoxBody(mPhysicsWorld, sprite, BodyDef.BodyType.KinematicBody, wallFixtureDefaultDef);
                wallBody.setUserData("wall");

                return sprite;

            default:
                break;


        }
        return sprite;
    }

    private Avatar makeAvatar(float pX, float pY) {

        return new Avatar(pX, pY, mAvatarTR, getVertexBufferObjectManager());

    }

    private void setAccel(float xAccel, float yAccel) {
        mXAccel = xAccel;
        mYAccel = yAccel;
    }

    private void calcAvatarVelocity(float accelerationMultiplier) {

        //relative accelerations for the avatar in game
        float relativeXAccel = (mXAccel/SensorManager.GRAVITY_EARTH)*accelerationMultiplier;
        float relativeYAccel = (mYAccel/SensorManager.GRAVITY_EARTH)*accelerationMultiplier;

        float newXVelocity = mAvatar.getLinearVelocity().x + relativeXAccel;
        float newYVelocity = mAvatar.getLinearVelocity().y + relativeYAccel;

        //mPlayerAvatar.setAvatarVelocity(newXVelocity, newYVelocity);
        mAvatar.setLinearVelocity(newXVelocity, newYVelocity);

    }

}
