package com.ndsl.sddh.image;

import com.ndsl.sddh.movie.MovieDataTransfer;
import org.bytedeco.javacv.Frame;
import org.bytedeco.opencv.opencv_core.Mat;

import java.awt.image.BufferedImage;
import java.io.File;

import static com.ndsl.sddh.movie.MovieDataTransfer.*;

public class AdvGImage {
    public AdvGImage(String path){
        this(MovieDataTransfer.getAsMat(path));
    }

    public AdvGImage(File file){
        this(MovieDataTransfer.getAsMat(file));
    }

    public AdvGImage(Mat mat){
        this(mat,getAsFrame(mat));
    }

    public AdvGImage(Frame frame){
        this(getAsMat(frame),frame);
    }

    public AdvGImage(Mat mat,Frame frame){
        this(mat,frame, getAsImage(frame));
    }

    public AdvGImage(Mat mat,Frame frame,BufferedImage image){
        this.image_mat=mat;
        this.image_frame=frame;
        this.bufferedImage=image;
    }

    protected Mat image_mat;
    protected Frame image_frame;
    protected BufferedImage bufferedImage;
    protected DataSupplier<Mat> matDataSupplier;
    protected DataSupplier<Frame> frameDataSupplier;
    protected DataSupplier<BufferedImage> bufferedImageDataSupplier;
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
     * @return Image that this frame
     */
    public DataSupplier<BufferedImage> getImage(){
        return bufferedImageDataSupplier==null?genImage():bufferedImageDataSupplier;
    }

    private DataSupplier<BufferedImage> genImage() {
        this.bufferedImageDataSupplier=new DataSupplier<BufferedImage>(bufferedImage);
        return bufferedImageDataSupplier;
    }

    /**
     * @return if frame or mat is dirty
     */
    public boolean isDirty(){
        return frameDataSupplier.isNeedClean()||matDataSupplier.isNeedClean()||bufferedImageDataSupplier.isNeedClean();
    }

    /**
     * Check Dirty Frame or Mat.
     * and if <p>one</p> of these is Dirty,
     * copy that,ant the other'll change to that one.
     */
    public void clean(){
        if(!isDirty()) return;
        if(isReject(frameDataSupplier.isNeedClean(),matDataSupplier.isNeedClean(),bufferedImageDataSupplier.isNeedClean())){
            throw new IllegalStateException("Editing Data Rejecting");
        }else{
            if(frameDataSupplier.isNeedClean()){
                frameDataSupplier.clean();
                matDataSupplier.setData(getAsMat(frameDataSupplier.get()));
                bufferedImageDataSupplier.setData(getAsImage(frameDataSupplier.get()));
            }else if(matDataSupplier.isNeedClean()){
                matDataSupplier.clean();
                frameDataSupplier.setData(getAsFrame(matDataSupplier.get()));
                bufferedImageDataSupplier.setData(getAsImage(matDataSupplier.get()));
            }else{
                bufferedImageDataSupplier.clean();
                matDataSupplier.setData(getAsMat(bufferedImageDataSupplier.get()));
                frameDataSupplier.setData(getAsFrame(bufferedImageDataSupplier.get()));
            }
        }
    }

    private boolean isReject(boolean b1,boolean b2,boolean b3){
        if(b1) return b2||b3;
        if(b2) return b3;
        return false;
    }
}
