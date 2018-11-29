package com.jhonny.listas

import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.*
import java.util.*

/**
 * A placeholder fragment containing a simple view.
 */
class MainActivityFragment : Fragment(), SeekBar.OnSeekBarChangeListener, TextToSpeech.OnInitListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    var progreso: Int? = null                       //progreso del seekBar
    var adaptador: ArrayAdapter<String>? = null     //adaptador en Array
    var listView: ListView? = null                  //listview

    var elementos = arrayOfNulls<String>(11)    //elementos del listView el 11 es el numero de filas

    var tts : TextToSpeech? = null //variable global para textToSpeech

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val vistaRaiz = inflater.inflate(R.layout.fragment_main, container, false)

        val seek = vistaRaiz.findViewById<SeekBar>(R.id.seekBar) as SeekBar

        progreso = seek.progress //3

        seek.setOnSeekBarChangeListener(this)   //implementa la interface del oyente de seekBar

        listView = vistaRaiz.findViewById<ListView>(R.id.listView) as ListView

        //instanciamos
        tts = TextToSpeech(context,this)
        //Log.i("Languages",Locale.getAvailableLocales().toString()) //sirve para ver que lenguages tiene instalado nuestra aplicación

        listView!!.setOnItemClickListener(this)
        listView!!.setOnItemLongClickListener(this)

        calcularTablas(progreso!!)  //los signos de admiracion son para indicar que no es null

        return vistaRaiz
    }

    fun calcularTablas(progreso: Int){
        for (i in 0..10){
            val texto = "$progreso x $i = ${progreso*i}"    //se crea el contenido del indice (fila)
            elementos.set(i, texto)                         //se inserta el texto en el indice
        }
        //  guardamos el array de indices al adaptador
        adaptador = ArrayAdapter<String>(context!!,android.R.layout.simple_list_item_1,elementos)
        //  enviamos el adaptador al objeto que en este caso es el listview
        listView!!.adapter = adaptador
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        calcularTablas(progress)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
        //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        //To change body of created functions use File | Settings | File Templates.
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS){
            val result = tts!!.setLanguage(Locale("es_MX"))
            if(result != TextToSpeech.LANG_NOT_SUPPORTED || result != TextToSpeech.LANG_MISSING_DATA){

            }else{
                Toast.makeText(context,"no soportado",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        if (tts != null){
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        var texto = elementos.get(position)!!.replace("*"," por ")
        Toast.makeText(context,"presionado",Toast.LENGTH_SHORT).show()
        tts!!.speak(texto,TextToSpeech.QUEUE_FLUSH,null,null)
    }

    override fun onItemLongClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long): Boolean {
        var texto = "No mames que intentas hacer, deja de presionar la puta pantalla"
        Toast.makeText(context,"mantener",Toast.LENGTH_SHORT).show()
        tts!!.speak(texto,TextToSpeech.QUEUE_FLUSH,null,null)
        return true
    }

}
