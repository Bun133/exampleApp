package com.ndsl.sddh;

import com.ndsl.graphics.display.Display;
import com.ndsl.graphics.display.sub.SubWindow;
import com.ndsl.graphics.pos.Rect;
import com.ndsl.sddh.util.SectionManager;
import com.ndsl.sddh.window.LoadingWindow;

public class main {
    public static void main(String[] args) throws InterruptedException {
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
}
