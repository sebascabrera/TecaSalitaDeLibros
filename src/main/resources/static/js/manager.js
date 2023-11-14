Vue.createApp({
    data() {
        return {
            titulo: "",
            autor: "",
            ilustrador: "",
            editorial: "", // Agregado
            genero: "", // Agregado
            outPut: "",
            nuevaCategoria: "",
            libros: [],
            listaGeneros: ["POESIA", "TEATRO", "NARRATIVA"],
            generosSeleccionado: null
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

                })
                .catch((error) => {
                    alert("Error loading libros: " + error);
                });
        },
        // handler for when user clicks add libros
        addlibros() {
            if (this.titulo.length > 1 && this.autor.length > 1 && this.ilustrador.length > 1 && this.generosSeleccionado.length > 1) {
                
                this.postlibros(this.titulo, this.autor, this.ilustrador, this.editorial, this.genero);
            } else {
                alert("Asegúrese de completar todos los campos.");
            }
        },
        // code to post a new libro using AJAX
        // on success, reload and display the updated data from the server
        postlibros(titulo, autor, ilustrador, editorial, genero) { // Agregados los parámetros
            axios.post("/api/libros", {
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

