package com.ndsl.sddh.util;

import com.ndsl.graphics.display.Display;
import com.ndsl.graphics.display.drawable.base.Drawable;
import com.ndsl.graphics.display.drawable.synced.SyncedStringDrawable;
import com.ndsl.graphics.display.sub.SubWindow;
import com.ndsl.graphics.pos.Rect;

import java.util.ArrayList;
import java.util.List;

public class SectionManager {
    public static SectionManager instance = new SectionManager();
    protected List<SectionEntry> sectionList=new ArrayList<>();

    public void startSection(SectionEntry e){
        sectionList.add(e);
    }

    public void startSection(Section s){
        startSection(new SectionEntry(s));
    }

    public void startSection(String section_name){
        startSection(new Section(section_name));
    }

    public void endSection(SectionEntry e){
        e.sectionEnd();
    }

    public void endSection(Section s){
        SectionEntry e = get(s);
        if(e==null) return;
        e.sectionEnd();
    }

    public void endSection(String s){
        SectionEntry e = get(s);
        if(e==null) return;
        e.sectionEnd();
    }

    public SectionEntry get(Section s){
        for(SectionEntry e:sectionList){
            if(s.equals(e.section)) return e;
        }
        return null;
    }

    public SectionEntry get(String s){
        return get(new Section(s));
    }

    public class SectionEntry {
        public Section section;
        private SectionEntry(Section s) {
            this.section = s;
            startTime=System.currentTimeMillis();
        }
        public long startTime;
        public long endTime;
        public long duringTime;
        public boolean isRunning=true;

        public void sectionEnd(){
            this.endTime=System.currentTimeMillis();
            isRunning=false;
            this.duringTime=endTime-startTime;
        }

        @Override
        public boolean equals(Object obj) {
            if(obj instanceof SectionEntry){
                return ((SectionEntry) obj).section.equals(this.section);
            }else if(obj instanceof Section){
                return ((Section)obj).equals(this.section);
            }else{
                return false;
            }
        }
    }
    protected SyncedStringDrawable ssd=null;
    public SyncedStringDrawable getDebug(Rect r){
        return ssd==null?genDebug(r):ssd;
    }

    public void debugUpdate(){
        if(ssd==null){return;}else{
            ssd.setText(genDebugStrings());
        }
    }

    protected SyncedStringDrawable genDebug(Rect r){
        ssd=new SyncedStringDrawable(genDebugStrings(),r,"SectionManager.debug.ssd");
        return ssd;
    }

    private String genDebugStrings() {
        StringBuilder sb=new StringBuilder();
        for(SectionEntry e:sectionList){
            sb.append("Name:");
            sb.append(e.section.section_name);
            sb.append("\n");
            sb.append("Running:");
            sb.append(e.isRunning);
            sb.append("\n");
            sb.append("StartTime:");
            sb.append(e.startTime);
            sb.append("ms");
            if(!e.isRunning) {
                sb.append("\n");
                sb.append("EndTime:");
                sb.append(e.endTime);
                sb.append("ms");
                sb.append("\n");
                sb.append("duringTime:");
                sb.append(e.duringTime);
                sb.append("ms");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public SubWindow debugSubWindow(){
        SubWindow sub=new SubWindow("SectionManager/Debug",3,new Rect(200,200,500,400));
        sub.addDrawable(new Drawable(genDebug(new Rect(20,50,0,0))));
        return sub;
    }
}
