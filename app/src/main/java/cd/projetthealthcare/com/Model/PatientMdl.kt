package cd.projetthealthcare.com.Model

data class PatientMdl(
    val adresse: String,
    val age: Int,
    val email: String,
    val genre: String,
    var id: String,
    val image: String="",
    val nom: String,
    val password: String,
    val prenom: String,
    val poste_nom: String,
    val telephone: String
)
{
    constructor() : this("", 0, "", "", "", "", "", "","", "", "")
}
