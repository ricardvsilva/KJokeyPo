package com.example.kjokeypo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var tela: Intent? = null
    var entra:Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // IR PARA TELA INICIAL
        if(entra) {
            tela = Intent(applicationContext, Inicial::class.java)
            startActivity(tela)
            entra = false
        }

        btnJogar.setOnClickListener({
            tela = Intent(applicationContext, Jogo::class.java)
            startActivity(tela)
        })

        btnConfig.setOnClickListener({
            tela = Intent(applicationContext, Config::class.java)
            tela!!.putExtra("nome", "Main")
            startActivity(tela)
        })

        btnTelaInicio.setOnClickListener({
            tela = Intent(applicationContext, Inicial::class.java)
            startActivity(tela)
        })
    }
}