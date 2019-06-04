package lu.sfeir.technicalevent.gcloud

import com.google.gson.Gson
import lu.sfeir.technicalevent.gson.GsonConverter
import org.springframework.stereotype.Component

@Component
class GCloudPubSubService(private val pubsubOutboundGateway: PubSubApplication.PubsubOutboundGateway) {

    fun sendMessage(message: Any) {
        val jsonString = GsonConverter.toJson(message)
        pubsubOutboundGateway.sendToPubsub(jsonString)
    }
}