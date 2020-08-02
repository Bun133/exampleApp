package com.ndsl.sddh.movie;

import com.ndsl.sddh.image.AdvGImage;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AdvGMovie {
    public AdvGImage[] images;
    public AdvGMovie(AdvGImage[] data) {
        this.images=data;
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
                .toArray(AdvGImage[]::new));
    }
}
