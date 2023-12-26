Vue.createApp({
    data() {
        return {
            titulo: '',
            genero: '',
            categorias: '',
            fechaDeEdicion: '',

            editorialSeleccionada: '',
            editoriales: [],
            nuevaEditorialVisible: false,
            nuevaEditorial: '',

            autorSeleccionado: '',
            autores: [],
            nuevoAutor: {
                nombreAutor: '',
                apellidoAutor: ''
            },

            ilustradorSeleccionado: '',
            ilustradores: [],
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
        flatpickr("#fechaDeEdicion", {
            dateFormat: "m/Y",
        });
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
            // Al hacer clic en el botón, muestra el campo para una nueva editorial
            this.nuevaEditorialVisible = true;
            // Limpia la selección de editorial existente
            this.editorialSeleccionada = '';
        },
        enviarFormulario() {
            // Crear el objeto que se enviará al backend
            const libroData = {
              ilustrador: this.ilustradorSeleccionado
                ? { id: this.ilustradorSeleccionado }  // Si se selecciona un ilustrador existente
                : {
                    nombreIlustrador: this.nuevoIlustrador.nombreIlustrador,
                    apellidoIlustrador: this.nuevoIlustrador.apellidoIlustrador
                  },  // Si se ingresa un nuevo ilustrador
              titulo: this.titulo,
              editorial: this.nuevaEditorialVisible
                ? { nombre: this.nuevaEditorial }  // Nueva editorial
                : { id: this.editorialSeleccionada },
              genero: this.genero,
              categorias: this.categorias.split(',').map(categoria => categoria.trim()), // Convertir a lista
              fechaDeEdicion: this.formatearFecha(this.fechaDeEdicion),
              autor: this.autorSeleccionado
                ? { id: this.autorSeleccionado }
                : {
                    nombreAutor: this.nuevoAutor.nombreAutor,
                    apellidoAutor: this.nuevoAutor.apellidoAutor
                  }
            };
          
            console.log("Datos enviados al servidor:", libroData);
          
            // Luego, envía el formulario usando Axios
            axios.post('/api/libros/guardarLibro', libroData)
              .then(response => {
                alert("Libro guardado o actualizado exitosamente");
                console.log("Libro guardado o actualizado exitosamente:", response.data);
                // Restablece los campos del formulario o realiza otras acciones necesarias
                this.titulo = '';
                this.editorialSeleccionada = '';
                this.genero = '';
                this.categorias = '';
                this.fechaDeEdicion = '';
                this.autorSeleccionado = '';
                this.nuevoAutor.nombreAutor = '';
                this.nuevoAutor.apellidoAutor = '';
                this.ilustradorSeleccionado = '';
                this.nuevoIlustrador.nombreIlustrador = '';
                this.nuevoIlustrador.apellidoIlustrador = '';
              })
              .catch(error => {
                console.error("Error al procesar el libro:", error);
                alert("Error al procesar el libro: " + error.response.data);
                // Maneja el error si es necesario
              });
          },
        formatearFecha(fecha) {
            return fecha ? new Date(fecha).toISOString().split('T')[0] : null;
        }
    }
}).mount("#formularioLibro");
