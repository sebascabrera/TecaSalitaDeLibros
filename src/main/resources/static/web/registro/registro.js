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
        signUp: function (event) {
            event.preventDefault();
            let config = {
                headers: {
                    'content-type': 'application/x-www-form-urlencoded'
                }
            };
            axios.post('/auth/signup', `nombreUsuario=${this.nombreUsuario}&email=${this.email}&password=${this.password}`, config)
                .then(() => {
                    // Puedes redirigir a otra página después del registro si lo deseas
                    window.location.href = "/ingreso.html";
                })
                .catch(() => {
                    this.errorMsg = "Sign up failed, check the information";
                    // Mostrar mensaje de error o manejar de alguna otra manera
                    // Puedes utilizar un Toast o mostrarlo de alguna forma en tu interfaz
                });
        },
    },
}).mount('#app');
