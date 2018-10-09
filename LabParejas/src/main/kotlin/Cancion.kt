
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class Cancion(val year: Int, val country: String, val region: Int, val artistName: String, val song: String , val isFavorite: Boolean) {
    class CancionArrayDeserializer: ResponseDeserializable<Array<Cancion>> {
        override fun deserialize(content: String): Array<Cancion>? {
            return Gson().fromJson(content, Array<Cancion>::class.java)
        }
    }
}