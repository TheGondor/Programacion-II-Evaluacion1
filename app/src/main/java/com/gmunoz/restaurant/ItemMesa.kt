package com.gmunoz.restaurant

class ItemMesa {
    private var cantidad: Int
    private var itemMenu: ItemMenu

    constructor(itemMenu: ItemMenu, cantidad: Int) {
        this.cantidad = cantidad
        this.itemMenu = itemMenu
    }

    public fun calcularSubTotal(): Int {
        return this.cantidad * this.itemMenu.getPrecio()
    }

    public fun getItemMenu(): ItemMenu {
        return this.itemMenu
    }

    public fun getCantidad(): Int {
        return this.cantidad
    }
}