package fundatec.com.br.pagoualugoutcc

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import kotlinx.android.synthetic.main.activity_anuncia_produto.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.jetbrains.anko.doAsync
import com.google.firebase.storage.FirebaseStorage
import org.jetbrains.anko.email
import java.io.IOException

class AnunciaProdutoActivity : AppCompatActivity() {

    val database = FirebaseDatabase.getInstance()
    var cadastroProdutoReference = database.getReference("cadastroProduto")

    var uriProfileImage: Uri? = null
    var taskUri: Task<Uri>? = null
    var profileImageUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anuncia_produto)

        botaoAnunciarProduto.setOnClickListener { view ->
        }

        cadastroProdutoReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val produto = dataSnapshot.getValue(Produto::class.java)
                produto?.let {
                    Log.e("descricao", it.descricao)
                    Log.e("preco", it.preco)
                    Log.e("nomeProduto", it.nomeProduto)
                    Log.e("emailUsuario", it.emailUsuario)
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

        // Image
        imagemProduto.setOnClickListener { showImageChooser() }
        findViewById<View>(R.id.botaoAnunciarProduto).setOnClickListener { }

        //salvar
        botaoAnunciarProduto.setOnClickListener { view ->
            if (taskUri != null) {
                if (taskUri?.isComplete!!) {
                    val descricao = inputDescricao.text.toString()
                    val preco = inputPreco.text.toString()
                    val nomeProduto = inputNomeProduto.text.toString()
                    val emailUsuario = InputEmail.text.toString()
                    val ProdutoDB = Produto(descricao, preco, nomeProduto,  profileImageUrl!!, emailUsuario)
                    val key = cadastroProdutoReference.child("Produto").push().key
                    cadastroProdutoReference.child(key!!).setValue(ProdutoDB)
                    Toast.makeText(baseContext, "Produto cadastrado!", Toast.LENGTH_LONG).show()
                    voltarPerfil()
                } else {
                    Toast.makeText(baseContext, "Carregando foto, por favor, aguarde 30 segundos e anuncie de novo", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(baseContext, "imagem obrigatoria", Toast.LENGTH_LONG).show()
            }
        }

        imagemProduto.setOnClickListener { showImageChooser() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CHOOSE_IMAGE && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            uriProfileImage = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uriProfileImage)
                imagemProduto.setImageBitmap(bitmap)
                carregarFotoFirebaseStorage()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun showImageChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select profile Image"), CHOOSE_IMAGE)
    }

    companion object {
        internal val CHOOSE_IMAGE = 101
    }

    fun voltarPerfil() {
        val intent = Intent(baseContext, PerfilActivity::class.java)
        startActivity(intent)
    }

    private fun carregarFotoFirebaseStorage() {
        doAsync {
            val profileImageRef = FirebaseStorage.getInstance()
                    .getReference("Galeria/" + System.currentTimeMillis() + ".jpg")
            if (uriProfileImage != null) {
                try {
                    taskUri = profileImageRef.putFile(uriProfileImage!!).continueWithTask { uploadTask ->
                        if (!uploadTask.isSuccessful) {
                            throw uploadTask.exception!!
                        }
                        return@continueWithTask profileImageRef.downloadUrl
                    }
                    val url = Tasks.await(taskUri!!)
                    profileImageUrl = url.toString()
                    Log.i("URL IMAGE", profileImageUrl)
                } catch (e: Exception) {
                    Log.e("URL IMAGE ERRO", e.message)
                }
            }
        }
    }
}
