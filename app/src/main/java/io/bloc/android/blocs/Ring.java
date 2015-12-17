package io.bloc.android.blocs;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import java.util.Stack;

/**
 * Created by Administrator on 12/14/2015.
 */
public class Ring extends Sprite {

    private int mWeight;
    private Stack mStack;
    private Sprite mTower;

    public Ring(
            int weight, float pX, float pY,
            ITextureRegion pTextureRegion,
            VertexBufferObjectManager pVertexBufferObjectManager) {

        super(pX,pY,pTextureRegion,pVertexBufferObjectManager);
        mWeight = weight;
    }

    public int getWeight() {
        return mWeight;
    }

    public Stack getStack() {
        return mStack;
    }

    public void setStack(Stack stack) {
        mStack = stack;
    }

    public Sprite getTower() {
        return mTower;
    }

    public void setTower(Sprite tower) {
        mTower = tower;
    }

}
