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
            axios.post('/auth/signup', `nombreUsuario=${this.nombreUsuario.toLowerCase()}&email=${this.email}&password=${this.password}`, config)
                .then(() => {
                    // redirigir a otra página
                    window.location.href = "../../index.html";
                })
                .catch(() => {
                    this.errorMsg = "Falló el intento de registro";
                    // Mostrar mensaje de error o manejar de alguna otra manera
                    // Puedes utilizar un Toast o mostrarlo de alguna forma en tu interfaz
                });
        },
    },
}).mount('#app');
