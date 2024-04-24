Vue.createApp({
    data() {
        return {
            nombreUsuario: "",
            email: "",
            password: "",
            errorMsg: "",
        };
    },
    methods: {
        capitalizarPalabras(frase) {            
            const palabras = frase.split(' ');
            const palabrasCapitalizadas = palabras.map(palabra => {
                return palabra.charAt(0).toUpperCase() + palabra.slice(1).toLowerCase();
            });
            return palabrasCapitalizadas.join(' ');
        },
        signUp: function (event) {
            event.preventDefault();
            let config = {
                headers: {
                    'content-type': 'application/x-www-form-urlencoded'
                }
            };
            this.nombreUsuario= this.capitalizarPalabras(this.nombreUsuario)
            let regexEmail=/^(([^<>()\[\]\.,;:\s@\”]+(\.[^<>()\[\]\.,;:\s@\”]+)*)|(\”.+\”))@(([^<>()[\]\.,;:\s@\”]+\.)+[^<>()[\]\.,;:\s@\”]{2,})$/
            if(!regexEmail.test(this.email)){
                alert("El email no es valido")
                return
            };
            let regexPassword = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[$@$!%*?&])[A-Za-z\d$@$!%*?&]{8,15}$/;

if (!regexPassword.test(this.password)) {
    let mensaje = "El password es inválido. Asegúrese de cumplir con los siguientes requisitos:\n";    
    if (!/(?=.*[a-z])/.test(this.password)) {
        mensaje += "- Al menos una letra minúscula.\n";
        }    
    if (!/(?=.*[A-Z])/.test(this.password)) {
        mensaje += "- Al menos una letra mayúscula.\n";
    }    
    if (!/(?=.*\d)/.test(this.password)) {
        mensaje += "- Al menos un dígito.\n";
    }      
    mensaje += "- Longitud entre 8 y 15 caracteres.";    
    alert(mensaje);
    return;
}

            axios.post('/auth/signup', `nombreUsuario=${this.nombreUsuario.toLowerCase()}&email=${this.email}&password=${this.password}`, config)
                .then(() => {
                    // redirigir a otra página
                    window.location.href = "/index.html";
                })
                .catch(() => {
                    this.errorMsg = "Falló el intento de registro";
                   
                });
        },
    },
}).mount('#app');
