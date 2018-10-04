import org.jetbrains.exposed.sql.Table

object Canciones: Table() {
    val year =  Int
    val country = varchar("country", length = 50)
    val region = Int
    val artistName = varchar("artistName", length = 50)
    val song = varchar("song", length = 50 )
    val isFavorite : Boolean = false
    val id = integer("id").autoIncrement().primaryKey()
    val title = varchar("title", length = 50)
}