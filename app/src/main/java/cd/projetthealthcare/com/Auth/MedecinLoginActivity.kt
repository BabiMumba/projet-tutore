package cd.projetthealthcare.com.Auth

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import cd.projetthealthcare.com.MainActivity
import cd.projetthealthcare.com.Model.Medecin
import cd.projetthealthcare.com.R
import cd.projetthealthcare.com.Utils.MEDECIN
import cd.projetthealthcare.com.Utils.Utils
import cd.projetthealthcare.com.databinding.ActivityMedecinLoginBinding
import cd.projetthealthcare.com.databinding.ActivityPatientLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MedecinLoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityMedecinLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMedecinLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.loginBtn.setOnClickListener {
            LoginWithMail()
        }
        binding.createCount.setOnClickListener {
            startActivity(Intent(this, MedecinRegesterActivity::class.java))
        }
    }
   // @SuppressLint("SuspiciousIndentation")
    fun LoginWithMail(){
        //login with mail
        val email = binding.edtEmail.text.toString()
        val password = binding.password.text.toString()
       if (Utils.isValidEmail(email) && Utils.isValidPassword(password)) {
           Utils.isloading(binding.loginBtn,binding.loader,true)
           val uid = Utils.getUID(email)
           FirebaseFirestore.getInstance()
              .collection(MEDECIN).document(uid).get().addOnSuccessListener {user->
                  val medecin = user.toObject(Medecin::class.java)
                if (user.exists()) {
                     FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener {it->
                          if (it.isSuccessful) {
                              Utils.savenameDoctore(this, medecin!!)
                            Utils.newIntentFinish(this, MainActivity::class.java)
                            Utils.isloading(binding.loginBtn,binding.loader,false)
                          } else {
                            Utils.showToast(this, "Erreur de connexion")
                            Utils.isloading(binding.loginBtn,binding.loader,false)
                          }
                     }
                } else {
                     Utils.showToast(this, "Aucun compte medecin trouvé")
                        Utils.isloading(binding.loginBtn,binding.loader,false)
                }
              }
       }else{
              Utils.showToast(this, "Veuillez entrer un email et un mot de passe valides")
       }
    }
}