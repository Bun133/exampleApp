package com.ndsl.sddh.movie;

import java.io.File;
import java.nio.file.FileAlreadyExistsException;

public abstract class MovieEncoder {
    public abstract File Encode(AdvGMovie movie,EncodeSettings settings) throws Exception;
    public abstract boolean isReady();
}
