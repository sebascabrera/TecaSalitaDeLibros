Vue.createApp({
    data() {
        return {
            libros: [],
            valorBusqueda: '',
            checked: [],
            claves: [],
            autores: [],
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
    },
    mounted(){
        const userRole = localStorage.getItem('userRole');
        if (userRole === 'ADMIN') {
            this.isAdmin = true;
        }
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
                    const librosTemp = response.data;
                    librosTemp.sort((a, b) => (a.titulo > b.titulo) ? 1 : -1);
                    this.libros = librosTemp    //.slice(0, this.librosMostrados); por el momento no hay limite total.
                    this.librosTemp = librosTemp;
                    console.log("Datos de libros:", response.data)
                    this.libros.forEach(libro => {
                        this.fetchCubierta(libro.isbn);
                        console.log("libro.isbn:", libro.isbn)
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
                        libro.cubiertaUrl = cubiertaUrl;
                    }
                })
                .catch(error => {
                    console.error('Error en la solicitud de la API externa de cubiertas de libros:', error);
                });
        },
        filtro() {
            if (this.valorBusqueda.trim() === '' && this.checked.length === 0) {
                this.libros = this.librosTemp;
            } else {
                let librosFiltrados = this.librosTemp.filter(libro => {
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
                    this.autores = response.data
                })
        },   
        mostrarMasLibros() {
            this.librosMostrados += 10; 
            this.libros = this.librosTemp.slice(0, this.librosMostrados); 
            this.libros.slice(-1).forEach(libro => {
                this.fetchCubierta(libro.isbn);
            });
        },
        iniciarSesion() {
            axios.post('/auth/signin', {
                email: this.email,
                password: this.password
            })
            .then(response => {                
                if (response.data === 'ADMIN') {
                    this.isAdmin = true;
                    window.location.href = '/web/formulariodecarga/formulario.html';
                } else {
                    console.log('No tienes permisos para acceder al formulario');                   
                }
            })
            .catch(error => {
                console.error('Error al iniciar sesión:', error.response.data);
            });
        },
        toggleSeleccion(autor) {
            autor.seleccionado = !autor.seleccionado;
            this.aplicarFiltroPorAutor(autor);
        },
        aplicarFiltroPorAutor(autor) {
            console.log("Autor seleccionado:", autor); // Verifica que el autor seleccionado sea el correcto
            this.libros = this.librosTemp.filter(libro => {
                console.log("Libro:", libro); // Verifica cada libro para asegurarte de que la estructura sea la esperada
                return libro.autores.some(libroAutor => {
                    console.log("Autor del libro:", libroAutor); // Verifica los autores de cada libro
                    console.log("Comparando autor del libro con autor seleccionado:", libroAutor.id, autor.id); // Verifica si las comparaciones de identificadores son correctas
                    return libroAutor.id === autor.id;
                });
            });
            console.log("Libros filtrados:", this.librosFiltrados); // Verifica los libros filtrados después de aplicar el filtro
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
        }
        
    }   
}).mount("#salitaDeLibros");
