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
            axios.post('/auth/signin', { email: this.email, password: this.password }, config)
                .then(response => {
                    // Redirigir al usuario a una página de inicio personalizada
                    window.location.href = "/web/ingreso/ingreso.html";
                })
                .catch(error => {
                    if (error.response.status === 401) {
                        this.errorMsg = "Credenciales inválidas. Por favor, verifica tu correo electrónico y contraseña.";
                    } else {
                        this.errorMsg = "Se produjo un error durante el inicio de sesión. Por favor, inténtalo de nuevo más tarde.";
                    }
                    this.errorToast.show();
                });
        },
        registroLink: function(event) {
            event.preventDefault();
            window.location.href = '/web/registro/registro.html';
        }
    },
    mounted: function () {
        this.errorToast = new bootstrap.Toast(document.getElementById('danger-toast'));
    }
}).mount('#app');
