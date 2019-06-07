package lu.sfeir.technicalevent.gcloud

import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate
import org.springframework.cloud.gcp.pubsub.integration.outbound.PubSubMessageHandler
import org.springframework.context.annotation.Bean
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.messaging.MessageHandler
import org.springframework.integration.annotation.MessagingGateway
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service


@Component
class PubSubApplication {

    @Bean
    @ServiceActivator(inputChannel = "pubsubOutputChannel")
    fun messageSender(pubsubTemplate: PubSubTemplate): MessageHandler {
        return PubSubMessageHandler(pubsubTemplate, "subject-events")
    }

    @Component
    @MessagingGateway(defaultRequestChannel = "pubsubOutputChannel")
    interface PubsubOutboundGateway {
        fun sendToPubsub(text: String, keys: Map<String, String>)
    }

}