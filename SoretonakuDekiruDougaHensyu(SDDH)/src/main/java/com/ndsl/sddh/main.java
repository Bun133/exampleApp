package com.ndsl.sddh;

import com.ndsl.graphics.display.drawable.base.Drawable;
import com.ndsl.graphics.display.drawable.img.GImage;
import com.ndsl.graphics.display.drawable.non_sync.ImageDrawable;
import com.ndsl.graphics.display.sub.SubWindow;
import com.ndsl.graphics.pos.Rect;
import com.ndsl.sddh.image.AdvGImage;
import com.ndsl.sddh.movie.AdvGMovie;
import com.ndsl.sddh.movie.DefaultMovieEncoder;
import com.ndsl.sddh.movie.EncodeSettings;
import com.ndsl.sddh.movie.MovieFrame;
import com.ndsl.sddh.util.SectionManager;
import com.ndsl.sddh.window.LoadingWindow;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class main {
    public static void main(String[] args) throws Exception {
        new main().loading();
    }

    public void encodeTest() throws Exception {
        File file=new File("src\\main\\resources\\bun.jpg");
        AdvGMovie movie=new AdvGMovie(genMovie(600,file));
        File output=new File("src\\main\\resources\\output.avi");
        System.out.println("Encode Start");
        DefaultMovieEncoder.INSTANCE.Encode(movie,new EncodeSettings.Properties().setExportFile(output).build());
        System.out.println("Encode End");
    }

    public MovieFrame[] genMovie(int sec,File file){
        MovieFrame[] frames=new MovieFrame[sec * 60];
        for (int i = 0; i < sec * 60; i++) {
            frames[i]=new MovieFrame(new AdvGImage(file));
        }
        return frames;
    }

    public void run() throws InterruptedException {
        //        Display d=new Display("NDSL/SDDH/Loading",3,new Rect(100,100,400,400));
        LoadingWindow loadWindow = new LoadingWindow("NDSL/SDDH/Loading",3,new Rect(100,100,400,400));
        SectionManager.instance.startSection("Loading");
        SubWindow sub=SectionManager.instance.debugSubWindow();
//        while(true){
//            if(loadWindow.limiter.onUpdate()) loadWindow.update();
//            if(sub.limiter.onUpdate()) {SectionManager.instance.debugUpdate();sub.update();}
//        }
        loadWindow.update();
        sub.update();
        //** SomeThing Load **//
        Thread.sleep(1000);
        System.out.println("Load End");
        SectionManager.instance.endSection("Loading");
        SectionManager.instance.startSection("HomeView");
        while(true){
            if(loadWindow.limiter.onUpdate()) loadWindow.update();
            if(sub.limiter.onUpdate()) sub.update();
        }
    }

    public void loading() throws IOException {
        LoadingWindow loadWindow=new LoadingWindow("NDSL/IR Loading",3,new Rect(0,0,1920/2,1080/2).shift(1920/2,1080/2).shift(-1920/4,-1080/4));
        GImage loading_image=GImage.get(new File("src\\main\\resources\\loading.png"));
        loading_image.zoom(0.5d);
        loadWindow.addDrawable(new Drawable(new ImageDrawable(loading_image.export(),"load_image")));
        loadWindow.update();
    }
}
