package com.ndsl.sddh.movie;

import com.ndsl.graphics.display.drawable.mov.GMovie;

import java.io.File;

public abstract class MovieEncoder {
    public abstract File Encode(GMovie movie,EncodeSettings settings);
    public abstract boolean isReady();
}
