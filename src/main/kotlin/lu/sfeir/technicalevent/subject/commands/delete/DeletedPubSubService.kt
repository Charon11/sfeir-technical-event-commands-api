package lu.sfeir.technicalevent.subject.commands.delete

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
class DeletedPubSubService {
    @Bean
    @ServiceActivator(inputChannel = "deletedPubsubOutputChannel")
    fun deletedMessageSender(pubsubTemplate: PubSubTemplate): MessageHandler {
        return PubSubMessageHandler(pubsubTemplate, "deleted-subject-events")
    }

    @Service
    @MessagingGateway(defaultRequestChannel = "deletedPubsubOutputChannel")
    interface CreatedPubsubOutboundGateway {
        fun sendToPubsub(text: String)
    }
}

@Component
class GCloudDeletedPubSubService(private val deletedPubsubOutboundGateway: DeletedPubSubService.CreatedPubsubOutboundGateway){
    fun sendMessage(message: Any) {
        val jsonString = GsonConverter.toJson(message)
        deletedPubsubOutboundGateway.sendToPubsub(jsonString)
    }
}