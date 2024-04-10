Vue.createApp({
    data() {
        return {
            nombreUsuario: "",
            email: "",
            password: "",
            errorMsg: "",
            showSignUp: false,
            showErrorToast: false 
        };
    },
    methods: {
        signIn: function (event) {
            event.preventDefault();
            console.log("se hizo click en login")
            console.log("se envia mail:", this.email)
            console.log("se envia password:", this.password)
            // Validación de campos
            if (!this.email || !this.password) {
                this.errorMsg = "Por favor, ingresa tu correo electrónico y contraseña.";
                this.showErrorToast = true; // Mostramos el toast de error
                return;
            }
            let config = {
                headers: {
                    'Content-Type': 'application/json'
                }
            };        
           // axios.post('/auth/signin',`email=${this.email}&password=${this.password}`, config)
            axios.post('/auth/signin', { email: this.email, password: this.password }, config)
            .then(response => {
                if (response.status === 200) {
                    window.location.href = "/ingreso.html";
                }
            })
            .catch(error => {
                if (error.response && error.response.status === 401) {
                    this.errorMsg = "Credenciales inválidas. Por favor, verifica tu correo electrónico y contraseña.";
                    this.showErrorToast = true; 
                } else {
                    this.errorMsg = "Se produjo un error durante el inicio de sesión. Por favor, inténtalo de nuevo más tarde.";
                    this.showErrorToast = true; 
                }
                this.showErrorToast = true;                 
            });
        },        
        registroLink: function(event) {
            event.preventDefault();
            window.location.href = 'registro.html';
            console.log("se hizo click en registro")
        }
    }
}).mount('#app');
