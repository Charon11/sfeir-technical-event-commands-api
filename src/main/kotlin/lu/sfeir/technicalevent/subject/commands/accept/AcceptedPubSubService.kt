package lu.sfeir.technicalevent.subject.commands.accept

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
class AcceptedPubSubService {
    @Bean
    @ServiceActivator(inputChannel = "acceptedPubsubOutputChannel")
    fun acceptedMessageSender(pubsubTemplate: PubSubTemplate): MessageHandler {
        return PubSubMessageHandler(pubsubTemplate, "accepted-subject-events")
    }

    @Service
    @MessagingGateway(defaultRequestChannel = "acceptedPubsubOutputChannel")
    interface CreatedPubsubOutboundGateway {
        fun sendToPubsub(text: String)
    }
}

@Component
class GCloudAcceptedPubSubService(private val acceptedPubsubOutboundGateway: AcceptedPubSubService.CreatedPubsubOutboundGateway){
    fun sendMessage(message: Any) {
        val jsonString = GsonConverter.toJson(message)
        acceptedPubsubOutboundGateway.sendToPubsub(jsonString)
    }
}