package fundatec.com.br.pagoualugoutcc

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        botaoEntrar.setOnClickListener{view ->
            entrarPerfil()
        }

        botaoFazerCadastro.setOnClickListener{view ->
            entrarCadastro()
        }
    }

    fun entrarPerfil(){
        val intent = Intent(baseContext, PerfilActivity::class.java)
        startActivity(intent)
    }

    fun entrarCadastro(){
        val intent = Intent(baseContext, CadastroActivity :: class.java)
            startActivity(intent)
    }
}
