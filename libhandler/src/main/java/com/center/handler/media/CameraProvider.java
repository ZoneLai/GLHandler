/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.center.handler.media;

import android.graphics.Point;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;

import com.center.handler.log.AvLog;

import java.io.IOException;
import java.util.concurrent.Semaphore;

/**
 * CameraProvider 相机数据
 */
public class CameraProvider implements ITextureProvider {

    private Camera mCamera;
    private int cameraId = 1;
    private Semaphore mFrameSem;
    private String tag = getClass().getSimpleName();

    @Override
    public Point open(final SurfaceTexture surface) {
        final Point size = new Point();
        try {
            mFrameSem = new Semaphore(0);
            mCamera = Camera.open(cameraId);

            //小米5、5s这类奇葩手机不调用这句会导致图像预览方向错误
            mCamera.setParameters(mCamera.getParameters());

            mCamera.setPreviewTexture(surface);
            surface.setOnFrameAvailableListener(frameListener);
            Camera.Size s = mCamera.getParameters().getPreviewSize();
            mCamera.startPreview();
            size.x = s.height;
            size.y = s.width;
            AvLog.i(tag, "Camera Opened");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return size;
    }

    @Override
    public void close() {
        mFrameSem.drainPermits();
        mFrameSem.release();

        mCamera.stopPreview();
        mCamera.release();
        mCamera = null;
    }

    @Override
    public boolean frame() {
        try {
            mFrameSem.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public long getTimeStamp() {
        return -1;
    }

    @Override
    public boolean isLandscape() {
        return true;
    }

    private SurfaceTexture.OnFrameAvailableListener frameListener = new SurfaceTexture.OnFrameAvailableListener() {

        @Override
        public void onFrameAvailable(SurfaceTexture surfaceTexture) {
            AvLog.d(tag, "onFrameAvailable");
            mFrameSem.drainPermits();
            mFrameSem.release();
        }

    };

}
