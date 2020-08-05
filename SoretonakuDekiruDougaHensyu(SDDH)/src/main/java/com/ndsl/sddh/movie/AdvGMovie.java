package com.ndsl.sddh.movie;

import com.ndsl.graphics.display.Display;
import com.ndsl.sddh.image.AdvGImage;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AdvGMovie {
    public double frameRate=-1;
    public List<MovieFrame> frames;
    public AdvGMovie(MovieFrame[] frames) {
        this.frames = Arrays.asList(frames);
    }
    public AdvGMovie(MovieFrame[] frames,double frameRate) {
        this.frames = Arrays.asList(frames);
        this.frameRate=frameRate;
    }

    public AdvGMovie(List<MovieFrame> images){
        this.frames=images;
    }
    public AdvGMovie(List<MovieFrame> images,double frameRate){
        this.frames=images;
        this.frameRate=frameRate;
    }

    public static AdvGMovie build(File file) throws FrameGrabber.Exception {
        var inputGrabber = new FFmpegFrameGrabber(file);
        inputGrabber.start();
        final int size = inputGrabber.getLengthInFrames();
        MovieFrame[] frames=new MovieFrame[size];
        for (int i = 0; i < size; i++) {
            System.out.println("Loading... "+(i+1)+"/"+size);
            frames[i] = new MovieFrame(new AdvGImage(inputGrabber.grabImage()));
        }
//        while(true){
//            try{
//
//                count++;
//
//            }catch(Exception e){
//                break;
//            }
//        }
        final double frameRate=inputGrabber.getFrameRate();
        inputGrabber.stop();
        return new AdvGMovie(frames, frameRate);
    }

    public BufferedImage[] getAsImages(){
        BufferedImage[] images = new BufferedImage[frames.size()];
        for(int i=0;i<frames.size();i++){
            images[i] = frames.get(i).base_image.getImage().get();
        }
        return images;
    }

    public BufferedImage getAsImage(int index){
        return frames.get(index).base_image.getImage().get();
    }

    public void writeToDisplay(Display d,int frame_count){
        d.getGraphic().drawImage(getAsImage(frame_count),0,0,null);
    }
}
