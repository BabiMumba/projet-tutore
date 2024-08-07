package cd.projetthealthcare.com.View

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import cd.projetthealthcare.com.Auth.HopitalActivity
import cd.projetthealthcare.com.Auth.MedecinLoginActivity
import cd.projetthealthcare.com.Auth.MedecinRegesterActivity
import cd.projetthealthcare.com.Auth.PatientLoginActivity
import cd.projetthealthcare.com.MainActivity
import cd.projetthealthcare.com.R
import cd.projetthealthcare.com.databinding.ActivityOnboardBinding

class OnboardActivity : AppCompatActivity() {
    lateinit var binding: ActivityOnboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOnboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        init_clic()



    }
    fun init_clic(){
        binding.btnPatient.setOnClickListener {
            startActivity(Intent(this, PatientLoginActivity::class.java))
        }
        binding.btnMedecin.setOnClickListener {
            startActivity(Intent(this, MedecinLoginActivity::class.java))
        }

    }
}