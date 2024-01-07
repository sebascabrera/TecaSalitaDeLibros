Vue.createApp({
    data() {
        return {
            titulo: '',
            genero: '',

            categoriaExistente: [],
            categorias: [],
            nuevaCategoria: {
                palabraCategoria: ''
            },

            editorialExistente: '',
            editoriales: [],
            
            autorSeleccionado: [],
            autores: [],

            ilustradorSeleccionado: '',
            ilustradores: [],

            fechaDeEdicion: ''
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
            console.log("genero seleccionado:", this.categoriaExistente);
            console.log("fecha De Edicion seleccionadas:", this.fechaDeEdicion);
            console.log("editorial Existente seleccionada:", this.editorialExistente);
            console.log("ilustrador Seleccionado :", this.ilustradorSeleccionado);
            console.log("autor Seleccionado :", this.autorSeleccionado);
        },
        getEditorialById(id) {
            return this.editoriales.find(editorial => editorial.id === id) || {};
        },
        getAutorById(id) {
            return this.autores.find(autor => autor.id === id) || {};
        },
        getIlustradorById(id) {
            return this.ilustradores.find(ilustrador => ilustrador.id === id) || {};
        },


        enviarFormulario() {
            const autoresTransformados = this.autorSeleccionado.map(autor => {
                return {
                    nombreAutor: autor.nombreAutor,
                    apellidoAutor: autor.apellidoAutor
                };
            })
            // Crear el objeto que se enviará al backend
            const libroData = {
                titulo: this.titulo,
                genero: this.genero,
                categorias: this.categoriaExistente ? this.categoriaExistente.map(categoria => categoria.id) : [],
                editorial: this.getEditorialById(this.editorialExistente), // Obtener objeto completo de editorial
                autor:this.autoresTransformados,                
                ilustrador: this.getIlustradorById(this.ilustradorSeleccionado), // Obtener objeto completo de ilustrador
                fechaDeEdicion: this.fechaDeEdicion
                //     autor: this.autorSeleccionado
                //     ? { id: this.autorSeleccionado }
                //   : null,
            };

            console.log("Datos enviados al servidor:", libroData);

            // Luego, envía el formulario usando Axios
            axios.post('/api/libros/guardarLibro', libroData)
                .then(response => {
                    alert("Libro guardado o actualizado exitosamente");
                    console.log("Libro guardado o actualizado exitosamente:", response.data);
                    // Restablece los campos del formulario o realiza otras acciones necesarias
                    this.titulo = '';
                    this.genero = '';
                    this.categorias = '';
                    this.editorial = '';
                    this.fechaDeEdicion = '';
                    this.autor = '';
                    this.ilustrador = '';
                    this.fechaDeEdicion = '';
                })
                .catch(error => {
                    console.error("Error al procesar el libro:", error);
                    console.log("Lista de lo enviado:", libroData);
                    alert("Error al procesar el libro: " + error.response.data);
                    // Maneja el error si es necesario
                    this.titulo = '';
                    this.genero = '';
                    this.categorias = '';
                    this.editorial = '';
                    this.fechaDeEdicion = '';
                    this.autor = '';
                    this.ilustrador = '';
                    this.fechaDeEdicion = '';
                });
        },
        formatearFecha(fecha) {
            return fecha ? new Date(fecha).toISOString().split('T')[0] : null;
        }
    }
}).mount("#formularioLibro");
