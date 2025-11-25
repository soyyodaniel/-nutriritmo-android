package com.example.nutriritmo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nutriritmo.data.repository.AyunoRepository
import com.example.nutriritmo.model.Ayuno
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AyunoViewModel(private val repository: AyunoRepository) : ViewModel() {

    private val _ayunos = MutableLiveData<List<Ayuno>>()
    val ayunos: LiveData<List<Ayuno>> = _ayunos

    // Función para obtener los ayunos de un usuario
    fun obtenerAyunos(idUsuario: Int) {
        repository.obtenerAyunos(idUsuario).enqueue(object : Callback<List<Ayuno>> {
            override fun onResponse(call: Call<List<Ayuno>>, response: Response<List<Ayuno>>) {
                if (response.isSuccessful) {
                    _ayunos.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<Ayuno>>, t: Throwable) {
                // Manejar error
            }
        })
    }

    // Función para iniciar un nuevo ayuno
    fun iniciarAyuno(ayuno: Ayuno) {
        repository.iniciarAyuno(ayuno).enqueue(object : Callback<Ayuno> {
            override fun onResponse(call: Call<Ayuno>, response: Response<Ayuno>) {
                if (response.isSuccessful) {
                    // Actualizar estado si es necesario
                }
            }

            override fun onFailure(call: Call<Ayuno>, t: Throwable) {
                // Manejar error
            }
        })
    }

    // Función para detener un ayuno
    fun detenerAyuno(ayuno: Ayuno) {
        repository.detenerAyuno(ayuno).enqueue(object : Callback<Ayuno> {
            override fun onResponse(call: Call<Ayuno>, response: Response<Ayuno>) {
                if (response.isSuccessful) {
                    // Actualizar estado si es necesario
                }
            }

            override fun onFailure(call: Call<Ayuno>, t: Throwable) {
                // Manejar error
            }
        })
    }
}
