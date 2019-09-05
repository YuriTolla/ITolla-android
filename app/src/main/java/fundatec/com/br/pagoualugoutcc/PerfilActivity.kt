package fundatec.com.br.pagoualugoutcc

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.Sampler
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_perfil.*

class PerfilActivity : AppCompatActivity() {

    val database = FirebaseDatabase.getInstance()
    var informacoesContatoReference = database.getReference("informacaoDeContato")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        botaoPerfilAnunciarProduto.setOnClickListener{view ->
            anunciarProduto()
        }

        botaoPerfilLista.setOnClickListener { View ->
            listarProdutos()
        }

        botaoPerfilVoltarHome.setOnClickListener { view ->
            voltarHome()
        }

        informacoesContatoReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val informacaoContato = dataSnapshot.getValue(InformacoesContato :: class.java)
                informacaoContato?.let {
                    Log.e("informacaoEmail", it.informacaoEmail)
                }
            }
            override fun onCancelled(p0: DatabaseError) {
            }
        })

        //salvar
       botaoPerfilSalvar.setOnClickListener { view ->
            //Snackbar.make(view, "Salvo", Snackbar.LENGTH_LONG).setAction("Sucess", null).show()
                val informacaoEmail = inputPerfilEmail.text.toString()

                val informacaoContatoDB = InformacoesContato(informacaoEmail)
                val InformacaoContatoDB = InformacoesContato(inputPerfilEmail.text.toString())
                val key = informacoesContatoReference.child("InformacaoContato").push().key
                if (key != null) {
                    informacoesContatoReference.child(key).setValue(InformacaoContatoDB)
            }
            Toast.makeText(baseContext,"Dados Salvos!", Toast.LENGTH_LONG).show()

        }
    }
    var informacaoContatoDB = InformacoesContato()

    fun anunciarProduto(){
        val intent = Intent(baseContext, AnunciaProdutoActivity :: class.java)
            startActivity(intent)
    }

    fun listarProdutos(){
        val intent = Intent(baseContext, ListaProdutosActivity :: class.java)
            startActivity(intent)
    }

    fun voltarHome(){
        val intent = Intent(baseContext, MainActivity :: class.java)
            startActivity(intent)
    }
}
