package fundatec.com.br.pagoualugoutcc

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_lista_produtos.*

class ListaProdutosActivity : AppCompatActivity() {

    var listaProdutos = ArrayList<Produto>()
    val database = FirebaseDatabase.getInstance()
    var listaProdutoReference = database.getReference("cadastroProduto")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_produtos)

        listaProdutoReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot){
                listaProdutos.clear()
                dataSnapshot.children.mapNotNullTo(listaProdutos){
                    it.getValue<Produto>(Produto::class.java)
                }
                var adapter = ArrayAdapter<Produto>(baseContext, android.R.layout.simple_list_item_1, listaProdutos)
                listaProdutosUsuario.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}
