package fundatec.com.br.pagoualugoutcc

class Usuario ( var nomeUsuario : String, var email : String, var senha : String) {
    constructor() : this("","",""){
        this.nomeUsuario = nomeUsuario
        this.email = email
        this.senha = senha
    }

    override fun toString() : String {
        return "{nomeUsuario: $nomeUsuario, email: $email, senha: $senha}"
    }
}