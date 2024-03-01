package com.example.formulariospantallas.modelo

class Diario {
    var id: Int
    var texto: String
    var fecha: String

    constructor(id: Int, texto: String, fecha: String) {
        this.id = id
        this.texto = texto
        this.fecha = fecha
    }
}