package fundatec.com.br.pagoualugoutcc

import android.text.Editable

class Produto() {
    var descricao : String = ""
    var preco : String = ""
    var nomeProduto : String = ""
    var imagem : String = ""
    var emailUsuario : String = " "

    constructor(descricao: String, preco: String, nomeProduto: String, imagem : String, emailUsuario : String) : this (){
        this.descricao = descricao
        this.preco = preco
        this.nomeProduto = nomeProduto
        this.imagem = imagem
        this.emailUsuario = emailUsuario

    }

    override fun toString() : String {
        return "descricao: $descricao, preco: $preco, nomeProduto: $nomeProduto, imagem: $imagem, emailUsuario: $emailUsuario"
    }
}