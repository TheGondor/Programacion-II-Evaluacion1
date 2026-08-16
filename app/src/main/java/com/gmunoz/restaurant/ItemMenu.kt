package com.gmunoz.restaurant

class ItemMenu {
    private var nombre: String
    private var precio: Int

    constructor(nombre: String, precio: Int)
    {
        this.nombre = nombre
        this.precio = precio
    }

    public fun getPrecio(): Int
    {
        return this.precio
    }
}