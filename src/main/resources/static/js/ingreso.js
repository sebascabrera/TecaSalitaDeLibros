Vue.createApp({
    data() {
        return {
            librosOriginales:[],
            libros: [],
            valorBusqueda: '',
            checked: [],
            claves: [],
            autores: [],
            ilustradores:[],
            categorias: [],
            librosFiltrados: [],
            esAdmin: false,
            librosMostrados: 10,
            currentPage: 1,
            pageSize: 10,
        }
    },
    computed: {
        totalPages() {
            return Math.ceil(this.libros.length / this.pageSize);
        },
        librosPaginados() {
            const startIndex = (this.currentPage - 1) * this.pageSize;
            return this.libros.slice(startIndex, startIndex + this.pageSize);
        }
    },
    created() {
        this.loadLibros();
        this.loadAtributos();
        this.loadAutores();
        this.loadIlustradores();
        this.loadCategorias();
    },
    methods: {
        nextPage() {
            if (this.currentPage < this.totalPages) {
                this.currentPage++;
            }
        },
        previousPage() {
            if (this.currentPage > 1) {
                this.currentPage--;
            }
        },
        loadAtributos() {
            axios.get("/api/libros/atributos")
            .then(response => {
                const data = response.data;
                for (let i = 0; i < data.length; i++) {
                    if (data[i] !== 'fechaDeEdicion' && data[i] !== 'id') {
                        this.claves.push(data[i]);
                    }
                }
            })
        },
        loadLibros() {
            axios.get("/api/libros/libros")
                .then(response => {
                    this.librosOriginales = response.data; 
                    this.libros = this.librosOriginales.slice(); 
                    this.libros.sort((a, b) => (a.titulo > b.titulo) ? 1 : -1);
                    console.log("Datos de libros:", this.librosPaginados);
                    this.libros.forEach(libro => {
                        this.fetchCubierta(libro.isbn);
                        console.log("libro.isbn:", libro.isbn);
                    });
                })
                .catch(error => {
                    console.error('Error al cargar libros:', error);
                    console.log("Datos de libros:", error);
                });
        },
        fetchCubierta(isbn) {
            fetch(`https://www.googleapis.com/books/v1/volumes?q=isbn:${isbn}`)
                .then(response => response.json())
                .then(data => {
                    if (data.totalItems > 0) {
                        const bookInfo = data.items[0].volumeInfo;
                        if (bookInfo.imageLinks && bookInfo.imageLinks.thumbnail) {
                            console.log("URL de la imagen de la cubierta:", bookInfo.imageLinks.thumbnail);
                            const cubiertaUrl = bookInfo.imageLinks.thumbnail;
                            const libro = this.libros.find(libro => libro.isbn === isbn);
                            if (libro) {
                                libro.cubiertaUrl = cubiertaUrl;
                            }

                        } else {
                            console.log("El libro no tiene una imagen de cubierta disponible.");
                            this.fetchOpenLibraryCubierta(isbn);

                        }
                    } else {
                        console.log("No se encontraron libros con el ISBN proporcionado.");
                        this.fetchOpenLibraryCubierta(isbn);

                    }
                })
                .catch(error => {
                    console.error('Error en la solicitud de la API de Google Books:', error);
                });
        },
        fetchOpenLibraryCubierta(isbn) {
            fetch(`https://covers.openlibrary.org/b/isbn/${isbn}-M.jpg`)
                .then(response => {
                    if (response.ok) {
                        console.log("URL de la imagen de la cubierta (Open Library):", response.url);
                        const libro = this.libros.find(libro => libro.isbn === isbn);
                        if (libro) {
                            libro.cubiertaUrl = response.url;
                        }
                    } else {
                        console.log("El libro no tiene una imagen de cubierta disponible en Open Library.");
                        this.fetchRandomHouseCubierta(isbn);
                    }
                })
                .catch(error => {
                    console.error('Error en la solicitud de la API de Cubiertas de Open Library:', error);
                    this.fetchRandomHouseCubierta(isbn);
                });
        },
        fetchRandomHouseCubierta(isbn) {
            const username = 'testuser';
            const password = 'testpassword';
            const url = `https://reststop.randomhouse.com/resources/title/${isbn}`;
            const headers = new Headers();
            headers.set('Authorization', 'Basic ' + btoa(username + ':' + password));
            fetch(url, {
                method: 'GET',
                headers: headers
            })
                .then(response => {

                    if (response.ok) {
                        return response.json();
                    } else {
                        throw new Error('Error en la solicitud a la API externa de cubiertas de libros');
                    }
                })
                .then(data => {
                    console.log("URL de la imagen de la cubierta (API externa):", cubiertaUrl);
                    const libro = this.libros.find(libro => libro.isbn === isbn);
                    if (libro) {
                        libro.cubiertaUrl = data.url;
                    }
                })
                .catch(error => {
                    console.error('Error en la solicitud de la API externa de cubiertas de libros:', error);
                });
        },
        filtro() {
            if (this.valorBusqueda.trim() === '' && this.checked.length === 0) {
                this.libros = this.libros;
            } else {
                let librosFiltrados = this.libros.filter(libro => {
                    if (this.valorBusqueda.trim() === '') return true; 
                    return libro.titulo.toLowerCase().includes(this.valorBusqueda.toLowerCase()) ||
                           libro.autores.some(autor => `${autor.nombreAutor} ${autor.apellidoAutor}`.toLowerCase().includes(this.valorBusqueda.toLowerCase())) ||
                           libro.ilustradores.some(ilustrador => `${ilustrador.nombreIlustrador} ${ilustrador.apellidoIlustrador}`.toLowerCase().includes(this.valorBusqueda.toLowerCase())) ||
                           (libro.categorias && libro.categorias.some(categoria => categoria.palabraCategoria.toLowerCase().includes(this.valorBusqueda.toLowerCase()))) ||
                           libro.editorial.nombreEditorial.toLowerCase().includes(this.valorBusqueda.toLowerCase()) ||
                           libro.isbn.toLowerCase().includes(this.valorBusqueda.toLowerCase())||
                           libro.genero.toLowerCase().includes(this.valorBusqueda.toLowerCase())||
                           libro.fechaDeEdicion.toLowerCase().includes(this.valorBusqueda.toLowerCase());

                });
                if (this.checked.length > 0) {
                    librosFiltrados = librosFiltrados.filter(libro => {
                        return this.checked.some(clave => {
                            if (clave === 'autores') {
                                return libro.autores.some(autor => `${autor.nombreAutor} ${autor.apellidoAutor}`.toLowerCase().includes(this.valorBusqueda.toLowerCase()));
                            }
                            if (clave === 'ilustradores') {
                                return libro.ilustradores.some(ilustrador => `${ilustrador.nombreIlustrador} ${ilustrador.apellidoIlustrador}`.toLowerCase().includes(this.valorBusqueda.toLowerCase()));
                            }
                            if (clave === 'categorias') {
                                return libro.categorias && libro.categorias.some(categoria => categoria.palabraCategoria.toLowerCase().includes(this.valorBusqueda.toLowerCase()));
                            }
                            if (clave === 'editorial') {
                                return libro.editorial.nombreEditorial.toLowerCase().includes(this.valorBusqueda.toLowerCase());
                            }
                            if (clave === 'titulo') {
                                return libro.titulo.toLowerCase().includes(this.valorBusqueda.toLowerCase());
                            }
                            if (clave === 'genero') {
                                return libro.genero.toLowerCase().includes(this.valorBusqueda.toLowerCase());
                            }
                            if (clave === 'isbn') {
                                return libro.isbn.toLowerCase().includes(this.valorBusqueda.toLowerCase());
                            }
                            if (clave === 'fechaDeEdicion') {
                                return libro.fechaDeEdicion.toLowerCase().includes(this.valorBusqueda.toLowerCase());
                            }
                            return false;
                        });
                    });
                }
        
                this.libros = librosFiltrados;
            }
        },
        loadAutores() {
            axios.get("/api/autores/autores")
                .then(response => {
                    console.log("Datos de autores:", response.data);
                    this.autores = response.data.sort((a, b) => {                        
                        const apellidoA = a.apellidoAutor; 
                        const apellidoB = b.apellidoAutor;        
                        if (apellidoA < apellidoB) {
                            return -1;
                        }
                        if (apellidoA > apellidoB) {
                            return 1;
                        }
                        return 0; 
                    });
                })
                .catch(error => {
                    console.error("Error cargando autores: ", error);
                });
        },
        loadIlustradores() {
            axios.get("/api/ilustradores/ilustradores")
                .then(response => {
                    console.log("Datos de ilustradores:", response.data);
                    this.ilustradores = response.data.sort((a, b) =>{
                        const apellidoA = a.apellidoIlustrador; 
                        const apellidoB = b.apellidoIlustrador;        
                        if (apellidoA < apellidoB) {
                            return -1;
                        }
                        if (apellidoA > apellidoB) {
                            return 1;
                        }
                        return 0; 
                    });                    
                })
                .catch(error => {
                    console.error("Error cargando ilustradores: ", error);
                });
        },
        loadCategorias() {
            axios.get("/api/categorias/categorias")
            .then(response => {
                console.log("Datos de categorias:", response.data);
                this.categorias = response.data;
            })
            .catch(error => {
                console.error("Error cargando categorias: ", error);
            });
        },   
        mostrarMasLibros() {
            this.librosMostrados += 10; 
        },
        toggleSeleccion(autor) {
            autor.seleccionado = !autor.seleccionado;
            this.aplicarFiltroPorAutor(autor);
        },
        aplicarFiltroPorAutor(autor) {
            console.log("Autor seleccionado:", autor); // Verifica que el autor seleccionado sea el correcto
            if (autor.seleccionado) {
                this.libros = this.librosOriginales.filter(libro => {
                    console.log("Libro:", libro); // Verifica cada libro para asegurarte de que la estructura sea la esperada
                    return libro.autores.some(libroAutor => {
                        console.log("Autor del libro:", libroAutor); // Verifica los autores de cada libro
                        console.log("Comparando autor del libro con autor seleccionado:", libroAutor.id, autor.id); // Verifica si las comparaciones de identificadores son correctas
                        return libroAutor.id === autor.id;
                    });
                });
            } else {
                this.libros = this.librosOriginales.slice(); // Restaura la lista original de libros
            }
            console.log("Libros filtrados:", this.libros); 
        },
        toggleIlustrador(ilustrador) {
            ilustrador.seleccionado = !ilustrador.seleccionado;
            this.aplicarFiltroPorIlustrador(ilustrador);
        },
        aplicarFiltroPorIlustrador(ilustrador) {
            console.log("ilustador seleccionado:", ilustrador);
            if (ilustrador.seleccionado){
                this.libros = this.librosOriginales.filter(libro => {
                    console.log("Libro en ilustrador", libro);
                    return libro.ilustradores.some(libroIlustrador => {
                        console.log("Ilustrador del libro", libroIlustrador);
                        console.log("Comparando Ilustrador del libro con Ilustrador seleccionado:", libroIlustrador.id, ilustrador.id);
                        return libroIlustrador.id === ilustrador.id;
                    })
                })
            } else {
                this.libros = this.librosOriginales.slice(); // Restaura la lista original de libros
            }
            console.log("Libros filtrados:", this.libros); // Verifica los libros filtrados después de aplicar el filtro
        },
        toggleCategoria(categoria) {
            categoria.seleccionada = !categoria.seleccionada;
            this.aplicarFiltroPorCategoria(categoria);
        },
        aplicarFiltroPorCategoria(categoria){
            console.log("categoria seleccionada:", categoria);
            if (categoria.seleccionada){
                this.libros = this.librosOriginales.filter(libro => {
                    console.log("Libro en categoria", libro);
                    return libro.categorias.some(libroCategoria => {
                        console.log("Categoria del libro", libroCategoria);
                        console.log("Comparando Categoria del libro con Categoria seleccionado:", libroCategoria.id, categoria.id);
                        return libroCategoria.id === categoria.id;
                    })
                }) 
            } else {
                this.libros = this.librosOriginales.slice(); 
            }
            console.log("Libros filtrados:", this.libros); 

        },
        generarPDF() {
            axios.get('/libros-pdf', {
                responseType: 'blob' 
            })
            .then(response => {
                const blob = new Blob([response.data], { type: 'application/pdf' }); // Crea un objeto Blob con la respuesta recibida
                const url = window.URL.createObjectURL(blob); // Crea una URL para el Blob
                window.open(url); // Abre una nueva ventana o pestaña con el PDF generado
            })
            .catch(error => {
                console.error('Error al generar el PDF:', error);
            });
        },
        generarPDFAutor() {
            axios.get('/libros-pdf-autor', {
                responseType: 'blob' 
            })
            .then(response => {
                const blob = new Blob([response.data], { type: 'application/pdf' }); 
                const url = window.URL.createObjectURL(blob); 
                window.open(url);
            })
            .catch(error => {
                console.error('Error al generar el PDF:', error);
            });
        },
        logOut: function(event) {
            event.preventDefault();
            console.log("Se hizo clic en logout");
        
            axios.post('/auth/logout')
            .then(response => {                
                window.location.href = "/index.html";
                console.log("Se hizo clic en logout", response);
            })
            .catch(error => {                
                console.error("Error al cerrar sesión:", error);               
            });
        },
        irAFormulario(){
            window.location.href = "/formulario.html";
        },
    }   
}).mount("#salitaDeLibros");
