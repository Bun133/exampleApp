package com.ndsl.sddh.window;

import com.ndsl.graphics.Debugger;
import com.ndsl.graphics.display.Display;
import com.ndsl.graphics.display.key.KeyInputHandler;
import com.ndsl.graphics.display.mouse.MouseInputHandler;
import com.ndsl.graphics.pos.Rect;
import org.jetbrains.annotations.NotNull;

public class LoadingWindow extends Display {
    public LoadingWindow(String title, int bufferSize, @NotNull Rect displayBound){
        super();
        this.setTitle(title);
        this.setBounds(displayBound.left_up.x,displayBound.left_up.y,displayBound.getWidth(),displayBound.getHeight());
        //*!!THIS!!*//
        this.setUndecorated(true);
        this.setVisible(true);
        this.createBufferStrategy(bufferSize);
        this.bufferSize=bufferSize;
        this.bufferStrategy = this.getBufferStrategy();
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.addWindowListener(this.exitManager.WL);
        this.keyHandler=new KeyInputHandler(this);
        this.mouseInputHandler=new MouseInputHandler(this);
        this.debugger=new Debugger(limiter,keyHandler,mouseInputHandler);
        Start_Time=System.currentTimeMillis();
    }
}
