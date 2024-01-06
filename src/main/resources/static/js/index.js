Vue.createApp({
    data() {
        return {
            titulo: '',
            genero: '',

            categoriaExistente: [],
            categorias: [],
            nuevaCategoria: {
                palabraCategoria:''
            },
            fechaDeEdicion: '',

            editorialExistente: [],
            editoriales: [],           
            nuevaEditorial:{
                nombre:''
            } ,

            autorSeleccionado: [],
            autores: [],
            filtroAutor: '',
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
    watch: {
        categoriaExistente: function(newValue, oldValue) {
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
        nuevaEditorialForm(){
            window.location.href = '../web/editorial/editorial.html'
        },
        nuevoAutorForm() {
            window.location.href = '../web/autor/autor.html'; 
        },
        nuevoIlustradorForm() {
            window.location.href = '../web/ilustrador/ilustrador.html'; 
        },
        nuevaCategoriaForm(){
            window.location.href = '../web/categoria/categoria.html';
        },
        manejarSeleccion() {
            console.log("Categorías seleccionadas:", this.categoriaExistente);
            console.log("Categorías seleccionadas:", this.autorSeleccionado);
          },
         

        enviarFormulario() {
            // Crear el objeto que se enviará al backend
            const libroData = {
            titulo: this.titulo,

            fechaDeEdicion: this.fechaDeEdicion,

 editorial: this.editorialExistente
    ? { id: this.editorialExistente }  
    : null,  
    editoriales: this.editorialExistente,
    categoria: this.categoriaExistente
  ? { id: this.categoriaExistente }
  : null,
  categorias: this.categoriaExistente ? this.categoriaExistente.map(categoria => categoria.id) : [], // Convertir a lista

              ilustrador: this.ilustradorSeleccionado
                ? { id: this.ilustradorSeleccionado }  // Si se selecciona un ilustrador existente
                : {
                    nombreIlustrador: this.nuevoIlustrador.nombreIlustrador,
                    apellidoIlustrador: this.nuevoIlustrador.apellidoIlustrador
                  },  // Si se ingresa un nuevo ilustrador


              genero: this.genero,
              
             

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
                this.autorSeleccionado = '';                
                this.ilustradorSeleccionado = '';                
                this.editoriales = '';
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
    }, computed: {
        filteredAutores() {
            const filtro = this.filtroAutor.toLowerCase();
            return this.autores.filter(autor =>
                autor.apellidoAutor.toLowerCase().includes(filtro) || autor.nombreAutor.toLowerCase().includes(filtro)
            );
        }
    }
}).mount("#formularioLibro");
