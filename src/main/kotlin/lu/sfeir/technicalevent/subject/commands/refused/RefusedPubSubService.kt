package lu.sfeir.technicalevent.subject.commands.refused

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
class RefusedPubSubService {
    @Bean
    @ServiceActivator(inputChannel = "refusedPubsubOutputChannel")
    fun refusedMessageSender(pubsubTemplate: PubSubTemplate): MessageHandler {
        return PubSubMessageHandler(pubsubTemplate, "refused-subject-events")
    }

    @Service
    @MessagingGateway(defaultRequestChannel = "refusedPubsubOutputChannel")
    interface RefusedPubsubOutboundGateway {
        fun sendToPubsub(text: String)
    }
}

@Component
class GCloudRefusedPubSubService(private val refusedPubsubOutboundGateway: RefusedPubSubService.RefusedPubsubOutboundGateway){
    fun sendMessage(message: Any) {
        val jsonString = GsonConverter.toJson(message)
        refusedPubsubOutboundGateway.sendToPubsub(jsonString)
    }
}