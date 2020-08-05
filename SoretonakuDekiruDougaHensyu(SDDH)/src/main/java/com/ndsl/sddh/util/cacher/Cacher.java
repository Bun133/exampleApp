package com.ndsl.sddh.util.cacher;

import com.ndsl.sddh.image.AdvGImage;
import com.ndsl.sddh.movie.AdvGMovie;
import com.ndsl.sddh.movie.MovieFrame;
import org.bytedeco.opencv.opencv_core.Mat;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("SpellCheckingInspection")
public class Cacher {
    protected List<ImageCache> image_cache=new ArrayList<>();
    protected List<MatCache> mat_cache=new ArrayList<>();
    protected int index;
    protected long loadTimeMill=10;
    protected CacherAttitude attitude;
    public Cacher(CacherAttitude attitude){
        this.attitude=attitude;
    }

    public Cacher(CacherAttitude attitude,long loadTimeMill){
        this.attitude=attitude;
        this.loadTimeMill=loadTimeMill;
    }

    /**
     * Export Mode
     * @param attitude
     * @param movie
     */
    public Cacher(CacherAttitude attitude, AdvGMovie movie){
        this.attitude=attitude;
        int i=0;
        for(MovieFrame img:movie.frames){
            this.image_cache.add(new ImageCache(img.export().getImage().get(),i));
//            this.mat_cache.add(new MatCache(img.export().getImage().get(),i));
            i++;
        }
    }

    public void addImageChache(ImageCache cache) {
        image_cache.add(cache);
    }

    public void addMatCache(MatCache mat) {
        mat_cache.add(mat);
    }

    public void setPointer(int index){
        this.index=index;
    }

    public void next() {
        setPointer(++index);
    }

    public ImageCache getImageCache(){
        return image_cache.get(index);
    }

    public BufferedImage getImage(){
        return getImageCache().get();
    }

    public MatCache getMatCache(){
        return mat_cache.get(index);
    }

    public Mat getMat(){
        return getMatCache().get();
    }

    public void main_call(){
        switch (attitude){
            case LoadNear:
                //TODO
                break;
            case LoadOnlyForward:
                flushCacheUntilMirror(System.currentTimeMillis()+(loadTimeMill/2));
                loadCacheUntil(System.currentTimeMillis()+(loadTimeMill/2));
                break;
        }
    }
    public void loadCache(){loadCache(3);}
    public void loadCache(int load_files_count){
        for (int i = index; i < load_files_count+index; i++) {
            load(i);
        }
    }

    public void loadCacheUntil(long until_time_mill){
        if(until_time_mill < System.currentTimeMillis()) throw new IllegalArgumentException("Until Time is in the past.");
        int pointer= index;
        while(!(until_time_mill < System.currentTimeMillis())) {
            load(pointer++);
        }
    }

    public void loadCacheUntilMirror(long until_time_mill){
        if(until_time_mill < System.currentTimeMillis()) throw new IllegalArgumentException("Until Time is in the past.");
        int pointer= index;
        while(!(until_time_mill < System.currentTimeMillis())) {
            load(pointer--);
        }
    }

    public void flushCache(){flushCache(3);}
    public void flushCache(int flush_files_count){
        for (int i = index; i < flush_files_count+index; i++) {
            flush(i);
        }
    }

    public void flushCacheUntil(long until_time_mill){
        if(until_time_mill < System.currentTimeMillis()) throw new IllegalArgumentException("Until Time is in the past.");
        int pointer = index;
        while(!(until_time_mill < System.currentTimeMillis())) {
            flush(pointer++);
        }
    }

    public void flushCacheUntilMirror(long until_time_mill){
        if(until_time_mill < System.currentTimeMillis()) throw new IllegalArgumentException("Until Time is in the past.");
        int pointer = index-1;
        while(!(until_time_mill < System.currentTimeMillis())) {
            flush(pointer--);
        }
    }

    private void load(int pointer){
        if(image_cache.size()-1 < pointer || pointer < 0) return;
        image_cache.get(pointer).reload();
        mat_cache.get(pointer).reload();
    }

    private void flush(int pointer){
        if(image_cache.size()-1 < pointer || pointer < 0) return;
        image_cache.get(pointer).flush();
        mat_cache.get(pointer).flush();
    }
}
