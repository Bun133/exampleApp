package com.ndsl.sddh.image;

import com.ndsl.graphics.display.drawable.img.GImage;
import org.bytedeco.javacv.Frame;
import org.bytedeco.opencv.opencv_core.Mat;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.function.Supplier;

import static com.ndsl.sddh.movie.MovieDataTransfer.getAsFrame;
import static com.ndsl.sddh.movie.MovieDataTransfer.getAsMat;

public class AdvGImage {
    public AdvGImage(Mat mat){
        this(mat,getAsFrame(mat));
    }

    public AdvGImage(Frame frame){
        this(getAsMat(frame),frame);
    }

    public AdvGImage(Mat mat,Frame frame){
        this.image_mat=mat;
        this.image_frame=frame;
    }

    protected Mat image_mat;
    protected Frame image_frame;
    protected DataSupplier<Mat> matDataSupplier;
    protected DataSupplier<Frame> frameDataSupplier;

    /**
     * <p>if you changed mat,You have to mark Dirty!</p>
     * @return Mat Supplier
     */
    public DataSupplier<Mat> getMat(){
        return matDataSupplier==null?genMat():matDataSupplier;
    }

    private DataSupplier<Mat> genMat() {
        this.matDataSupplier=new DataSupplier<Mat>(image_mat);
        return matDataSupplier;
    }

    /**
     * <p>if you changed frame,You have to mark Dirty!</p>
     * @return Frame Supplier
     */
    public DataSupplier<Frame> getFrame(){
        return frameDataSupplier==null?genFrame():frameDataSupplier;
    }

    private DataSupplier<Frame> genFrame(){
        this.frameDataSupplier=new DataSupplier<Frame>(image_frame);
        return frameDataSupplier;
    }

    /**
     * @return if frame or mat is dirty
     */
    public boolean isDirty(){
        return frameDataSupplier.isNeedClean()||matDataSupplier.isNeedClean();
    }

    /**
     * Check Dirty Frame or Mat.
     * and if <p>one</p> of these is Dirty,
     * copy that,ant the other'll change to that one.
     */
    public void clean(){
        if(!isDirty()) return;
        if(frameDataSupplier.isNeedClean()&&matDataSupplier.isNeedClean()){
            throw new IllegalStateException("Editing Data Rejecting");
        }else{
            if(frameDataSupplier.isNeedClean()){
                frameDataSupplier.clean();
                matDataSupplier.setData(getAsMat(frameDataSupplier.get()));
            }else{
                matDataSupplier.clean();
                frameDataSupplier.setData(getAsFrame(matDataSupplier.get()));
            }
        }
    }
}
