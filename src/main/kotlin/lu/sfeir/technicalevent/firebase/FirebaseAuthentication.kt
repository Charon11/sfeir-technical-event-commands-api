package lu.sfeir.technicalevent.firebase

import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseToken
import com.google.firebase.tasks.Task
import com.google.firebase.tasks.Tasks
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.io.ByteArrayInputStream
import java.util.*
import java.util.concurrent.ExecutionException


@Component
class FirebaseConfig {
    @Value("\${firebase.serviceaccount.encoded-key}")
    lateinit var serviceAccountEncodedKey: String
}

@Service
class FirebaseAuthentication(firebaseConfig: FirebaseConfig) {


    private final val serviceAccount = ByteArrayInputStream(Base64.getDecoder().decode(firebaseConfig.serviceAccountEncodedKey))
    private final val options = FirebaseOptions.Builder()
            .setServiceAccount(serviceAccount)
            .setDatabaseUrl("https://sfeirluxtechnicalevent.firebaseio.com")
            .build()

    private val firebaseApp = FirebaseApp.initializeApp(options)

    @Throws(InterruptedException::class, ExecutionException::class)
    fun verifyIdToken(idToken: String): FirebaseToken {
        return Tasks.await(FirebaseAuth.getInstance().verifyIdToken(idToken))
    }

    fun generateToken(uid: String): String {
        return Tasks.await(FirebaseAuth.getInstance().createCustomToken(uid))
    }
}