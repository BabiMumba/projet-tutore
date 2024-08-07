package cd.projetthealthcare.com.View

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import cd.projetthealthcare.com.Model.MedecinMdl
import cd.projetthealthcare.com.Model.MedicalPrescription
import cd.projetthealthcare.com.Model.Medicament
import cd.projetthealthcare.com.Model.PatientMdl
import cd.projetthealthcare.com.Model.userdata
import cd.projetthealthcare.com.R
import cd.projetthealthcare.com.Utils.PATIANT
import cd.projetthealthcare.com.Utils.PRESCRIPTION
import cd.projetthealthcare.com.Utils.Utils
import cd.projetthealthcare.com.databinding.ActivityAddPrescripBinding
import com.bumptech.glide.util.Util
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AddPrescripActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddPrescripBinding
    private val db = FirebaseFirestore.getInstance()
    var IdPatient= ""
    //liste des patients
    val liste_patients = ArrayList<PatientMdl>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPrescripBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.toolbarTitle.text = "Ajouter une prescription"
        binding.toolbar.backBtn.setOnClickListener {
            onBackPressed()
        }
        binding.etPatientId.visibility = View.GONE
        GetPatient()
        Handler().postDelayed({
            binding.etPatientId.visibility = View.VISIBLE
            setupPatientSpinner()
        }, 1000)

     /*   binding.etPatientId.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val patientId = liste_patients[position]
               // Utils.showsnakbar(binding.root, "Patient ID: $patientId")
                IdPatient = patientId.id

            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Ne rien faire
            }
        }*/

        // Récupérer l'ID du médecin actuellement connecté
        val mail = FirebaseAuth.getInstance().currentUser?.email
        val currentDoctorId = Utils.getUID(mail!!)

        // Bouton pour enregistrer la prescription
        binding.btnSavePrescription.setOnClickListener {
            savePrescription(currentDoctorId)
        }
    }

    fun setupPatientSpinner() {
        // Création d'une liste de noms à partir de la liste des patients
        val patientNames = liste_patients.map { patient -> "${patient.poste_nom} ${patient.nom}" }

        // Création d'un ArrayAdapter avec la liste des noms
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, patientNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.etPatientId.adapter = adapter

        // Définir un écouteur pour récupérer l'ID du patient sélectionné
        binding.etPatientId.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedPatient = liste_patients[position]
                IdPatient = selectedPatient.id // Récupérer l'ID du patient sélectionné
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Ne rien faire
            }
        }
    }
    private fun savePrescription(doctorId: String) {
        binding.progressBar.visibility = View.VISIBLE
        binding.btnSavePrescription.isEnabled = false
        // Récupérer les valeurs saisies dans les champs du formulaire
        val patientId = IdPatient
        val prescriptionDate = ""
        val medicationName = binding.etMedicationName.text.toString()
        val medicationDosage = binding.etMedicationDosage.selectedItem.toString()
        val medicationFrequency = binding.etMedicationFrequency.selectedItem.toString()
        val medicationDuration = binding.etMedicationDuration.selectedItem.toString()
        val medicationUnity = binding.unite.selectedItem.toString()
        val medicationHeure = binding.heure.selectedItem.toString()

        // Créer l'objet de prescription
        val prescription = MedicalPrescription(
            patientId = patientId,
            doctorId = doctorId,
            prescriptionDate = prescriptionDate,
            medications =
                Medicament(
                    name = medicationName,
                    dosage = medicationDosage,
                    frequency = medicationFrequency,
                    duration = medicationDuration,
                    duree = medicationDuration,
                    unity = medicationUnity,
                    heure = medicationHeure

            ),
            userdata = userdata(
                patientName = patientId,
                doctorIdName = Utils.username(this)
            )
        )

        // Enregistrer la prescription dans Firestore
        db.collection(PRESCRIPTION)
            .add(prescription)
            .addOnSuccessListener {
                // Prescription enregistrée avec succès
                Utils.showsnakbar(binding.root, "Prescription enregistrée avec succès")
                binding.progressBar.visibility = View.GONE
                binding.btnSavePrescription.isEnabled = true
            }
            .addOnFailureListener { e ->
                // Erreur lors de l'enregistrement
                Utils.showsnakbar(binding.root, "Erreur lors de l'enregistrement de la prescription: $e")
            }
    }
    fun GetPatient(){
        // Récupérer la liste des patients
        val db = FirebaseFirestore.getInstance()
        db.collection(PATIANT)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val patientId = document.toObject(PatientMdl::class.java)
                    liste_patients.add(patientId)

                }
                Log.d("TAG", "Liste des patients: $liste_patients")
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Erreur lors de la récupération des patients: $exception", Toast.LENGTH_SHORT).show()
            }

    }
}
