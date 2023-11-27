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
            if (this.titulo.length > 1 && this.autor.length > 1 && this.ilustrador.length > 1 && this.generoSeleccionado && this.categoriasInput.length > 1) {
                this.categorias = this.categoriasInput.split(',').map(categoria => categoria.trim());
                this.categorias = this.categorias.concat(this.categoriasSeleccionadas);

                this.postlibros(this.titulo, this.autor, this.ilustrador, this.editorial, this.generoSeleccionado, this.categorias);
            } else {
                alert("Asegúrese de completar todos los campos.");
            }
        },
        postLibros(titulo, autor, ilustrador, editorial, genero, categorias) {
            axios.post("/api/libros", {
                "titulo": titulo,
                "autor": autor,
                "ilustrador": ilustrador,
                "editorial": editorial,
                "genero": genero,
                "categorias": categorias
            })
                .then((response) => {
                    this.loadData();
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
