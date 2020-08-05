package com.ndsl.sddh.movie;

import com.ndsl.graphics.display.Display;
import com.ndsl.graphics.display.drawable.IDrawable;
import com.ndsl.graphics.display.fps.FPSLimiter;
import com.ndsl.graphics.pos.Rect;

import java.awt.*;
import java.awt.image.BufferedImage;
@Deprecated
public class MoviePlayer implements IDrawable {
    public final AdvGMovie movie;
    public final BufferedImage Sumnaile;
    public final int fps;
    public final int frameShift;
    public final long perFrameTime;
    public final String id;
    public final long startTime;

    public MoviePlayer(String id,AdvGMovie movie){
        this(id,movie, (int) movie.frameRate);
    }

    public MoviePlayer(String id,AdvGMovie movie, int fps) {
        this(id,movie, fps,0);
    }

    public MoviePlayer(String id,AdvGMovie movie, int fps,int frameShift) {
        this.movie=movie;
        this.frameShift=frameShift;
        this.fps=fps;
        this.perFrameTime=1000/fps;
        this.id=id;
        this.startTime=System.currentTimeMillis();
        this.Sumnaile=movie.getAsImage(0);
    }

    @Override
    public void onDraw(Graphics graphics, Rect rect) {
        graphics.drawImage(getNowImage(),0,0,null);
        System.out.println("NowCount:"+getNowFrameCount()+"/"+movie.frames.size());
    }

    private int getNowFrameCount(){
        return (int) ((System.currentTimeMillis() - startTime)/perFrameTime);
    }

    private BufferedImage getNowImage(int frameCount){
        return movie.getAsImage(frameCount);
    }

    private BufferedImage getNowImage(){return getNowImage(getNowFrameCount());}

    @Override
    public Rect getShowingRect() {
        return new Rect(0,0,Sumnaile.getWidth(),Sumnaile.getHeight());
    }

    @Override
    public boolean isShowing(Display display) {
        return display.isShowing(getShowingRect());
    }

    @Override
    public String getID() {
        return id;
    }
}
