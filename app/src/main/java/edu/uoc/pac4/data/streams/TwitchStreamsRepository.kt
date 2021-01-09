package edu.uoc.pac4.data.streams

/**
 * Created by alex on 11/21/20.
 */

class TwitchStreamsRepository(
    private val dataSource: StreamsDataSource
) : StreamsRepository {

    override suspend fun getStreams(cursor: String?): Pair<String?, List<Stream>> {
        var streamResponse = dataSource.getStreams(cursor)
        var resultado = Pair<String?, List<Stream>>(null, emptyList())

        streamResponse?.data?.let {
            resultado =   Pair<String?, List<Stream>>(streamResponse.pagination?.cursor, it)
        }

        return resultado
    }

}