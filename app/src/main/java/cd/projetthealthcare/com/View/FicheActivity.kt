package cd.projetthealthcare.com.View

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import cd.projetthealthcare.com.R
import cd.projetthealthcare.com.Utils.Utils
import cd.projetthealthcare.com.databinding.ActivityFicheBinding

class FicheActivity : AppCompatActivity() {
    lateinit var binding: ActivityFicheBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFicheBinding.inflate(layoutInflater)
        setContentView(binding.root)

        visiblefab()
    }

    fun visiblefab(){
        if (!Utils.IsDoctor(this)){
            binding.fab.visibility = View.VISIBLE
        }else{
            binding.fab.visibility = View.GONE
        }
    }
}