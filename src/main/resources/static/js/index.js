Vue.createApp({
    data() {
        return {
            titulo: "",
            autor: "",
            ilustrador: "",
            editorial: "",
            generoSeleccionado: null,
            categoriasInput: '',
            categorias: [],
            listaGeneros: ["POESIA", "TEATRO", "NARRATIVA"],
            categoriasexistentes: [],
            categoriasSeleccionadas: []
        };
    },
    created() {
        this.loadCategorias();
    },
    methods: {
        loadCategorias() {
            axios.get("/api/libros/categorias")
                .then((response) => {
                    this.categoriasexistentes = response.data;
                })
                .catch((error) => {
                    alert("Error loading categorías: " + error);
                });
        },
        addLibros() {
            if (this.titulo && this.autor && this.ilustrador && this.generoSeleccionado && this.categoriasInput) {
                this.categorias = this.categoriasInput.split(',').map(categoria => categoria.trim());
                this.categorias = this.categorias.concat(this.categoriasSeleccionadas);

                this.postLibros();
            } else {
                alert("Asegúrese de completar todos los campos.");
            }
        },
        postLibros() {
            axios.post("/api/libros", {
                "titulo": this.titulo,
                "autor": this.autor,
                "ilustrador": this.ilustrador,
                "editorial": this.editorial,
                "genero": this.generoSeleccionado,
                "categorias": this.categorias
            })
                .then((response) => {
                    alert("Libro creado exitosamente");
                    this.clearData();
                })
                .catch((error) => {
                    alert("Error al crear libro: " + error);
                });
        },
        clearData() {
            this.autor = "";
            this.ilustrador = "";
            this.titulo = "";
            this.editorial = "";
            this.generoSeleccionado = null;
            this.categoriasInput = '';
            this.categoriasSeleccionadas = [];
        }
    }
}).mount("#api-libros-form");
