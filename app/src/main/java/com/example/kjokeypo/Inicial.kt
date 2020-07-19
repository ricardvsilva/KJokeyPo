package com.example.kjokeypo


import android.graphics.drawable.AnimationDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class Inicial : AppCompatActivity() {

    var xTempo1:Boolean = true
    var xTempo2:Boolean = true
    var xTempo3:Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.kjokeypo.R.layout.activity_inicial)

        tempoAbertura()

        val img = findViewById<ImageView>(R.id.imageView)
        img.setBackgroundResource(R.drawable.roda_animacao)

        val frameAnime = img.background as AnimationDrawable
        frameAnime.start()

    }
    fun tempoAbertura(){

        object : CountDownTimer(4000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

                if( millisUntilFinished/1000 <= 3.7 && xTempo1) {
                    playSound1() // Jo SOUND
                }
                if( millisUntilFinished/1000 <= 2.9 && xTempo2) {
                    playSound2() // Ken SOUND
                }
                if( millisUntilFinished/1000 <= 1.2 && xTempo3) {
                    playSound3() // Pon SOUND
                }
            }
            override fun onFinish() {
                finish()
            }
        }.start()
    }
    private fun playSound1() {
        val mediaPlayer: MediaPlayer = MediaPlayer.create(this, R.raw.jo)
        mediaPlayer.start()
        xTempo1 = false
    }
    private fun playSound2() {
        val mediaPlayer: MediaPlayer = MediaPlayer.create(this, R.raw.ken)
        mediaPlayer.start()
        xTempo2 = false
    }
    private fun playSound3() {
        val mediaPlayer: MediaPlayer = MediaPlayer.create(this, R.raw.pon)
        mediaPlayer.start()
        xTempo3 = false
    }
}