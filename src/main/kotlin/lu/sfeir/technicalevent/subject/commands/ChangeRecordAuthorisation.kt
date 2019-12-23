package lu.sfeir.technicalevent.subject.commands

import com.google.common.collect.ImmutableMap
import com.google.firebase.auth.FirebaseToken
import lu.sfeir.technicalevent.gcloud.GCloudPubSubService
import lu.sfeir.technicalevent.subject.EventKind
import lu.sfeir.technicalevent.subject.Events
import org.springframework.stereotype.Service

data class RecordAuthorisationChanged(val record: Boolean,
                                      override val entityId: String,
                                      override val userId: String,
                                      override val _kind: String = EventKind.RECORD_AUTHORISATION_CHANGED) : Events()

data class ChangeRecordAuthorisationCommand(val record: Boolean)

@Service
class ChangeRecordAuthorisation(private val gCloudPubSubService: GCloudPubSubService) {
    fun changeRecordAuthorisation(entityId: String, changeRecordAuthorisationCommand: ChangeRecordAuthorisationCommand, token: FirebaseToken): RecordAuthorisationChanged {
        val recordAuthorisationChanged = RecordAuthorisationChanged(
                record = changeRecordAuthorisationCommand.record,
                entityId = entityId,
                userId = token.uid)
        gCloudPubSubService.sendMessage(recordAuthorisationChanged, ImmutableMap.of("_kind", recordAuthorisationChanged._kind))
        return recordAuthorisationChanged
    }
}