package com.example.kjokeypo

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

var btnAuxLiga:Boolean = true
var btnAuxRoda:Boolean = true
var auxBtnVeredito:Int = 0
var passaValorSalvoParaTextBox = true

var contaVitorias2:Int = 0
var contaEmpate:Int = 0
var contaDerrotas2:Int = 0

// strings com erro

private var gravaVitoria ="text1"
private var gravaEmpate ="text2"
private var gravaDerrota ="text3"

val SHARED_PREFS = "sharedPrefs"

class Jogo : AppCompatActivity() {

    var MUSICA_CONFIG = 1
    var retAct:String = ""

    private var mp: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jogo)

        // salvar placares utilizando Shared preferences
        val sharedPreferences = getSharedPreferences(SHARED_PREFS, 0)

        var txtSalvaVitoria = sharedPreferences.getString(gravaVitoria, "0").toString()
        var valorContagemVitorias: TextView = findViewById<TextView>(R.id.contaVitorias)
        valorContagemVitorias.text = txtSalvaVitoria

        var txtSalvaEmpate = sharedPreferences.getString(gravaEmpate, "0").toString()
        var valorContagemEmpates: TextView = findViewById<TextView>(R.id.contaEmpates)
        valorContagemEmpates.text = txtSalvaEmpate

        var txtSalvaDerrota = sharedPreferences.getString(gravaDerrota,"0").toString()
        var valorContagemDerrotas: TextView = findViewById<TextView>(R.id.contaDerrotas)
        valorContagemDerrotas.text = txtSalvaDerrota

        // Instanciando arquivo de media para som de escolha
        mp = MediaPlayer.create(this, R.raw.choose)

        // Instanciando botões e Radio Buttons da tela
        var btnRodar:Button = findViewById(R.id.btnRodar)
        var rdoPedra:RadioButton = findViewById(R.id.radioUm)
        var rdoPapel:RadioButton = findViewById(R.id.radioDois)
        var rdoTesoura:RadioButton = findViewById(R.id.radioTres)

        // Instanciando Animações
        val imgRodaJogo = findViewById<ImageView>(R.id.imgRodaJogo)
        imgRodaJogo.setBackgroundResource(R.drawable.roda_jogo)
        val frameAnime = imgRodaJogo.background as AnimationDrawable

        // Vá até tela Configurações e pegue o Status do switch musica.
        val intentMusica = Intent(this@Jogo, Config::class.java)
        intentMusica.putExtra("nome", "Jogo")
        startActivityForResult(intentMusica, MUSICA_CONFIG)

        // BOTÃO RODAR
        btnRodar.setOnClickListener(View.OnClickListener {

            var textView2:TextView = findViewById(R.id.textView2)

            if (retAct == "true") {
                mp?.start()
                mp?.isLooping ?: true
            }

            findViewById<RadioButton>(R.id.radioUm).isEnabled = true
            findViewById<RadioButton>(R.id.radioDois).isEnabled = true
            findViewById<RadioButton>(R.id.radioTres).isEnabled = true
            textView2.text = "VERSUS"

            val imgRodaJogo = findViewById<ImageView>(R.id.imgRodaJogo)
            imgRodaJogo.setBackgroundResource(R.drawable.roda_jogo)
            val frameAnime = imgRodaJogo.background as AnimationDrawable

            if(btnAuxRoda) {
                frameAnime.start ()
                btnAuxRoda = false
            }else if (!btnAuxRoda){
                frameAnime.stop ()
                btnAuxRoda = true
            }
        })
        // RADIO PEDRA
        rdoPedra.setOnClickListener(View.OnClickListener {
            if (retAct == "true") {
                mp?.pause()
                mp?.stop()
                mp?.prepare()
            }
            btnAuxRoda = true
            auxBtnVeredito = 1
            veredito()
            paraTudo()
        })
        // RADIO PAPEL
        rdoPapel.setOnClickListener(View.OnClickListener {
            if (retAct == "true") {
                mp?.pause()
                mp?.stop()
                mp?.prepare()
            }
            btnAuxRoda = true
            auxBtnVeredito = 2
            veredito()
            paraTudo()
        })
        // RADIO TESOURA
        rdoTesoura.setOnClickListener(View.OnClickListener {
            if (retAct == "true") {
                mp?.pause()
                mp?.stop()
                mp?.prepare()
            }
            btnAuxRoda = true
            auxBtnVeredito = 3
            veredito()
            paraTudo()
        })
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == MUSICA_CONFIG) {
            if (data != null) {
                if (data.hasExtra("retorno")) {
                    retAct =  (data.extras?.getString("retorno") ?: "false").toString()
                }
            }
        }
    }
    override fun onStop() {
        super.onStop()
        val sharedPreferences1 = getSharedPreferences(SHARED_PREFS, 0)
        val editor1 = sharedPreferences1.edit()
        var valorContagemVitorias: TextView = findViewById<TextView>(R.id.contaVitorias)
        editor1.putString(gravaVitoria, valorContagemVitorias.text.toString())
        editor1.apply()
        editor1.commit()
        var valorContagemEmpates: TextView = findViewById<TextView>(R.id.contaEmpates)
        editor1.putString(gravaEmpate, valorContagemEmpates.text.toString())
        editor1.apply()
        editor1.commit()
        var valorContagemDerrotas: TextView = findViewById<TextView>(R.id.contaDerrotas)
        editor1.putString(gravaDerrota, valorContagemDerrotas.text.toString())
        editor1.apply()
        editor1.commit()
    }
    override fun onDestroy(){
        super.onDestroy()
        val sharedPreferences1 = getSharedPreferences(SHARED_PREFS, 0)
        val editor1 = sharedPreferences1.edit()
        var valorContagemVitorias: TextView = findViewById<TextView>(R.id.contaVitorias)
        editor1.putString(gravaVitoria, valorContagemVitorias.text.toString())
        editor1.apply()
        editor1.commit()
        var valorContagemEmpates: TextView = findViewById<TextView>(R.id.contaEmpates)
        editor1.putString(gravaEmpate, valorContagemEmpates.text.toString())
        editor1.apply()
        editor1.commit()
        var valorContagemDerrotas: TextView = findViewById<TextView>(R.id.contaDerrotas)
        editor1.putString(gravaDerrota, valorContagemDerrotas.text.toString())
        editor1.apply()
        editor1.commit()

        if (retAct == "true") {
            mp?.pause()
            mp?.stop()
            mp?.release()
        }
    }
    fun voltarJogo(view: View) {
        btnAuxLiga = true
        finish()
    }
    fun inicioJogo(view: View) {

        var btnInicio:Button
        btnInicio = findViewById(R.id.btnInicioJogo)
        var textView2:TextView = findViewById(R.id.textView2)
        textView2.text = "VERSUS"

        if (btnAuxLiga){
            btnInicio.text = "Parar"

            findViewById<View>(R.id.btnRodar).isEnabled = true

            btnAuxLiga = false
        }
        else if (!btnAuxLiga){

            val imgRodaJogo = findViewById<ImageView>(R.id.imgRodaJogo)
            imgRodaJogo.setBackgroundResource(R.drawable.roda_jogo)
            val frameAnime = imgRodaJogo.background as AnimationDrawable
            frameAnime.stop ()
            btnAuxRoda = true

            btnInicio.text = "Início"
            findViewById<View>(R.id.radioUm).isEnabled = false
            findViewById<View>(R.id.radioDois).isEnabled = false
            findViewById<View>(R.id.radioTres).isEnabled = false
            findViewById<View>(R.id.btnRodar).isEnabled = false
            btnAuxLiga = true

        }
    }
    fun btnZerar(view: View) {
        var xcontaVitorias:TextView = findViewById(R.id.contaVitorias)
        var xcontaEmpates:TextView = findViewById(R.id.contaEmpates)
        var xcontaDerrotas:TextView = findViewById(R.id.contaDerrotas)
        contaVitorias2 = 0
        contaEmpate = 0
        contaDerrotas2 = 0
        xcontaVitorias.text = contaVitorias2.toString()
        xcontaEmpates.text = contaEmpate.toString()
        xcontaDerrotas.text = contaDerrotas2.toString()
    }
    fun veredito(){

        var txtJogoResultado:TextView = findViewById(R.id.textView2)
        var xcontaVitorias:TextView = findViewById(R.id.contaVitorias)
        var xcontaEmpates:TextView = findViewById(R.id.contaEmpates)
        var xcontaDerrotas:TextView = findViewById(R.id.contaDerrotas)
        val imgRodaJogo = findViewById<ImageView>(R.id.imgRodaJogo)
        val frameAnime = imgRodaJogo.background as AnimationDrawable

        if(passaValorSalvoParaTextBox==true){
            contaVitorias2 = xcontaEmpates.text.toString().toInt()
            contaEmpate = xcontaEmpates.text.toString().toInt()
            contaDerrotas2 = xcontaDerrotas.text.toString().toInt()
            passaValorSalvoParaTextBox=false
        }

        var ram:Int = (1..3).random()

        // ESCOLHIDO PEDRA = 1
        if(auxBtnVeredito == 1){
            if(ram == 1){
                txtJogoResultado.text = "EMPATE"
                contaEmpate ++
                imgRodaJogo.setBackgroundResource(R.drawable.rock)
            }
            if(ram == 2){
                txtJogoResultado.text = "VITÓRIAS"
                contaVitorias2 ++
                imgRodaJogo.setBackgroundResource(R.drawable.scisor)
            }
            if(ram == 3){
                txtJogoResultado.text = "DERROTA"
                contaDerrotas2 ++
                imgRodaJogo.setBackgroundResource(R.drawable.sheet)
            }
            auxBtnVeredito = 0
        }
        // ESCOLHIDO PAPEL = 2
        if(auxBtnVeredito == 2){
            if(ram == 1){
                txtJogoResultado.text = "VITÓRIAS"
                contaVitorias2 ++
                imgRodaJogo.setBackgroundResource(R.drawable.rock)
            }
            if(ram == 2){
                txtJogoResultado.text = "DERROTAS"
                contaDerrotas2 ++
                imgRodaJogo.setBackgroundResource(R.drawable.scisor)
            }
            if(ram == 3){
                txtJogoResultado.text = "EMPATE"
                contaEmpate ++
                imgRodaJogo.setBackgroundResource(R.drawable.sheet)
            }
            auxBtnVeredito = 0
        }
        // ESCOLHIDO TESOURA = 3
        if(auxBtnVeredito == 3){
            if(ram == 1){
                txtJogoResultado.text = "DERROTA"
                contaDerrotas2 ++
                imgRodaJogo.setBackgroundResource(R.drawable.rock)
            }
            if(ram == 2){
                txtJogoResultado.text = "EMPATE"
                contaEmpate ++
                imgRodaJogo.setBackgroundResource(R.drawable.scisor)
            }
            if(ram == 3){
                txtJogoResultado.text = "VITORIAS"
                contaVitorias2 ++
                imgRodaJogo.setBackgroundResource(R.drawable.sheet)
            }
            auxBtnVeredito = 0
        }

        xcontaVitorias.text = contaVitorias2.toString()
        xcontaEmpates.text = contaEmpate.toString()
        xcontaDerrotas.text = contaDerrotas2.toString()

    }
    fun paraTudo(){
        findViewById<RadioButton>(R.id.radioUm).isChecked = false
        findViewById<RadioButton>(R.id.radioUm).isEnabled = false
        findViewById<RadioButton>(R.id.radioDois).isChecked = false
        findViewById<RadioButton>(R.id.radioDois).isEnabled = false
        findViewById<RadioButton>(R.id.radioTres).isChecked = false
        findViewById<RadioButton>(R.id.radioTres).isEnabled = false
    }
}

