package lu.sfeir.technicalevent.gcloud

import com.google.gson.Gson
import lu.sfeir.technicalevent.gson.GsonConverter
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

@Service
class GCloudPubSubService(private val pubsubOutboundGateway: PubSubApplication.PubsubOutboundGateway) {

    fun sendMessage(message: Any, keys: Map<String, String>) {
        val jsonString = GsonConverter.toJson(message)
        pubsubOutboundGateway.sendToPubsub(jsonString, keys)
    }
}