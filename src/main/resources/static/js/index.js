Vue.createApp({
    data() {
        return {
            titulo: '',
            genero: '',

            categoriaExistente: [],
            categorias: [],

            editorialExistente: [],
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
                    this.autores = response.data
                })
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
        capitalizarPalabras(frase) {            
            const palabras = frase.split(' ');        
            const palabrasCapitalizadas = palabras.map(palabra => {
                return palabra.charAt(0).toUpperCase() + palabra.slice(1).toLowerCase();
            });
            if (palabrasCapitalizadas[0] === 'El' || palabrasCapitalizadas[0] === 'La' || palabrasCapitalizadas[0] === 'Los' || palabrasCapitalizadas[0] === 'Las') {
                const nuevoTitulo = palabrasCapitalizadas.slice(1).join(' ') + ', ' + palabrasCapitalizadas[0];
                return nuevoTitulo;
            } else {            
                return palabrasCapitalizadas.join(' ');
            }
        },
        limiteDeISBN(codigo) {
            codigo = codigo.trim(); 
            if (!/^\d+$/.test(codigo)) {
                alert("El ISBN es incorrecto. Debe contener solo números.");
                this.isbn = this.isbn='';
            } else if (codigo.length > 13) {
                alert("El ISBN es incorrecto. No puede exceder los 13 dígitos.");
                this.isbn = this.isbn='';
            } else {
                return codigo;
            }
        },
        async enviarFormulario() {
           try {
              
            this.titulo = this.capitalizarPalabras(this.titulo);
                this.isbn = this.limiteDeISBN(this.isbn);
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
              
                    const response = await axios.post('/api/libros/guardarLibro', datosPrincipales, config)
                  
                  if (response.data) {
                    const nuevoLibroIdstr = response.data;
                    const nuevoLibroId = BigInt(nuevoLibroIdstr.replace(/\D/g, ''));
                    console.log("Se recibio del servidor: response.data: ", response.data);
                    console.log("Se recibio del servidor: convertido: ", nuevoLibroId);
                    if (Number.isSafeInteger(Number(nuevoLibroId))) {
                        this.enviarDatosAsociacion(Number(nuevoLibroId));
                        this.enviarDatosIlustrador(Number(nuevoLibroId));
                        this.enviarDatosCategorias(Number(nuevoLibroId));
                        this.enviarDatosEditorial(Number(nuevoLibroId));
                    } else {
                        console.error("El valor de 'id' es nulo o indefinido.");
                    }

                } else {
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
                     let autores = [];  
                if (this.autorSeleccionado.length > 1) {
                    autores = this.autorSeleccionado.map(autor => autor.id);
                    console.log("autores en if", autores)
                } else {
                     autores = [this.autorSeleccionado[0].id];
                    console.log("autores en else", autores)
                }        
                axios.post(`/api/libros/asociarDatos?autores=${autores.join(',')}&id=${id}`, null, config)
                    .then(response => {
                        console.log("autores post axios", autores);
                        this.autorSeleccionado = [];
                    })
                    .catch(error => {
                        console.error("Error al enviar datos de asociación al servidor:", error);
                    });
            } catch (error) {
                console.error("Error general al enviar datos de asociación al servidor:", error);
            }
        },        
        enviarDatosIlustrador(id) {
            try {
                const config = {
                    headers: {
                        'Content-Type': 'application/json',
                    },
                }
                let ilustradores = [];
                if (this.ilustradorSeleccionado.length > 1) {                    
                    ilustradores = this.ilustradorSeleccionado.map(autor => autor.id);
                } else if (this.ilustradorSeleccionado.length === 1) {                    
                    ilustradores = [this.ilustradorSeleccionado[0].id];
                }
                axios.post(`/api/libros/asociarIlustradores?ilustradores=${ilustradores.join(',')}&id=${id}`, null, config)
                .then(response => {
                    console.log("ilustradores post axios", ilustradores);
                    this.ilustradorSeleccionado = [];
                })
            } catch (error) {
                console.error("Error general al enviar datos de ilustradores al servidor:", error);
            }
        },
        enviarDatosCategorias(id){
            try{
                const config = {
                    headers: {
                        'Content-Type': 'application/json',
                    },
                }
                console.log("categorias pre axios", this.categoriaExistente);
                const idsCategorias = this.categoriaExistente.map(categoria => categoria.id);
                axios.post(`/api/libros/asociarCategorias?categorias=${idsCategorias.join(',')}&id=${id}`, null, config)
                .then(response => {
                    
                    console.log("categorias post axios", idsCategorias);
                    this.categoriaExistente = [];
                  
                })

            } catch (error) {
                console.error("Error general al enviar datos de categorias al servidor:", error);
            }
        },
        enviarDatosEditorial(id){
            const config = {
                headers: {
                    'Content-Type': 'application/json',
                },
            }
            console.log("editorial pre axios", this.editorialExistente);
            const editorial = [this.editorialExistente.id];
            axios.post(`/api/libros/asociarEditorial?editorial=${editorial.join(',')}&id=${id}`, null, config)
            .then(response =>{
                alert("Libro guardado o actualizado exitosamente");
                this.titulo = '';
                this.genero = '';
                this.fechaDeEdicion = '';
                this.isbn = '';
                this.editorialExistente = [];
            })
        },
    }
}).mount("#formularioLibro");
