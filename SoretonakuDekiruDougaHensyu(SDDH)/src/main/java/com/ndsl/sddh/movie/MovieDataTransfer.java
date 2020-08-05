package com.ndsl.sddh.movie;

import com.ndsl.graphics.display.drawable.img.GImage;
import com.ndsl.graphics.display.drawable.mov.GMovie;
import com.ndsl.sddh.image.AdvGImage;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.opencv.opencv_core.Mat;

import java.awt.image.BufferedImage;
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

    public static final Java2DFrameConverter Java2DConverter = new Java2DFrameConverter();
    public static BufferedImage getAsImage(Frame frame){return Java2DConverter.convert(frame);}
    public static BufferedImage getAsImage(Mat mat){
        return getAsImage(getAsFrame(mat));
    }

    public static Frame getAsFrame(BufferedImage image){return Java2DConverter.convert(image);}
    public static Mat getAsMat(BufferedImage image){return getAsMat(Java2DConverter.convert(image));}
}
