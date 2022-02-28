package com.jonnesten.lullanap

import android.media.MediaRecorder
import java.io.IOException


class SoundMeter() {
    private val EMA_FILTER = 0.6

    private var mRecorder: MediaRecorder? = null
    private var mEMA = 0.0

    fun start() {
        if (mRecorder == null) {
            mRecorder = MediaRecorder()
            mRecorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
            mRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            mRecorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            mRecorder!!.setOutputFile("/dev/null/")
            try {
                mRecorder!!.prepare()
            } catch (e: IllegalStateException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            mRecorder!!.start()
            mEMA = 0.0
        }
    }

    fun stop() {
        if (mRecorder != null) {
            mRecorder!!.stop()
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