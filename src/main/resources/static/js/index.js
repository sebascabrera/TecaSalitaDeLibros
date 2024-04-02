Vue.createApp({
    data() {
        return {
            nombreUsuario: "",
            email: "",
            password: "",
            errorMsg: "",
            showSignUp: false,
            errorToast: null,
        };
    },
    methods: {
        signIn: function (event) {
            event.preventDefault();
            console.log("se hizo click en login")
            // Validación de campos
            if (!this.email || !this.password) {
                this.errorMsg = "Por favor, ingresa tu correo electrónico y contraseña.";
                this.errorToast.show();
                return;
            }

            // Realizar autenticación
            let config = {
                headers: {
                    'Content-Type': 'application/json'
                }
            };
            axios.post('/sig-in/auth/signin', { email: this.email, password: this.password }, config)
            .then(response => {
               
                window.location.href = response.data.redirectUrl;
                console.log("respuesta de login: ", response.data)
            })
            .catch(error => {
                if (error.response && error.response.status === 401) {
                    this.errorMsg = "Credenciales inválidas. Por favor, verifica tu correo electrónico y contraseña.";
                } else {
                    this.errorMsg = "Se produjo un error durante el inicio de sesión. Por favor, inténtalo de nuevo más tarde.";
                }
                if (this.errorToast) {
                    this.errorToast.show();
                }
            });
            
        },
        registroLink: function(event) {
            event.preventDefault();
            window.location.href = 'web/registro/registro.html';
            console.log("se hizo click en registro")
        }
    },
    mounted: function () {
        this.errorToast = new bootstrap.Toast(document.getElementById('danger-toast'));
    }
}).mount('#app');