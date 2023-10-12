Vue.createApp({
    data() {
        return {
            titulo: "",
            autor: "",
            ilustrador: "",
            editorial: "", // Agregado
            genero: "", // Agregado
            outPut: "",
            libros: []
        };
    },
    created() {
        this.loadData();
    },
    methods: {
        // load and display JSON sent by server for /libros
        loadData() {
            axios.get("/libros")
                .then((response) => {
                    // handle success
                    this.outPut = response.data;
                    this.libros = response.data._embedded.libros;
                })
                .catch((error) => {
                    alert("Error loading libros: " + error);
                });
        },
        // handler for when user clicks add libros
        addlibros() { // Corregido el nombre de la función
            if (this.titulo.length > 1 && this.autor.length > 1 && this.ilustrador.length > 1) {
                this.postlibros(this.titulo, this.autor, this.ilustrador, this.editorial, this.genero); // Agregados los últimos dos parámetros
            }
        },
        // code to post a new libro using AJAX
        // on success, reload and display the updated data from the server
        postlibros(titulo, autor, ilustrador, editorial, genero) { // Agregados los parámetros
            axios.post("/libros", {
                "titulo": titulo,
                "autor": autor,
                "ilustrador": ilustrador,
                "editorial": editorial, // Agregado
                "genero": genero // Agregado
            })
                .then((response) => {
                    // handle success
                    this.loadData();
                    this.clearData();
                })
                .catch((error) => {
                    // handle error
                    alert("Error to create libro: " + error);
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
}).mount("#app");

