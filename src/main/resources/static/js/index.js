Vue.createApp({

    data() {
        return {
            // Define los datos de Vue que deseas usar en el formulario
            // Puedes agregar más datos según sea necesario
            titulo: '',
            editorial: '',
            genero: [],
            categorias: '',
            fechaDeEdicion: '',

            autorSeleccionado: '',  // ID del autor seleccionado
            autores: [],  // Lista de autores disponibles
            nuevoAutor: {
                nombreAutor: '',
                apellidoAutor: ''
            },

            editoriales: [],  // Lista de editoriales disponibles
            editorialSeleccionada: '',  // ID de la editorial seleccionada
            nuevaEditorialVisible: false,
            nuevaEditorial: '',

            ilustradorSeleccionado: '',  // ID del ilustrador seleccionado
            ilustradores: [],  // Lista de ilustradores disponibles            
            nuevoIlustrador: {
                nombreIlustrador: '',
                apellidoIlustrador: ''
            }

        }

    },
    created() {
        this.loadData();
        this.loadAutores();
        this.loadEditoriales();
        this.loadIlustradores();
    },
    methods: {

        loadAutores() {
            axios.get("/api/autores/autores")
                .then(response => {
                    this.autores = response.data;
                })
                .catch(error => {
                    console.error("Error loading autores: ", error);
                });
        },
        loadEditoriales() {
            axios.get("/api/editoriales/editorial")
                .then(response => {
                    this.editoriales = response.data;
                })
                .catch(error => {
                    console.error("Error loading editoriales: ", error);
                });
        },
        loadIlustradores() {
            axios.get("/api/ilustradores/ilustradores")
                .then(response => {
                    this.ilustradores = response.data;
                })
                .catch(error => {
                    console.error("Error loading ilustradores: ", error);
                });
        },
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
        mostrarNuevaEditorial() {
            this.nuevaEditorialVisible = true;
        },

        // Método para manejar el envío del formulario
        enviarFormulario: function () {
            // Aquí puedes realizar cualquier lógica necesaria antes de enviar el formulario
            console.log("Datos enviados al servidor:", {
                titulo: this.titulo,
                editorial: this.editorialSeleccionada,
                genero: this.genero,
                categorias: this.categorias,
                fechaDeEdicion: this.fechaDeEdicion,
                nombreAutor: this.nuevoAutor.nombreAutor,
                apellidoAutor: this.nuevoAutor.apellidoAutor,
                nombreIlustrador: this.nuevoIlustrador.nombreIlustrador,
                apellidoIlustrador: this.nuevoIlustrador.apellidoIlustrador
            });
            this.genero = document.querySelector('input[name="genero"]:checked').value;
            // Luego, envía el formulario usando Axios
            axios.post('/api/libros/guardar-libro', {
                titulo: this.titulo,
                editorial: this.editorialSeleccionada,
                genero: this.genero,
                categorias: this.categorias,
                fechaDeEdicion: this.fechaDeEdicion,
                nombreAutor: this.nuevoAutor.nombreAutor,
                apellidoAutor: this.nuevoAutor.apellidoAutor,
                nombreIlustrador: this.nuevoIlustrador.nombreIlustrador,
                apellidoIlustrador: this.nuevoIlustrador.apellidoIlustrador
            }).then(response => {
                console.log("Libro guardado o actualizado exitosamente:", response.data);
                // Maneja la respuesta si es necesario
            }).catch(error => {
                // Maneja el error si es necesario
                console.error("Error al procesar el libro:", error);
            });
        }
    }

}).mount("#formularioLibro");
