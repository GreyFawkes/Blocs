package io.bloc.android.blocs;

import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created by Administrator on 12/29/2015.
 */
public class Avatar extends Sprite {

    //item velocity
    float mXVelocity;
    float mYVelocity;

    //physics handler
    public PhysicsHandler mPhysicsHandler;

    public Avatar(
            float pX,
            float pY,
            ITextureRegion iTextureRegion,
            VertexBufferObjectManager vertexBufferObjectManager) {

        super(pX, pY, iTextureRegion, vertexBufferObjectManager);
        setAvatarVelocity(0, 0);
        mPhysicsHandler = new PhysicsHandler(this);
        this.registerUpdateHandler(this.mPhysicsHandler);

    }


    public void setAvatarVelocity(float xVelocity, float yVelocity) {
        mXVelocity = xVelocity;
        mYVelocity = yVelocity;
    }

    public float getXVelocity() {
        return mXVelocity;
    }

    public float getYVelocity() {
        return mYVelocity;
    }

    @Override
    protected void onManagedUpdate(float pSecondsElapsed) {
        mPhysicsHandler.setVelocity(getXVelocity(),getYVelocity());
        super.onManagedUpdate(pSecondsElapsed);
    }
}
