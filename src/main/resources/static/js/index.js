Vue.createApp({
    data() {
        return {
            titulo: "",
            nombreAutor: "",
            apellidoAutor: "",
            ilustrador: "",
            editorial: "",
            generoSeleccionado: null,
            libroSeleccionado: null,
            libroSeleccionadoIndex: null,
            categoriasInput: '',
            categorias: [],
            libros: [],
            listaGeneros: ["POESIA", "TEATRO", "NARRATIVA"],
            categoriasexistentes: [],
            categoriasSeleccionadas: []
        };
    },
    created() {
        this.loadData();
    },
    methods: {
        loadData() {
            axios.get("/api/libros")
                .then((response) => {
                    this.libros = response.data;
                    this.categoriasexistentes = response.data.categorias;
                })
                .catch((error) => {
                    alert("Error loading libros: " + error);
                });
        },
        addLibros() {
            if (this.libros.length > 0) {
                // Abre el modal para confirmar la edición antes de enviar
                this.mostrarModalEditarLibros();
            } else {
                alert("Agregue al menos un libro antes de enviar.");
            }
        },
        mostrarModalEditarLibros() {
            // Abre el modal
            new bootstrap.Modal(document.getElementById('editarLibrosModal')).show();
        },
        editarLibro(libro) {
            // Asigna directamente el objeto libro seleccionado
  
            this.libroSeleccionado = this.libros[index];
    
            // Establecer los datos del libro seleccionado en el formulario del modal
            this.titulo = libro.titulo;
            this.nombreAutor = libro.nombreAutor;
            this.apellidoAutor = libro.apellidoAutor;
            this.ilustrador = libro.ilustrador;
            this.editorial = libro.editorial;
            this.generoSeleccionado = libro.genero;
            this.categoriasInput = libro.categorias.join(', ');
            this.categoriasSeleccionadas = libro.categorias;
    
            // Abre el modal de edición
            this.mostrarModalEditarLibros();
        },
        enviarLibros() {
            if (this.libroSeleccionado) {
                // Si hay un libro seleccionado, actualiza sus valores
                this.libros[this.libroSeleccionadoIndex] = {
                        titulo: this.titulo,
                        nombreAutor: this.nombreAutor,
                        apellidoAutor: this.apellidoAutor,
                        ilustrador: this.ilustrador,
                        editorial: this.editorial,
                        genero: this.generoSeleccionado,
                        categorias: this.categoriasInput.split(',').map(categoria => categoria.trim())
                                    };
            } else {
                // Si no hay libro seleccionado, agrega uno nuevo
                this.libros.push({
                    titulo: this.titulo,
                    nombreAutor: this.nombreAutor,
                    apellidoAutor: this.apellidoAutor,
                    ilustrador: this.ilustrador,
                    editorial: this.editorial,
                    genero: this.generoSeleccionado,
                    categorias: this.categorias
                });
            }
           
            new bootstrap.Modal(document.getElementById('editarLibrosModal')).hide();
            // Aquí puedes enviar los libros al servidor
            axios.post("/api/libros-varios", this.libros)
                .then((response) => {
                    this.loadData();
                    this.clearData();
                    // Cierra el modal después de enviar los libros
                    new bootstrap.Modal(document.getElementById('editarLibrosModal')).hide();
                })
                .catch((error) => {
                    alert("Error al crear/editar libros: " + error);
                });
        },
        addLibroLocal() {
            if (this.titulo.length > 1 && this.nombreAutor.length > 1 && this.apellidoAutor.length > 1 && this.ilustrador.length > 1 && this.generoSeleccionado && this.categoriasInput.length > 1) {
                this.categorias = this.categoriasInput.split(',').map(categoria => categoria.trim());
                this.categorias = this.categorias.concat(this.categoriasSeleccionadas);

                this.libros.push({
                    titulo: this.titulo,
                    nombreAutor: this.nombreAutor,
                    apellidoAutor: this.apellidoAutor,
                    ilustrador: this.ilustrador,
                    editorial: this.editorial,
                    genero: this.generoSeleccionado,
                    categorias: this.categorias
                });

                this.clearData();
            } else {
                alert("Asegúrese de completar todos los campos.");
            }
        },
        clearData() {
            this.nombreAutor = "";
            this.apellidoAutor = "";
            this.ilustrador = "";
            this.titulo = "";
            this.editorial = "";
            this.generoSeleccionado = null;
            this.categoriasInput = '';
            this.categoriasSeleccionadas = [];
        }
    }
}).mount("#api-libros-form");