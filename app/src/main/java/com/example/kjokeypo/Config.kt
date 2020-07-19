package com.example.kjokeypo

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.content.Context
import android.media.AudioManager
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.util.DisplayMetrics
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_config.*
import java.util.*


val Hab_Musicas = "Musicas"
val salvaHabMusicas = "true"

val Hab_Toques = "Toques"
val salvaHabToques = "true"

var tela2: Intent? = null

var AUX_IDIOMA = 1

class Config : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config)

        // RECUPERAR STATUS DO SWITCH MUSICA
        val sharedPreferencesMusica = getSharedPreferences(Hab_Musicas, 0)
        val musicaSwt = sharedPreferencesMusica.getBoolean(salvaHabMusicas, true)
        swtMusica.isChecked = musicaSwt

        // RECUPERAR STATUS DO SWITCH TOQUE
        val sharedPreferencesToque = getSharedPreferences(Hab_Toques, 0)
        val ToqueSwt = sharedPreferencesToque.getBoolean(salvaHabToques, true)
        swtToque.isChecked = ToqueSwt

        // AO RECEBER A STRING JOGO, ESSA TELA DEVE FICAR POR 100ms APENAS.
        var telaQueChamou = intent.getStringExtra("nome")?:"Nao Veio"

        if(telaQueChamou == "Jogo") {
            object : CountDownTimer(100, 100) {
                override fun onTick(millisUntilFinished: Long) { }
                override fun onFinish() { finish() }
            }.start()
        }

        val intentIdioma = Intent(this, MainActivity::class.java)

        btnUS.setOnClickListener({
            setAppLocale("en")
            setContentView(R.layout.activity_config)
            intentIdioma.putExtra("idioma", "en")
            startActivityForResult(intentIdioma, AUX_IDIOMA)
        })

        btnBr.setOnClickListener({
            setAppLocale("pt")
            setContentView(R.layout.activity_config)
            intentIdioma.putExtra("idioma", "pt")
            startActivityForResult(intentIdioma, AUX_IDIOMA)
        })
    }
    override fun onStop() {
        super.onStop()

        val sharedPreferencesMusica = getSharedPreferences(Hab_Musicas, 0)
        val auxSalvaMusica = sharedPreferencesMusica.edit()
        auxSalvaMusica.putBoolean(salvaHabMusicas, swtMusica.isChecked)
        auxSalvaMusica.apply()

        val sharedPreferencesToque = getSharedPreferences(Hab_Toques, 0)
        val auxSalvaToque = sharedPreferencesToque.edit()
        auxSalvaToque.putBoolean(salvaHabToques, swtToque.isChecked)
        auxSalvaToque.apply()

        tela2 = Intent(applicationContext, MainActivity::class.java)
        tela2!!.putExtra("idioma", "olá")

    }
    override fun finish() {
        val dados = Intent()

        val mAlramMAnager  = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        // switch Music On or OFF

        if(swtMusica.isChecked) {
            dados.putExtra("retorno", "true")
        }else{
            dados.putExtra("retorno", "false")
        }

        // switch System Tone On or OFF
        if (swtToque.isChecked){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_SYSTEM, AudioManager.ADJUST_UNMUTE, 0)
            } else {
                mAlramMAnager.setStreamMute(AudioManager.STREAM_SYSTEM, false)
            }
        }else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_SYSTEM, AudioManager.ADJUST_MUTE, 0)
            } else {
                mAlramMAnager.setStreamMute(AudioManager.STREAM_SYSTEM, true)
            }
        }

        setResult(Activity.RESULT_OK, dados)
        super.finish()
    }
    private fun setAppLocale(localeCode: String) {
        val resources: Resources = resources
        val padraoMetrico: DisplayMetrics = resources.displayMetrics
        val configuracaoAtualSistema: Configuration = resources.configuration
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuracaoAtualSistema.setLocale(Locale(localeCode.toLowerCase()))
        } else {
            configuracaoAtualSistema.locale = Locale(localeCode.toLowerCase())
        }
        resources.updateConfiguration(configuracaoAtualSistema, padraoMetrico)
    }
    fun voltarJogo(view: View) {
        btnAuxLiga = true
        finish()
    }
}

