package algokelvin.app.room.db

class SubscriberRepository(private val dao: SubscriberDao) {

    val subscribers = dao.getAllData()

    suspend fun insert(subscriber: Subscriber): Long = dao.insert(subscriber)

    suspend fun update(subscriber: Subscriber) = dao.update(subscriber)

    suspend fun delete(subscriber: Subscriber) = dao.delete(subscriber)

    suspend fun deleteAll() = dao.deleteAll()

}