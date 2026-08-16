package com.gmunoz.eva1

import android.graphics.Typeface
import android.os.Bundle
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doAfterTextChanged
import com.gmunoz.restaurant.CuentaMesa
import com.gmunoz.restaurant.ItemMenu
import com.gmunoz.restaurant.ItemMesa
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var cantidadPlatosPastelDeChocloInput: EditText
    private lateinit var cantidadPlatosCazuelaInput: EditText
    private lateinit var cantidadPlatosPicaronesInput: EditText
    private lateinit var precioTotalPastelDeChoclo: TextView
    private lateinit var precioTotalCazuela: TextView
    private lateinit var precioTotalPicarones: TextView
    private lateinit var aceptaPropina: Switch
    private lateinit var precioTotalComida: TextView
    private lateinit var valorPropina: TextView
    private lateinit var totalAPagar: TextView
    private val precioPastelDeChoclo: Int = 12_000
    private val precioCazuela: Int = 8_000
    private val precioPricarones: Int = 6_000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        inicializarVistas()
        configurarListeners()
        calcularTotales()
    }

    private fun inicializarVistas() {
        cantidadPlatosPastelDeChocloInput = findViewById(R.id.pastelChocloInput)
        cantidadPlatosCazuelaInput = findViewById(R.id.cazuelaInput)
        cantidadPlatosPicaronesInput = findViewById(R.id.picaronesInput)

        precioTotalPastelDeChoclo = findViewById(R.id.precioPastelChoclo)
        precioTotalCazuela = findViewById(R.id.precioCazuela)
        precioTotalPicarones = findViewById(R.id.precioPicarones)

        aceptaPropina = findViewById(R.id.aceptaPropina)

        precioTotalComida = findViewById(R.id.totalComida)
        valorPropina = findViewById(R.id.totalPropina)
        totalAPagar = findViewById(R.id.total)
    }

    private fun configurarListeners() {
        cantidadPlatosPastelDeChocloInput.doAfterTextChanged { calcularTotales() }
        cantidadPlatosCazuelaInput.doAfterTextChanged { calcularTotales() }
        cantidadPlatosPicaronesInput.doAfterTextChanged { calcularTotales() }

        aceptaPropina.setOnCheckedChangeListener { _, _ ->
            calcularTotales()
        }
    }

    private fun calcularTotales() {
        val cantidadPlatosDePastelDeChoclo = cantidadPlatosPastelDeChocloInput.text.toString().toIntOrNull() ?: 0
        val cantidadPlatosDeCazuela = cantidadPlatosCazuelaInput.text.toString().toIntOrNull() ?: 0
        val cantidadPlatosDePicarones = cantidadPlatosPicaronesInput.text.toString().toIntOrNull() ?: 0
        val incluyePropina: Boolean = aceptaPropina.isChecked

        val pastelChocloItem = ItemMesa(ItemMenu("Pastel de Choclo", precioPastelDeChoclo), cantidadPlatosDePastelDeChoclo)
        val cazuelaItem = ItemMesa(ItemMenu("Cazuela", precioCazuela), cantidadPlatosDeCazuela)
        val picaronesItem = ItemMesa(ItemMenu("Picarones", precioPricarones), cantidadPlatosDePicarones)

        val mesa = CuentaMesa(1)

        mesa.setAceptaPropina(incluyePropina)
        mesa.agregarItem(pastelChocloItem)
        mesa.agregarItem(cazuelaItem)
        mesa.agregarItem(picaronesItem)

        val formatoMoneda = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("es-CL"))

        actualizarPrecioPlato(precioTotalPastelDeChoclo, pastelChocloItem, formatoMoneda)
        actualizarPrecioPlato(precioTotalCazuela, cazuelaItem, formatoMoneda)
        actualizarPrecioPlato(precioTotalPicarones, picaronesItem, formatoMoneda)

        precioTotalComida.text = formatoMoneda.format(mesa.calcularTotalSinPropina())
        valorPropina.text = formatoMoneda.format(
            if (incluyePropina) mesa.calcularPropina() else 0
        )
        totalAPagar.text = formatoMoneda.format(mesa.calcularTotal())
    }

    private fun actualizarPrecioPlato(
        textView: TextView,
        itemMesa: ItemMesa,
        formatoMoneda: NumberFormat
    ) {
        if (itemMesa.getCantidad() > 0) {
            textView.text = formatoMoneda.format(itemMesa.calcularSubTotal())
            textView.alpha = 1.0f
            textView.setTypeface(null, Typeface.BOLD)
        } else {
            textView.text = formatoMoneda.format(itemMesa.getItemMenu().getPrecio())
            textView.alpha = 0.45f
            textView.setTypeface(null, Typeface.NORMAL)
        }
    }
}