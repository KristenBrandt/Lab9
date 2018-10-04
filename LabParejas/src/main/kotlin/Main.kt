import com.github.kittinunf.fuel.Fuel
import dbmodels.Books
import models.Cancion
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction


fun menupricipal():  String {
    return """
        Menu Principal
        1. Buscar canciones por nombre
        2. Buscar canciones por artista
        3. Mostrar todas mis canciones favoritas
        4. Salir
        """.trimIndent()
}
fun menusecundario(): String {
    return """
        Menu
        Ingrese parte del nombre de la cancion de desea buscar:
        """.trimIndent()
}
fun menuterciario(): String{
    return """
        Menu
        Ingrese el artista deseado
        """.trimIndent()
}
fun menufav (): String{
    return """
        Sus canciones favoritas son:
        """".trimIndent()
}

fun cancionesPorNombre(val nombre:String){
    var lista: String
    var selecion : Boolean = true
    var respuesta: String
    var respuesta2: Int
    var num: Int = 0
    for (cancion in Canciones.selectAll()) {

        if(cancion.name == nombre){
            num++
            println("$num. ${cancion.name}")
        }
    }
    println("Desea guardar alguna canción como favorita? (Si/No)")

    while (selecion){
        respuesta = readLine()!!
        when (respuesta){
            "Si" ->{println("Elija cual (ingrese el numero)")
                respuesta2 = readLine()!!.toInt()
                var num2: Int = 0
                for (cancion in Canciones.selectAll()) {
                    num2++
                    if(cancion.name == nombre){
                        num2++
                        println("$num. ${cancion.name}")
                    }
                    if(num2 == respuesta2){
                        cancion.isFavorite = true
                    }
                }
                selecion = false
            }
            "No" ->{selecion = false}
            else -> println("Lo que ingreso no es valido, intente de nuevo.")
        }
    }
}
fun cancionesPorArtista(val nombre:String){
    var lista: String
    var selecion : Boolean = true
    var respuesta: String
    var respuesta2: Int
    var num: Int = 0
    for (cancion in Canciones.selectAll()) {

        if(cancion.artistName == nombre){
            num++
            println("$num. ${cancion.name} - ${cancion.artistName}")
        }
    }
    println("Desea guardar alguna canción como favorita? (Si/No)")

    while (selecion){
        respuesta = readLine()!!
        when (respuesta){
            "Si" ->{println("Elija cual (ingrese el numero)")
                respuesta2 = readLine()!!.toInt()
                var num2: Int = 0
                for (cancion in Canciones.selectAll()) {
                    if(cancion.artistName == nombre){
                        num++
                        println("$num. ${cancion.name} - ${cancion.artistName}")
                    }
                    if(num2 == respuesta2){

                        cancion.isFavorite = true
                    }
                }
                selecion = false
            }
            "No" ->{selecion = false}
            else -> println("Lo que ingreso no es valido, intente de nuevo.")
        }
    }
}
fun favSongs(){
    var lista : String
    var num: Int = 0
    for (cancion in Canciones.selectAll()){
        num++
        if (cancion.isFavorite == true)
            println("$num. ${cancion.name}")
    }
}

fun main(args: Array<String>) {
    // Conexiones//
    val url = "https://next.json-generator.com/api/json/get/EkeSgmXycS"

    val (request, response, result ) = Fuel.get(url).responseObject(Cancion.CancionArrayDeserializer())
    val (canciones, err) = result

    Database.connect(
            "jdbc:postgresql:misctests",
            "org.postgresql.Driver",
            "postgres",
            "postgres"
    )

    transaction {
        SchemaUtils.create(Canciones)

        canciones?.forEach{
            Canciones.insert{
                it
            }
        }
    }
    Thread.sleep(5000)

    // Programa Normal//
    var on : Boolean = true
    var men: Int
    var nombre: String
    println(menupricipal())
    men = readLine()!!.toInt()
    while(on) {
        when (men) {
            1 -> {
                println(menusecundario())
                nombre = readLine()!!
                cancionesPorNombre(nombre)
            }
            2 -> {
                println(menuterciario())
                nombre = readLine()!!
                cancionesPorArtista(nombre)
            }
            3 -> {
                println(menufav())
                favSongs()
            }
            4 -> {
                println("Saliendo del programa")
                on = false
            }
            else -> println("Opcion no valida, intente de nuevo.")
        }
    }

}