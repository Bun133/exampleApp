package com.ndsl.sddh.movie;

import com.ndsl.sddh.image.AdvGImage;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.FrameRecorder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DefaultMovieEncoder extends MovieEncoder {
    public static final DefaultMovieEncoder INSTANCE = new DefaultMovieEncoder();
    @Override
    public File Encode(AdvGMovie movie, EncodeSettings settings) throws Exception {
        File f=settings.exportFile;
        if(f.exists()){
//            throw new FileAlreadyExistsException("");
            f.delete();
        }
        if(f.isDirectory()){
            throw new IllegalArgumentException("Encode File is Directory!");
        }
        if(!f.createNewFile()){
            throw new Exception("[ERROR]Filed to create File");
        }
        List<AdvGImage> images=new ArrayList<AdvGImage>();
        for(MovieFrame frame:movie.frames){
            images.add(frame.export());
        }
        return ImagesToMovie(f, images.toArray(new AdvGImage[0]));
    }

    private File ImagesToMovie(File f,AdvGImage... images) throws FrameRecorder.Exception {
        FFmpegFrameRecorder recorder=new FFmpegFrameRecorder(f.getAbsolutePath(),images[0].getFrame().get().imageWidth,images[0].getFrame().get().imageHeight);
//        recorder.setFormat("ai");
        recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
        recorder.setVideoQuality(10000D);
        recorder.setFrameRate(60.00D);
        /*if(audioSample != 0) {
            mRecorder.setAudioQuality(audioQuality);//音声フレームレート
            mRecorder.setSampleRate(sampleAudioRateInHz);//音声サンプルレート
            mRecorder.setAudioCodec(avcodec.AV_CODEC_ID_AAC);//音声コーデック。大体AAC
        }*/
        recorder.setVideoOption("preset", "ultrafast");//エンコード速度設定
        recorder.setTimestamp(0);
//        recorder.setGopSize();
        recorder.start();
        final int length=images.length;
        int frameCount=0;
        final long startTime= System.currentTimeMillis();
        while(true){
            recorder.setFrameNumber(frameCount++);
            try {
                recorder.record(images[frameCount].getFrame().get());
//            recorder.recordSamples(audio);
            }catch(Exception e){
                System.out.println("Encode End");
                break;
            }

            System.out.println("Encode Passing..."+frameCount+"/"+length);
        }
        recorder.stop();
        System.out.println("Time:"+(System.currentTimeMillis()-startTime)+"ms");
        return f;
    }
    @Override
    public boolean isReady() {
        return true;
    }
}
