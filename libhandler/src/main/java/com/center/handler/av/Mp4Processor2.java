package com.center.handler.av;

import com.center.handler.media.Mp4Provider;
import com.center.handler.media.SurfaceEncoder;
import com.center.handler.media.SurfaceShower;
import com.center.handler.media.VideoSurfaceProcessor;
import com.center.handler.media.hard.IHardStore;
import com.center.handler.media.hard.StrengthenMp4MuxStore;

/**
 * Mp4Processor2 用于处理Mp4文件
 */
public class Mp4Processor2 {

    private VideoSurfaceProcessor mTextureProcessor;
    private Mp4Provider mMp4Provider;
    private SurfaceShower mShower;
    private SurfaceEncoder mSurfaceStore;
    private IHardStore mMuxer;

    public Mp4Processor2() {
        //用于视频混流和存储
        mMuxer = new StrengthenMp4MuxStore(true);

        //用于预览图像
        mShower = new SurfaceShower();
        mShower.setOutputSize(720, 1280);

        //用于编码图像
        mSurfaceStore = new SurfaceEncoder();
        mSurfaceStore.setStore(mMuxer);

        //用于音频
//        mSoundRecord=new SoundRecorder(mMuxer);
        mMp4Provider = new Mp4Provider();
        mMp4Provider.setStore(mMuxer);

        //用于处理视频图像
        mTextureProcessor = new VideoSurfaceProcessor();
        mTextureProcessor.setTextureProvider(mMp4Provider);
        mTextureProcessor.addObserver(mShower);
        mTextureProcessor.addObserver(mSurfaceStore);
    }

    public void setSurface(Object surface) {
        mShower.setSurface(surface);
    }

    public void setInputPath(String path) {
        mMp4Provider.setInputPath(path);
    }

    public void setOutputPath(String path) {
        mMuxer.setOutputPath(path);
    }

    public void setPreviewSize(int width, int height) {
        mShower.setOutputSize(width, height);
    }

    public void open() {
        mTextureProcessor.start();
    }

    public void close() {
        mTextureProcessor.stop();
    }

    public void startPreview() {
        mShower.open();
    }

    public void stopPreview() {
        mShower.close();
    }

    public void startRecord() {
        mSurfaceStore.open();
//        mSoundRecord.start();
    }

    public void stopRecord() {
        mSurfaceStore.close();
//        mSoundRecord.stop();
    }

}
