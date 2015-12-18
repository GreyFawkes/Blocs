package io.bloc.android.blocs;

import org.andengine.entity.sprite.TiledSprite;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created by Administrator on 12/18/2015.
 */
public abstract class Block extends TiledSprite {

    String mBlockId;

    public Block(String blockId, float pX, float pY,
                 ITiledTextureRegion iTiledTextureRegion,
                 VertexBufferObjectManager vertexBufferObjectManager) {
        super(pX, pY, iTiledTextureRegion, vertexBufferObjectManager);
        setBlockId(blockId);
    }

    private void setBlockId(String blockId) {
        mBlockId = blockId;
    }

    public String getBlockId() {
        return mBlockId;
    }

        //action to be performed when the avatar would overlap this block
    abstract void onOverlap(Block overlappingBlock);


}
