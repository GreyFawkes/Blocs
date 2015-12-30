package io.bloc.android.blocs;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created by Administrator on 12/18/2015.
 */
public abstract class Block extends Sprite {

    int mBlockId;

    public Block(int blockId, float pX, float pY,
                 ITextureRegion iTextureRegion,
                 VertexBufferObjectManager vertexBufferObjectManager) {
        super(pX, pY, iTextureRegion, vertexBufferObjectManager);
        setBlockId(blockId);
    }

    private void setBlockId(int blockId) {
        mBlockId = blockId;
    }

    public int getBlockId() {
        return mBlockId;
    }

        //action to be performed when the avatar would overlap this block
    abstract void onOverlap(Avatar overlappingBlock);


}
