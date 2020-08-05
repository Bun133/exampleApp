package com.ndsl.sddh.util.cacher;

import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.MatAllocator;

public class MatCache extends CacheAble<Mat>{
    private int index;
    public MatAllocator allocator=null;
    public MatCache(Mat mat,int index) {
        super(mat);
        this.index=index;
    }

    @Override
    public void flush() {
        if(!isLoaded) return;
        this.isLoaded=false;
        this.allocator=this.data.allocator();
        this.data.deallocate();
    }

    @Override
    public void reload() {
        if(isLoaded) return;
        this.isLoaded=true;
        this.data.allocator(allocator);
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public void setIndex(int index) {
        this.index=index;
    }

    @Override
    public Mat get() {
        if(!this.isLoaded) reload();
        return null;
    }
}
