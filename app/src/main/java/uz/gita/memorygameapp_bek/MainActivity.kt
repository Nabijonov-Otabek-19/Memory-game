package uz.gita.memorygameapp_bek

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import uz.gita.memorygameapp_bek.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}