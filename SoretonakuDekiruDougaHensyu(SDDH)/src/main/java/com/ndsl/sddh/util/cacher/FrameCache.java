package com.ndsl.sddh.util.cacher;

import org.bytedeco.javacpp.indexer.Indexer;
import org.bytedeco.javacv.Frame;
@Deprecated
public class FrameCache extends CacheAble<Frame> {
    protected int index;
    private Indexer indexer;
    public FrameCache(Frame frame,int index) {
        super(frame);
        this.index=index;
    }

    @Override
    public void flush() {
        this.isLoaded=false;
        this.indexer=this.data.createIndexer();
        this.indexer.release();
    }

    @Override
    public void reload() {
        //TODO
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
    public Frame get() {
        if(!isLoaded) reload();
        return data;
    }
}
