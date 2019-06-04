package lu.sfeir.technicalevent.gson

import com.google.gson.GsonBuilder
import com.fatboyindustrial.gsonjavatime.Converters


object GsonConverter {
    val gson = Converters.registerInstant(GsonBuilder()).create()

    fun toJson(data: Any): String = gson.toJson(data)
}