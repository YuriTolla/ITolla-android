package fundatec.com.br.pagoualugoutcc

class InformacoesContato() {
    var informacaoEmail : String = " "

    constructor(informacaoEmail : String) : this (){
        this.informacaoEmail = informacaoEmail
    }

    override fun toString(): String {
        return "informacaoEmail: $informacaoEmail"
    }
}