package com.ndsl.sddh.util.cacher;

import java.awt.image.BufferedImage;

public class ImageCache extends CacheAble<BufferedImage> {
    private int index;
    public ImageCache(BufferedImage bufferedImage,int index) {
        super(bufferedImage);
    }

    @Override
    public void flush() {
        if(!isLoaded) return;
        this.isLoaded=false;
        this.data.flush();
    }

    @Override
    public void reload() {
        if(isLoaded) return;
        this.isLoaded=true;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public BufferedImage get() {
        if(!this.isLoaded) reload();
        return this.data;
    }
}
