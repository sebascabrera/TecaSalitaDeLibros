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
         /*    console.log("Titulo seleccionado:", this.titulo);
            console.log("genero seleccionado:", this.genero);
            console.log("categoria seleccionada:", this.categorias);
            console.log("fecha De Edicion seleccionadas:", this.fechaDeEdicion);
            console.log("editorial Existente seleccionada:", this.editoriales);
            console.log("ilustrador Seleccionado :", this.ilustradores);
            console.log("autor Seleccionado :", this.autores);
            console.log("categoria seleccionada:", this.categorias);
            console.log("ISBN Seleccionado :", this.isbn); */
            
          
        },
        async enviarFormulario() {
            try {
                const datosPrincipales = {
                    'titulo': this.titulo,
                    'genero': this.genero,
                    'fechaDeEdicion': this.fechaDeEdicion,
                    'isbn': this.isbn,
                };
        
                const config = {
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                    },
                };
        
                const response = await axios.post('/api/libros/guardarLibro', datosPrincipales, config);
        
                 if (response.data) {
                    const nuevoLibroIdstr= response.data; 
                    const nuevoLibroId = BigInt(nuevoLibroIdstr.replace(/\D/g, ''));
                    console.log("Se recibio del servidor: response.data: ", response.data);
                    console.log("Se recibio del servidor: convertido: ", nuevoLibroId);
                    if (Number.isSafeInteger(Number(nuevoLibroId))) {
                        this.enviarDatosAsociacion(Number(nuevoLibroId));
                    } else {
                        console.error("El valor de 'id' es nulo o indefinido.");
                    }
        
                } else {
                    // Manejar el caso en que no se recibe un ID válido
                    console.log("No se recibió un ID válido del servidor. response.data: ", response.data);
                }
            } catch (error) {
                console.error("Error al enviar el formulario principal:", error);
            }
        },        
        enviarDatosAsociacion(id) {
            try {
              const config = {
                headers: {
                  'Content-Type': 'application/json', 
                },
              };
          
              const datosAsociacion = {
                'id': id,
                'categorias': this.categoriaExistente.map(categoria => {
                    return {
                      id: categoria.id, 
                      palabraCategoria: categoria.palabraCategoria,
                      libros: categoria.libros || [] 
                    };
                  }),
                'editorial': [this.editorialExistente].map(editorial => { return {
                    id: editorial.id,
                    nombreEditorial: editorial.nombreEditorial,                    
                    libros: editorial.libros || []
                };}),
                'autores': [this.autorSeleccionado].map(autor =>{ return{
                    id: autor.id,
                    nombreAutor: autor.nombreAutor,
                    apellidoAutor: autor.apellidoAutor,
                    libros: autor.libros || []
                }}),
                'ilustradores': [this.ilustradorSeleccionado].map(ilustrador =>{return{
                    id: ilustrador.id,
                    nombreIlustrador: ilustrador.nombreIlustrador,
                    apellidoIlustrador: ilustrador.apellidoIlustrador,
                    libros: ilustrador.libros || []
                }}),            
              };
          
              console.log("ID del nuevo libro asociado:", id);
          
              axios.post('/api/libros/asociarDatos', datosAsociacion, config).then
              console.log("autores", this.autores);  
              console.log("ilustradores", this.ilustradores); 
              console.log("categorias", this.categorias);  
              console.log("editorial", this.editoriales);
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
          
              console.log("Post asociarDatos: ", datosAsociacion);
            } catch (error) {
              console.error("Error al enviar datos de asociación al servidor:", error);
            }
          },
          
        
    }
}).mount("#formularioLibro");
