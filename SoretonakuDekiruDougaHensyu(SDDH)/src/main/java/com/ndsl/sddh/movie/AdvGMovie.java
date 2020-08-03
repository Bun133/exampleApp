package com.ndsl.sddh.movie;

import com.ndsl.sddh.image.AdvGImage;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AdvGMovie {
    public List<MovieFrame> frames;
    public AdvGMovie(MovieFrame[] frames) {
        this.frames = Arrays.asList(frames);
    }

    public AdvGMovie(List<MovieFrame> images){
        this.frames=images;
    }

    public static AdvGMovie build(File file) throws FrameGrabber.Exception {
        var inputGrabber = new FFmpegFrameGrabber(file);
        inputGrabber.start();
        List<Frame> frameList=new ArrayList<>();
        while(true){
            try{
                frameList.add(inputGrabber.grab());
            }catch(Exception e){
                break;
            }
        }
        inputGrabber.stop();
        inputGrabber.release();
        return new AdvGMovie(frameList.stream().
                filter(Objects::nonNull)
                .map(AdvGImage::new)
                .map(MovieFrame::new)
                .toArray(MovieFrame[]::new));
    }
}
