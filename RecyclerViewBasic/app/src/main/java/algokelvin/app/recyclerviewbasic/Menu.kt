package algokelvin.app.recyclerviewbasic

data class Menus(
    val code: String,
    val type: String,
    val name: String,
    val description: String,
    val price: Int
)

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