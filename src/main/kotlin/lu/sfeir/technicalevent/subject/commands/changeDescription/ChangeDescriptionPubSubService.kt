package lu.sfeir.technicalevent.subject.commands.changeDescription

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
class DescriptionChangedPubSubService {
    @Bean
    @ServiceActivator(inputChannel = "descriptionChangedPubsubOutputChannel")
    fun descriptionChangedMessageSender(pubsubTemplate: PubSubTemplate): MessageHandler {
        return PubSubMessageHandler(pubsubTemplate, "description-changed-subject-events")
    }

    @Service
    @MessagingGateway(defaultRequestChannel = "descriptionChangedPubsubOutputChannel")
    interface DescriptionChangedPubsubOutboundGateway {
        fun sendToPubsub(text: String)
    }
}

@Component
class GCloudDescriptionChangedPubSubService(private val descriptionChangedPubsubOutboundGateway: DescriptionChangedPubSubService.DescriptionChangedPubsubOutboundGateway){
    fun sendMessage(message: Any) {
        val jsonString = GsonConverter.toJson(message)
        descriptionChangedPubsubOutboundGateway.sendToPubsub(jsonString)
    }
}