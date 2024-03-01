package com.example.formulariospantallas.modelo

class Usuario {
    var nombreCompleto: String
    var nombreUser: String
    var telefono: String
    var correo: String
    var contrasena: String
    var diario: MutableList<Diario>

    constructor(
        nombreCompleto: String,
        nombreUser: String,
        telefono: String,
        correo: String,
        contrasena: String,
        diario: MutableList<Diario>,
    ) {
        this.nombreCompleto = nombreCompleto
        this.nombreUser = nombreUser
        this.telefono = telefono
        this.correo = correo
        this.contrasena = contrasena
        this.diario = diario
    }
}