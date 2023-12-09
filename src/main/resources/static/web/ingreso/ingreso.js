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
            let config = {
                headers: {
                    'content-type': 'application/x-www-form-urlencoded'
                }
            };
            axios.post('/auth/signin', `email=${this.email}&password=${this.password}`, config)
                .then(response => window.location.href = "/web/ingreso.html")
                .catch(() => {
                    this.errorMsg = "Sign in failed, check the information";
                    this.errorToast.show();
                });
        },
        signUp: function (event) {
            event.preventDefault();
            let config = {
                headers: {
                    'content-type': 'application/x-www-form-urlencoded'
                }
            };
            axios.post('/auth/signup', `nombreUsuario=${this.nombreUsuario}&email=${this.email}&password=${this.password}`, config)
                .then(() => this.signIn(event))
                .catch(() => {
                    this.errorMsg = "Sign up failed, check the information";
                    this.errorToast.show();
                });
        },
        showSignUpToogle: function () {
            this.showSignUp = !this.showSignUp;
        },
    },
    mounted: function () {
        this.errorToast = new bootstrap.Toast(document.getElementById('danger-toast'));
    }
}).mount('#app');
