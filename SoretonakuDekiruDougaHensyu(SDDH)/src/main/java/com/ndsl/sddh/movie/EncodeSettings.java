package com.ndsl.sddh.movie;

import java.io.File;

public class EncodeSettings {
    public File exportFile;
    public String Encoding_format;

    private EncodeSettings(){}
    public static class Properties{
        public Properties(){settings=new EncodeSettings();}
        protected EncodeSettings settings;
        public Properties setExportFile(File file){settings.exportFile=file;return this;}
        public EncodeSettings build(){
            return settings;
        }
    }
}
