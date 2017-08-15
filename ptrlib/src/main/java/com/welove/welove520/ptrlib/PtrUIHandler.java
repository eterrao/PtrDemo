package com.welove.welove520.ptrlib;

/**
 *
 */
public interface PtrUIHandler {

    /**
     * When the content view has reached top and refresh has been completed, view will be reset.
     *
     * @param frame
     */
    public void onUIReset(PTRLayout frame);

    /**
     * prepare for loading
     *
     * @param frame
     */
    public void onUIRefreshPrepare(PTRLayout frame);

    /**
     * perform refreshing UI
     */
    public void onUIRefreshBegin(PTRLayout frame);

    /**
     * perform UI after refresh
     */
    public void onUIRefreshComplete(PTRLayout frame);

    public void onUIPositionChange(PTRLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator);
}
