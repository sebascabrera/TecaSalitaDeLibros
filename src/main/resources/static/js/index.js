Vue.createApp({
    
    data(){
        return {
            // Define los datos de Vue que deseas usar en el formulario
            // Puedes agregar más datos según sea necesario
            titulo: '',
            editorial: '',
            genero: [],
            categorias: '',
            fechaDeEdicion: '',
            nombreAutor: '',
            apellidoAutor: '',
            nombreIlustrador: '',
            apellidoIlustrador: ''
        }

    },
    created() {
        this.loadData();
    },
    methods: {
        loadData() {
            axios.get("api/libros/libros")
                .then((response) => {
                    this.libros = response.data;
                    this.categoriasexistentes = response.data.categorias;
                })
                .catch((error) => {
                    alert("Error loading libros: " + error);
                });
        },
        // Método para manejar el envío del formulario
        enviarFormulario: function () {
            // Aquí puedes realizar cualquier lógica necesaria antes de enviar el formulario

            // Luego, envía el formulario usando Axios
            axios.post('/api/guardar', {
                titulo: this.titulo,
                editorial: this.editorial,
                genero: this.genero,
                categorias: this.categorias,
                fechaDeEdicion: this.fechaDeEdicion,
                nombreAutor: this.nombreAutor,
                apellidoAutor: this.apellidoAutor,
                nombreIlustrador: this.nombreIlustrador,
                apellidoIlustrador: this.apellidoIlustrador
            }).then(response => {
                // Maneja la respuesta si es necesario
            }).catch(error => {
                // Maneja el error si es necesario
            });
        }
    }

}).mount("#formularioLibro");
