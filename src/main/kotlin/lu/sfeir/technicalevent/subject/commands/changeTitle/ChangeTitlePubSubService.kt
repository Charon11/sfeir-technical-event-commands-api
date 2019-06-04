package lu.sfeir.technicalevent.subject.commands.changeTitle

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
class TitleChangedPubSubService {
    @Bean
    @ServiceActivator(inputChannel = "titleChangedPubsubOutputChannel")
    fun titleChangedMessageSender(pubsubTemplate: PubSubTemplate): MessageHandler {
        return PubSubMessageHandler(pubsubTemplate, "title-changed-subject-events")
    }

    @Service
    @MessagingGateway(defaultRequestChannel = "titleChangedPubsubOutputChannel")
    interface DescriptionChangedPubsubOutboundGateway {
        fun sendToPubsub(text: String)
    }
}

@Component
class GCloudTitleChangedPubSubService(private val titleChangedPubsubOutboundGateway: TitleChangedPubSubService.DescriptionChangedPubsubOutboundGateway){
    fun sendMessage(message: Any) {
        val jsonString = GsonConverter.toJson(message)
        titleChangedPubsubOutboundGateway.sendToPubsub(jsonString)
    }
}