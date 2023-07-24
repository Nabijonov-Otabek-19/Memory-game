package uz.nabijonov.otabek.memorygameapp_bek

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import uz.nabijonov.otabek.memorygameapp_bek.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}