//import axios from 'axios';
Vue.createApp({
    data() {
        return {
            titulo: "",
            autor: "",
            ilustrador: "",
            editorial: "", // Agregado
            genero: "", // Agregado
            outPut: "",
            categoriasInput: '',
            categorias: [],
            libros: [],
            listaGeneros: ["POESIA", "TEATRO", "NARRATIVA"],
            generosSeleccionado: null,
            categoriasexistentes: [],
            categoriasSeleccionadas: []
        };
    },
    created() {
        this.loadData();
    },
    methods: {
        // load and display JSON sent by server for /libros
        loadData() {
            axios.get("/api/libros")
                .then((response) => {
                    console.log(response.data);
                    // handle success
                    this.outPut = response.data;
                    this.libros = response.data.libros;
                    this.listaGeneros = ["POESIA", "TEATRO", "NARRATIVA"];
                    this.categoriasexistentes = response.data.libros.categorias
                })
                .catch((error) => {
                    alert("Error loading libros: " + error);
                });
        },
        // handler for when user clicks add libros
        addlibros() {
            if (this.titulo.length > 1 && this.autor.length > 1 && this.ilustrador.length > 1 && this.generosSeleccionado.length > 1 && this.categoriasInput.length > 1) {
                // Separar las categorías ingresadas por coma y eliminar espacios en blanco
                this.categorias = this.categoriasInput.split(',').map(categoria => categoria.trim());

                // Combina las categorías ingresadas con las categorías existentes seleccionadas
                this.categorias = this.categorias.concat(this.categoriasSeleccionadas);

                this.postlibros(this.titulo, this.autor, this.ilustrador, this.editorial, this.genero, this.categorias);
                return true;
            } else {
                alert("Asegúrese de completar todos los campos.");
                return false;
            }
        },


        postlibros(titulo, autor, ilustrador, editorial, genero, categorias) {
            axios.post("/api/libros", {
                "titulo": titulo,
                "autor": autor,
                "ilustrador": ilustrador,
                "editorial": editorial,
                "genero": genero,
                "categorias": categorias  // Enviar las categorías al servidor
            })
                .then((response) => {
                    // handle success
                    this.loadData();
                    this.clearData();
                })
                .catch((error) => {
                    // handle error
                    alert("Error al crear libro: " + error);
                });
        },
        clearData() {
            this.autor = "";
            this.ilustrador = "";
            this.titulo = "";
            this.editorial = ""; // Agregado
            this.genero = ""; // Agregado
        }
    }
}).mount("#api-libros-form");

