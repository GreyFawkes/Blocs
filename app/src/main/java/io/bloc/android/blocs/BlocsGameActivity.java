package io.bloc.android.blocs;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.io.in.IInputStreamOpener;
import org.andengine.util.debug.Debug;

import java.io.IOException;
import java.io.InputStream;
import java.util.Stack;

public class BlocsGameActivity extends SimpleBaseGameActivity{

    private static final int CAMERA_HEIGHT = 480;
    private static final int CAMERA_WIDTH = 800;

    private Camera mCamera;
    public Scene mCurrentScene;
    private ITextureRegion mBackgroundTextureRegion, mTowerRegion, mRing1, mRing2, mRing3;
    private Sprite mTower1, mTower2, mTower3;
    private Stack mStack1, mStack2, mStack3;

    @Override
    protected void onCreateResources() throws IOException {
        try{
            ITexture backgroundTexture = new BitmapTexture(
                    this.getTextureManager(),
                    new IInputStreamOpener() {
                        @Override
                        public InputStream open() throws IOException {

                            return getAssets().open("gtx/background.png");
                        }
                    }
            );

            ITexture towerTexture = new BitmapTexture(
                    this.getTextureManager(),
                    new IInputStreamOpener() {
                        @Override
                        public InputStream open() throws IOException {
                            return getAssets().open("gtx/tower.png");
                        }
                    }
            );

            ITexture ring1Texture = new BitmapTexture(
                    this.getTextureManager(),
                    new IInputStreamOpener() {
                        @Override
                        public InputStream open() throws IOException {
                            return getAssets().open("gtx/ring1.png");
                        }
                    }
            );

            ITexture ring2Texture = new BitmapTexture(
                    this.getTextureManager(),
                    new IInputStreamOpener() {
                        @Override
                        public InputStream open() throws IOException {
                            return getAssets().open("gtx/ring2.png");
                        }
                    }
            );

            ITexture ring3Texture = new BitmapTexture(
                    this.getTextureManager(),
                    new IInputStreamOpener() {
                        @Override
                        public InputStream open() throws IOException {
                            return getAssets().open("gtx/ring3.png");
                        }
                    }
            );


            backgroundTexture.load();
            towerTexture.load();
            ring1Texture.load();
            ring2Texture.load();
            ring3Texture.load();

            mBackgroundTextureRegion = TextureRegionFactory.extractFromTexture(backgroundTexture);
            mTowerRegion = TextureRegionFactory.extractFromTexture(towerTexture);
            mRing1 = TextureRegionFactory.extractFromTexture(ring1Texture);
            mRing2 = TextureRegionFactory.extractFromTexture(ring2Texture);
            mRing3 = TextureRegionFactory.extractFromTexture(ring3Texture);

            mStack1 = new Stack();
            mStack2 = new Stack();
            mStack3 = new Stack();

        } catch (IOException e) {
            e.printStackTrace();
            Debug.e(e);
        }

    }

    @Override
    protected Scene onCreateScene() {
        final Scene scene = new Scene();
            //set up background
        Sprite backgroundSprite = new Sprite(CAMERA_WIDTH/2, CAMERA_HEIGHT/2, mBackgroundTextureRegion, getVertexBufferObjectManager());
        scene.attachChild(backgroundSprite);
            //add towers
        int towerVerticalOffset = 293;
        int towerHorizontalOffset = 14;
        mTower1 = new Sprite(192 + towerHorizontalOffset, towerVerticalOffset,
                mTowerRegion, getVertexBufferObjectManager());

        mTower2 = new Sprite(400 + towerHorizontalOffset, towerVerticalOffset,
                mTowerRegion, getVertexBufferObjectManager());

        mTower3 = new Sprite(604 + towerHorizontalOffset, towerVerticalOffset,
                mTowerRegion, getVertexBufferObjectManager());

        scene.attachChild(mTower1);
        scene.attachChild(mTower2);
        scene.attachChild(mTower3);


        int ring1VerticalOffset = -21;
        int ring2VerticalOffset = -23;
        int ring3VerticalOffset = -29;
        int ring1HorizontalOffset = 70;
        int ring2HorizontalOffset = 89;
        int ring3HorizontalOffset = 111;
        Ring ring1 = new Ring(1, 139 + ring1HorizontalOffset, 480-174 + ring1VerticalOffset, mRing1, getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                if( ((Ring) this.getStack().peek()).getWeight() != this.getWeight())
                    return false;
                this.setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2 ,
                        pSceneTouchEvent.getY() - this.getHeight() / 2);
                if(pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
                    checkForCollisionsWithTowers(this);
                }
                return true;
            }
        };
        Ring ring2 = new Ring(2, 118 + ring2HorizontalOffset, 480-212 + ring2VerticalOffset, mRing2, getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                if( ((Ring) this.getStack().peek()).getWeight() != this.getWeight())
                    return false;
                this.setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2 ,
                        pSceneTouchEvent.getY() - this.getHeight() / 2);
                if(pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
                    checkForCollisionsWithTowers(this);
                }
                return true;
            }
        };
        Ring ring3 = new Ring(3, 97 + ring3HorizontalOffset, 480-255 + ring3VerticalOffset, mRing3, getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                if( ((Ring) this.getStack().peek()).getWeight() != this.getWeight())
                    return false;
                this.setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2 ,
                        pSceneTouchEvent.getY() - this.getHeight() / 2);
                if(pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
                    checkForCollisionsWithTowers(this);
                }
                return true;
            }
        };
        scene.attachChild(ring1);
        scene.attachChild(ring2);
        scene.attachChild(ring3);

        mStack1.add(ring3);
        mStack1.add(ring2);
        mStack1.add(ring1);

        ring1.setStack(mStack1);
        ring2.setStack(mStack1);
        ring3.setStack(mStack1);
        ring1.setTower(mTower1);
        ring2.setTower(mTower1);
        ring3.setTower(mTower1);

        scene.registerTouchArea(ring1);
        scene.registerTouchArea(ring2);
        scene.registerTouchArea(ring3);

        scene.setTouchAreaBindingOnActionDownEnabled(true);

        return scene;
    }

    @Override
    public EngineOptions onCreateEngineOptions() {
        mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
        return new EngineOptions(
                true,
                ScreenOrientation.LANDSCAPE_FIXED,
                new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT),
                mCamera
        );
    }

    private void checkForCollisionsWithTowers(Ring ring) {
        Stack stack = null;
        Sprite tower = null;

            //if the ring is going on the poll check if there are any rings on the poll
            //place the ring if there are no rings on the poll else
            //only place if the top ring has a larger weight
        if(ring.collidesWith(mTower1) && (mStack1.size() == 0 ||
            ring.getWeight() < ((Ring) mStack1.peek()).getWeight() )) {
            stack = mStack1;
            tower = mTower1;
        } else if ( ring.collidesWith(mTower2) && (mStack2.size() == 0 ||
            ring.getWeight() < ((Ring) mStack2.peek()).getWeight() )) {
            stack = mStack2;
            tower = mTower2;
        } else if (ring.collidesWith(mTower3) && (mStack3.size() == 0 ||
            ring.getWeight() < ((Ring) mStack3.peek()).getWeight() )) {
            stack = mStack3;
            tower = mTower3;
        } else {

            //else place with the last tower
            stack = ring.getStack();
            tower = ring.getTower();
        }

        ring.getStack().remove(ring);
        if(stack != null && tower != null && stack.size() == 0) {
            ring.setPosition(
                    tower.getX() + tower.getWidth()/2 - ring.getWidth()/2,
                    tower.getY() + tower.getHeight() - ring.getHeight());

        } else if(stack != null && tower != null && stack.size() > 0) {
            ring.setPosition(
                    tower.getX() + tower.getWidth()/2 - ring.getWidth()/2,
                    ((Ring) stack.peek()).getY() - ring.getHeight());
        }

        stack.add(ring);
        ring.setStack(stack);
        ring.setTower(tower);

    }


}
