package io.bloc.android.blocs;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created by Administrator on 12/18/2015.
 */
public class BlocSprite extends Sprite {

    int mBlocSpriteId;

    public BlocSprite(int blocSpriteId, float pX, float pY,
                 ITextureRegion iTextureRegion,
                 VertexBufferObjectManager vertexBufferObjectManager) {
        super(pX, pY, iTextureRegion, vertexBufferObjectManager);
        setBlocSpriteId(blocSpriteId);
    }

    private void setBlocSpriteId(int blocSpriteId) {
        mBlocSpriteId = blocSpriteId;
    }

    public int getBlocSpriteId() {
        return mBlocSpriteId;
    }

        //action to be performed when the avatar would overlap this block


}
