package lu.sfeir.technicalevent.subject

object SubjectStatus {
    const val NEW = "Nouveau"
    const val PENDING = "En cours"
    const val ACCEPTED = "Accepté"
    const val REFUSED = "Refusé"
    const val DELETED = "Supprimé"
}

enum class SubjectType(val type: String) {
    QUARTER_BACKS("Quarter Backs"),
    BOUFFE_FRONT("Bouffe Front"),
    BEYOND_KEYBOARDS("Beyond Keyboards"),
    SFEIR_HOBBIES("Sfeir Hobbies"),
    AFTER_SFEIR("After Sfeir")
}