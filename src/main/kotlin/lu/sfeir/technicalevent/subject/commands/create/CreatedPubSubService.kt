package lu.sfeir.technicalevent.subject.commands.create

import com.google.gson.Gson
import lu.sfeir.technicalevent.gson.GsonConverter
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate
import org.springframework.cloud.gcp.pubsub.integration.outbound.PubSubMessageHandler
import org.springframework.context.annotation.Bean
import org.springframework.integration.annotation.MessagingGateway
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.messaging.MessageHandler
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

@Component
class CreatedPubSubService {
    @Bean
    @ServiceActivator(inputChannel = "createdPubsubOutputChannel")
    fun createdMessageSender(pubsubTemplate: PubSubTemplate): MessageHandler {
        return PubSubMessageHandler(pubsubTemplate, "created-subject-events")
    }

    @Service
    @MessagingGateway(defaultRequestChannel = "createdPubsubOutputChannel")
    interface CreatedPubsubOutboundGateway {
        fun sendToPubsub(text: String)
    }
}

@Component
class GCloudCreatedPubSubService(private val createdPubsubOutboundGateway: CreatedPubSubService.CreatedPubsubOutboundGateway){
    fun sendMessage(message: Any) {
        val jsonString = GsonConverter.toJson(message)
        createdPubsubOutboundGateway.sendToPubsub(jsonString)
    }
}