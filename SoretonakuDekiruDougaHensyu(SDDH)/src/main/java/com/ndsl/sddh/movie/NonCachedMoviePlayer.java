package com.ndsl.sddh.movie;

import com.ndsl.graphics.display.Display;
import com.ndsl.graphics.display.drawable.IDrawable;
import com.ndsl.graphics.display.drawable.synced.SyncedImageDrawable;
import com.ndsl.graphics.display.util.ExitAttitude;
import com.ndsl.graphics.pos.Pos;
import com.ndsl.graphics.pos.Rect;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Objects;

import static com.ndsl.sddh.movie.MovieDataTransfer.getAsImage;

public class NonCachedMoviePlayer implements IDrawable, ExitAttitude {
    protected FFmpegFrameGrabber grabber;
    protected String id;
    protected final Rect rect;
    public final double frameRate;
    public final long StartTime;
    public final double perFrameTime;

    public NonCachedMoviePlayer(String id,File file) throws FrameGrabber.Exception {
        this(id,file.getAbsolutePath());
    }

    public NonCachedMoviePlayer(String id,String path) throws FrameGrabber.Exception {
        this(id,path,new Pos(0,0));
    }

    public NonCachedMoviePlayer(String id, String path, Pos left_up) throws FrameGrabber.Exception {
        this.grabber=new FFmpegFrameGrabber(path);
        this.grabber.start();
        this.id=id;
        this.rect=new Rect(0,0,grabber.getImageHeight(),grabber.getImageWidth()).shift(left_up.x,left_up.y);
        this.frameRate=grabber.getFrameRate();
        this.StartTime=System.currentTimeMillis();
        this.perFrameTime=1000/frameRate;
    }

    protected SyncedImageDrawable i_d;

    @Override
    public void onDraw(Graphics graphics, Rect rect) {
        if(i_d==null) {
            try {
                this.i_d=new SyncedImageDrawable(safeGetAsImage(getNext()),id+"i_d");
            } catch (FrameGrabber.Exception e) {
                e.printStackTrace();
            }
        }
//        drawTo(graphics,currentImage,rect.left_up);
        if(isNextFrame()){
            try {
//                currentImage=nextImage;
//                nextImage=getAsImage(getNext());
                frameCount++;
                i_d.setImage(safeGetAsImage(getNext()));
            } catch (FrameGrabber.Exception e) {e.printStackTrace();}
        }
        i_d.onDraw(graphics,rect);
//        drawTo(graphics,Sumnaile,rect.left_up);
    }

    protected void drawTo(Graphics g,Frame f,Pos left_up){
        g.drawImage(getAsImage(f),left_up.x,left_up.y,null);
    }

    protected void drawTo(Graphics g,BufferedImage i,Pos left_up){
        g.drawImage(i,left_up.x,left_up.y,null);
    }

    @Override
    public Rect getShowingRect() {return rect;}

    @Override
    public boolean isShowing(Display display) {return true;}

    @Override
    public String getID() {return id;}

    public Frame getNext() throws FrameGrabber.Exception {
        Frame f=grabber.grab();
        assert f.image!=null;
        return f;
    }

    public int getNowFrame(){return (int) ((System.currentTimeMillis() - StartTime)/perFrameTime);}

    protected int frameCount=0;

    protected boolean isNextFrame(){return frameCount!=getNowFrame();}

    protected BufferedImage safeGetAsImage(Frame frame){
        return Objects.requireNonNull(getAsImage(frame));
    }

    @Override
    public void onClose(WindowEvent windowEvent) {
        try {
            grabber.close();
        } catch (FrameGrabber.Exception e) {
            e.printStackTrace();
        }
    }
}
