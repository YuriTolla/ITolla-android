package fundatec.com.br.pagoualugoutcc

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import java.util.ArrayList
import android.view.LayoutInflater
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_produto.view.*


class ProdutoAdapter<T>(var baseContext: Context?, var listaProdutos: ArrayList<Produto>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = LayoutInflater.from(baseContext)
        val row: View
        row = inflater.inflate(R.layout.item_produto, parent, false)
        Log.i("item $position", getItem(position).toString())
        Picasso.get().load(getItem(position).imagem).into(row.image_produto)
        row.titulo.setText(getItem(position).nomeProduto)
        row.preco.setText(getItem(position).preco)
        return row
    }

    override fun getItem(position: Int): Produto {
        return listaProdutos.get(position)
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return listaProdutos.count()
    }

}
