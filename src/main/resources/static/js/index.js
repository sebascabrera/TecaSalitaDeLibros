Vue.createApp({
    data() {
        return {
            titulo: '',
            genero: '',

            categoriaExistente: '',
            categorias: [],
            nuevaCategoria: {
                palabraCategoria:''
            },
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
        this.loadCategorias();
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
            axios.get("/api/editoriales/editoriales")
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
        loadCategorias() {
            axios.get("api/categorias/categorias")
                .then((response) => {
                    this.categorias = response.data;
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
            titulo: this.titulo,

            fechaDeEdicion: this.fechaDeEdicion,

 editorial: this.nuevaEditorialVisible
    ? { nombre: this.nuevaEditorial }  // Nueva editorial
    : { id: this.editorialSeleccionada },  // Editorial existente

    categoria: this.categoriaExistente
    ? { id: this.categoriaExistente }
    : { palabraCategoria: this.nuevaCategoria.palabraCategoria },

              ilustrador: this.ilustradorSeleccionado
                ? { id: this.ilustradorSeleccionado }  // Si se selecciona un ilustrador existente
                : {
                    nombreIlustrador: this.nuevoIlustrador.nombreIlustrador,
                    apellidoIlustrador: this.nuevoIlustrador.apellidoIlustrador
                  },  // Si se ingresa un nuevo ilustrador


              genero: this.genero,
              categorias: this.categoriasexistentes ? this.categoriasexistentes.map(categoria => categoria.id) : [], // Convertir a lista
 

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
                this.autorSeleccionado = '';
                this.nuevoAutor.nombreAutor = '';
                this.nuevoAutor.apellidoAutor = '';
                this.ilustradorSeleccionado = '';
                this.nuevoIlustrador.nombreIlustrador = '';
                this.nuevoIlustrador.apellidoIlustrador = '';
                this.editorialSeleccionada = '';
                this.genero = '';
                this.categorias = '';
                this.fechaDeEdicion = '';              
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
