package fundatec.com.br.pagoualugoutcc

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import fundatec.com.br.pagoualugoutcc.R.id.botaoCadastrar
import kotlinx.android.synthetic.main.activity_cadastro.*

class CadastroActivity : AppCompatActivity() {

    val database = FirebaseDatabase.getInstance()
    var cadastroUsuarioReference = database.getReference("cadastroUsuario")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        botaoCadastrar.setOnClickListener{view ->
        }

        cadastroUsuarioReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val usuario = dataSnapshot.getValue(Usuario ::class.java)
                usuario?.let {
                    Log.e("nomeUsuario", it.nomeUsuario)
                    Log.e("email", it.email)
                    Log.e("senha", it.senha)
                }
            }

            override fun onCancelled(p0: DatabaseError) {
            }
        })

        botaoCadastrar.setOnClickListener{ view ->
            //Snackbar.make(view, "salvo", Snackbar.LENGTH_LONG).serAction("Sucess", null).show()
            val nomeUsuario = inputCadastroNomeUsuario.text.toString()
            val email = inputCadastroEmail.text.toString()
            val senha = inputCadastroSenha.text.toString()
            val UsuarioDB = Usuario(nomeUsuario, email, senha)
            val key = cadastroUsuarioReference.child("Usuario").push().key
            cadastroUsuarioReference.child(key!!).setValue(UsuarioDB)
            Toast.makeText(baseContext,"Usuário cadastrado com Sucesso!", Toast.LENGTH_LONG).show()
            entrarPerfilCadastrado()
        }
    }

    fun entrarPerfilCadastrado(){
        val intent = Intent(baseContext, PerfilActivity::class.java)
        startActivity(intent)
    }
}
