package fundatec.com.br.pagoualugoutcc

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detalhe_produto.*

class DetalheProdutoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhe_produto)

        botaoVoltarDetalheProduto.setOnClickListener{view ->
            voltarHome()
        }

        textNomeProduto.setText( intent.getStringExtra ("nomeProduto" ) )
        textDescricao.setText(intent.getStringExtra("descricao"))
        textPreco.setText(intent.getStringExtra("preco"))
        val url = intent.getStringExtra("url")
        Picasso.get().load(url).into(imageView)
        textEmail.setText(intent.getStringExtra("emailUsuario"))
    }

    fun voltarHome(){
        val intent = Intent(baseContext, MainActivity::class.java)
            startActivity(intent)
    }




}
