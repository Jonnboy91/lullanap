package com.jonnesten.lullanap

import android.media.MediaRecorder
import android.util.Log
import java.io.IOException


class SoundMeter() {
    private val EMA_FILTER = 0.6

    private var mRecorder: MediaRecorder? = null
    private var mEMA = 0.0

    fun start() {
        if (mRecorder == null) {
            mRecorder = MediaRecorder().apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
                setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
                setAudioEncodingBitRate(16);
                setAudioSamplingRate(44100);
                setOutputFile("/dev/null/")
                try {
                    prepare()
                    //Making sure that the code has enough time to prepare the mRecorder
                    Thread.sleep(1000);
                    start()
                    mEMA = 0.0
                } catch (e: IllegalStateException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun stop() {
        if (mRecorder != null) {
            mRecorder!!.stop()
            mRecorder!!.reset()
            mRecorder!!.release()
            mRecorder = null
        }
    }

    fun getTheAmplitude(): Double {
        return if (mRecorder != null) mRecorder!!.maxAmplitude.toDouble() else 0.0
    }

    fun getAmplitude(): Double {
        return if (mRecorder != null) mRecorder!!.maxAmplitude / 2700.0 else 0.0
    }

    fun getAmplitudeEMA(): Double {
        val amp = getAmplitude()
        mEMA = EMA_FILTER * amp + (1.0 - EMA_FILTER) * mEMA
        return mEMA
    }
}