package com.welove.welove520.ptrlib;

import android.graphics.PointF;

/**
 * Created by Raomengyang on 17-8-15.
 * Email    : ericrao@welove-inc.com
 * Desc     :
 * Version  : 1.0
 */

public class PtrIndicator {

    private PointF lastMove = new PointF();

    private float mOffsetX;
    private float mOffsetY;
    private int mCurrentPosition = 0;
    private int mLastPosition = 0;


    public void onDownPressed(float x, float y) {
        lastMove.set(x, y);
    }

    public void onMove(float x, float y) {
        float offsetX = x - lastMove.x;
        float offsetY = y - lastMove.y;
        setOffsetXY(offsetX, offsetY);
    }

    private void setOffsetXY(float offsetX, float offsetY) {
        this.mOffsetX = offsetX;
        this.mOffsetY = offsetY;
    }

    public float getOffsetX() {
        return mOffsetX;
    }

    public float getOffsetY() {
        return mOffsetY;
    }

    public int getCurrentPosition() {
        return mCurrentPosition;
    }

    public int getLastPosition() {
        return mLastPosition;
    }

    public int getCurrentPosY() {
        return mCurrentPosition;
    }
}
