package com.ndsl.sddh.movie;

import com.ndsl.sddh.image.AdvGImage;
import com.ndsl.sddh.image.DataSupplier;
import com.ndsl.sddh.movie.edit.MovieEditor;
import org.bytedeco.javacv.Frame;
import org.bytedeco.opencv.opencv_core.Mat;

import java.io.File;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.List;

import static org.bytedeco.opencv.global.opencv_imgcodecs.imwrite;

public class MovieFrame {
    public AdvGImage base_image;
    public List<MovieEditor<Mat>> mat_editors = new ArrayList<MovieEditor<Mat>>();
    public List<MovieEditor<Frame>> frame_editors = new ArrayList<MovieEditor<Frame>>();

    public MovieFrame(AdvGImage img){
        this.base_image=img;
    }

    public File export(String path) throws Exception {
        return export(new File(path));
    }

    public File export(File file) throws Exception {
        if(file.exists()){
            throw new FileAlreadyExistsException("[ERROR]Export File is already Exists!");
        }
        if(file.isDirectory()){
            throw new IllegalArgumentException("[ERROR]Export File is Directory!");
        }
        if (!file.createNewFile()){
            throw new Exception("[ERROR]Filed to create File");
        }
        AdvGImage img=export();
        img.clean();
        imwrite(file.getAbsolutePath(),img.getMat().get());
        return file;
    }

    public AdvGImage export() {
        DataSupplier<Frame> frame = base_image.getFrame();
        DataSupplier<Mat> mat = base_image.getMat();
        for(MovieEditor<Frame> editor:frame_editors){
            frame=editor.onEdit(frame);
        }
        frame.markDirt();
        frame.clean();
        for(MovieEditor<Mat> editor:mat_editors){
            mat=editor.onEdit(mat);
        }
        mat.markDirt();
        mat.clean();
        return base_image;
    }
}