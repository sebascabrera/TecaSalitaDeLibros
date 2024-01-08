Vue.createApp({
    data() {
        return {
            titulo: '',
            genero: '',

            categoriaExistente: [],
            categorias: [],

            editorialExistente: '',
            editoriales: [],

            autorSeleccionado: [],
            autores: [],

            ilustradorSeleccionado: [],
            ilustradores: [],

            fechaDeEdicion: '',

            isbn: ''
        }
    },
    watch: {
        titulo: function (newValue, oldValue) {
            this.manejarSeleccion();
        },
        genero: function (newValue, oldValue) {
            this.manejarSeleccion();
        },
        categoriaExistente: function (newValue, oldValue) {
            this.manejarSeleccion();
        },
        fechaDeEdicion: function (newValue, oldValue) {
            this.manejarSeleccion();
        },
        editorialExistente: function (newValue, oldValue) {
            this.manejarSeleccion();
        },
        autorSeleccionado: function (newValue, oldValue) {
            this.manejarSeleccion();
        },
        ilustradorSeleccionado: function (newValue, oldValue) {
            this.manejarSeleccion();
        },
        isbn: function (newValue, oldValue) {
            this.manejarSeleccion();
        },
    },
    created() {
        this.loadCategorias();
        this.loadAutores();
        this.loadEditoriales();
        this.loadIlustradores();
    },
    methods: {
        loadAutores() {
            axios.get("/api/autores/autores")
                .then(response => {
                    console.log("Datos de autores:", response.data);
                    this.autores = response.data;
                })
                .catch(error => {
                    console.error("Error loading autores: ", error);
                });
        },
        loadEditoriales() {
            axios.get("/api/editoriales/editoriales")
                .then(response => {
                    console.log("Datos de editoriales:", response.data);
                    this.editoriales = response.data;

                })
                .catch(error => {
                    console.error("Error loading editoriales: ", error);
                });
        },
        loadIlustradores() {
            axios.get("/api/ilustradores/ilustradores")
                .then(response => {
                    console.log("Datos de ilustradores:", response.data);
                    this.ilustradores = response.data;

                })
                .catch(error => {
                    console.error("Error loading ilustradores: ", error);
                });
        },
        loadCategorias() {
            axios.get("api/categorias/categorias")
                .then((response) => {
                    console.log("Datos de categorias:", response.data);
                    this.categorias = response.data;

                })
                .catch((error) => {
                    alert("Error loading libros: " + error);
                });
        },
        nuevaEditorialForm() {
            window.location.href = '../web/editorial/editorial.html'
        },
        nuevoAutorForm() {
            window.location.href = '../web/autor/autor.html';
        },
        nuevoIlustradorForm() {
            window.location.href = '../web/ilustrador/ilustrador.html';
        },
        nuevaCategoriaForm() {
            window.location.href = '../web/categoria/categoria.html';
        },
        manejarSeleccion() {
            console.log("Titulo seleccionado:", this.titulo);
            console.log("genero seleccionado:", this.genero);
            console.log("categoria seleccionada:", this.categorias);
            console.log("fecha De Edicion seleccionadas:", this.fechaDeEdicion);
            console.log("editorial Existente seleccionada:", this.editoriales);
            console.log("ilustrador Seleccionado :", this.ilustradores);
            console.log("autor Seleccionado :", this.autores);
            console.log("ISBN Seleccionado :", this.isbn);
        },
        enviarFormulario() {
            // Crear el objeto que se enviará al backend
            const config = {
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
            };

            const datosPrincipales = {
                'titulo': this.titulo,
                'genero': this.genero,
                'categoria': this.categoriaExistente.join(','),
                'fechaDeEdicion': this.formatearFecha(this.fechaDeEdicion),
                'isbn': this.isbn,
            };

            axios.post('/api/libros/guardarLibro', datosPrincipales, config)
                .then(response => {
                    const nuevoLibroId = response.data.id;
                    this.enviarDatosAsociacion(nuevoLibroId);
                })
                .catch(error => {
                    // Manejar errores
                });
            console.log("Datos enviados al servidor:", datosPrincipales)
        },
        enviarDatosAsociacion(nuevoLibroId) {
            const config = {
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
            };
            const datosAsociacion = {
                'libroId': nuevoLibroId,
                'editorial': this.editorialExistente,
                'autor': this.autorSeleccionado,
                'ilustradores': this.ilustradorSeleccionado,
            };
                console.log("previo al post asociarDatos: ", libroId, editorial, autor, ilustradores)
            axios.post('/api/libros/asociarDatos', datosAsociacion, config)
                .then(response => {
                    alert("Libro guardado o actualizado exitosamente");
                    // Restablecer campos del formulario o realizar otras acciones necesarias
                    this.titulo = '';
                    this.genero = '';
                    this.categoriaExistente = [];
                    this.editorialExistente = '';
                    this.fechaDeEdicion = '';
                    this.autorSeleccionado = [];
                    this.ilustradorSeleccionado = [];
                    this.isbn = '';
                })
                .catch(error => {
                    // Manejar errores
                });
                console.log("Datos enviados al servidor:", datosAsociacion)
        },
        formatearFecha(fecha) {
            return fecha ? new Date(fecha).toISOString().split('T')[0] : null;
        }
    }
}).mount("#formularioLibro");
