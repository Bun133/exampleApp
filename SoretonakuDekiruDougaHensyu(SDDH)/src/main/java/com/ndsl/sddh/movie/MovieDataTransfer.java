package com.ndsl.sddh.movie;

import com.ndsl.graphics.display.drawable.img.GImage;
import com.ndsl.graphics.display.drawable.mov.GMovie;
import com.ndsl.sddh.image.AdvGImage;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.opencv.opencv_core.Mat;

import java.io.File;

import static org.bytedeco.opencv.global.opencv_imgcodecs.imread;


public class MovieDataTransfer {
    public static GImage[] getAllImage(GMovie movie){
       return movie.data;
    }

    public static final OpenCVFrameConverter.ToMat to_mat= new OpenCVFrameConverter.ToMat();
    public static Mat getAsMat(Frame frame){return to_mat.convert(frame);}

    public static Frame getAsFrame(Mat mat){
        return to_mat.convert(mat);
    }

    public static Mat getAsMat(String path){
        return imread(path);
    }

    public static Mat getAsMat(File file){
        return getAsMat(file.getAbsolutePath());
    }
}
