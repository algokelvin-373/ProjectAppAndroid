package kelvinht.app.cafeapps.utils

import android.os.Build
import androidx.annotation.RequiresApi
import kelvinht.app.cafeapps.model.data.CodeMenu
import kelvinht.app.cafeapps.model.data.Menus
import java.text.NumberFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun nowDateTime(pattern: String): String {
    val dtf = DateTimeFormatter.ofPattern(pattern)
    val dateNow = LocalDateTime.now()
    return dtf.format(dateNow)
}

fun formatRupiah(number: Double): String {
    val locale = Locale("in", "ID")
    val formatRupiah = NumberFormat.getCurrencyInstance(locale)
    return formatRupiah.format(number)
}

fun listNoTable(): ArrayList<String> {
    val data = ArrayList<String>()
    for (x in 1 until 11) {
        data.add("M0$x")
    }
    return data
}

fun listCode(): ArrayList<String> {
    val menu = menu()
    val data = ArrayList<String>()
    for (dt in menu) {
        data.add("${dt.code} - ${dt.name}")
    }
    return data
}

fun listCodeMenu(): ArrayList<CodeMenu> {
    val menu = menu()
    val data = ArrayList<CodeMenu>()
    for (dt in menu) {
        data.add(CodeMenu("${dt.code} - ${dt.name}", dt.price))
    }
    return data
}

fun menu(): ArrayList<Menus> {
    val data = ArrayList<Menus>()
    data.add(Menus(
        "B01", "Minuman", "Kopi Hitam",
        "Kopi Hitam dengan dibuat dengan teknik espresso, dimana biji kopi yang digunakan " +
                "yaitu berasal dari Aceh Gayo jenis Arabika, disajikan dengan gula terpisah.",
        10000))
    data.add(Menus(
        "B02", "Minuman", "Cappucino",
        "Paduan kopi dengan buih susu kental serta menggunakan sirup karamel, dimana biji " +
                "kopi yang digunakan berasal dari Gunung Puntang Jawa Barat jenis Robusta.", 20000))
    data.add(Menus(
        "M03", "Minuman", "Sparkling Tea",
        "Minuman Teh yang menggunakan daun teh dari pegununngan daerah Garut dengan tambahan " +
                "sari melati asli dan gula merah alami.", 15000))
    data.add(Menus(
        "F01", "Makanan", "Batagor",
        "Baso dan Tahu Goreng dengan sajian bumbu kacang dan kecap khas Bandung.", 25000))
    data.add(Menus(
        "F02", "Makanan", "Cireng",
        "Makanan ringan berupa tepung kanji goreng isi bahan dasar tempe fermentasi yang " +
                "disebut oncom, disajikan dengan bumbu kacang pedas.", 10000))
    data.add(Menus(
        "F03", "Makanan", "Nasi Goreng",
        "Nasi goreng ayam yang disajikan dengan telur mata sapi disertai satai ayam.", 50000))
    data.add(Menus(
        "D01", "Desert", "Cheese Cake",
        "Kue Tart 1 slice dengan bahan utama cream cheese dengan topping buah - buahan " +
                "asli seperti anggur, jeruk, kiwi", 40000))
    data.add(Menus(
        "D02", "Desert", "Black Salad",
        "Potongan buah-buah segar yang terdiri dari Pepaya, Jambu, Melon, dan Mangga " +
                "disajikan dengan bumbu rujak kacang pedas dan gula merah", 30000))
    return data
}