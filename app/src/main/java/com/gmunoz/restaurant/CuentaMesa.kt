package com.gmunoz.restaurant

class CuentaMesa {
    private var _items: MutableList<ItemMesa> = mutableListOf()
    private var aceptaPropina: Boolean = true
    private var numeroMesa: Int

    constructor(mesa: Int) {
        this.numeroMesa = mesa
    }

    public fun agregarItem(itemMesa: ItemMesa) {
        this._items.add(itemMesa)
    }

    public fun calcularTotalSinPropina(): Int {
        var totalCuenta = 0
        for (itemMesa in this._items) {
            totalCuenta += itemMesa.calcularSubTotal()
        }

        return totalCuenta
    }

    public fun calcularPropina(): Int {
        val procentajePropina: Double = 0.1

        return (this.calcularTotalSinPropina() * procentajePropina).toInt()
    }

    public fun calcularTotalConPropina(): Int {
        return (this.calcularTotalSinPropina() * 1.1).toInt()
    }

    public fun calcularTotal(): Int {
        if (this.aceptaPropina) {
            return this.calcularTotalConPropina()
        }

        return this.calcularTotalSinPropina()
    }
    public fun setAceptaPropina (aceptaPropina: Boolean)
    {
        this.aceptaPropina = aceptaPropina
    }
}