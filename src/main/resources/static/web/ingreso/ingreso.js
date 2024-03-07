Vue.createApp({
    data() {
        return {
            libros: [],
            valorBusqueda: '',
            checked: [],
            claves: [],
            esAdmin: false,
            librosMostrados: 10,
        }
    },
    created() {
        this.loadLibros();
        this.loadAtributos();

    },
    mounted(){
        const userRole = localStorage.getItem('userRole');
        if (userRole === 'ADMIN') {
            this.isAdmin = true;
        }
    },
    methods: {
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
            if (this.valorBusqueda.trim() === '') {
                this.libros = this.librosTemp;
            } else {
                this.libros = this.librosTemp.filter(libro => {                  
                    const isSelected = this.checked.length === 0 || this.checked.some(clave => {                        
                        if (clave === 'autores') {
                            return libro.autores.some(autor => {
                                return `${autor.nombreAutor} ${autor.apellidoAutor}`.toLowerCase().includes(this.valorBusqueda.toLowerCase());
                            });
                        }                        
                        if (clave === 'ilustradores') {
                            return libro.ilustradores.some(ilustrador => {
                                return `${ilustrador.nombreIlustrador} ${ilustrador.apellidoIlustrador}`.toLowerCase().includes(this.valorBusqueda.toLowerCase());
                            });
                        }                        
                        if (clave === 'categorias') {
                            return libro.categorias && libro.categorias.some(categoria => {
                                return `${categoria.palabraCategoria}`.toLowerCase().includes(this.valorBusqueda.toLowerCase());
                            });
                        }                    
                        if (clave === 'editorial') {
                            return libro.editorial.nombreEditorial.toLowerCase().includes(this.valorBusqueda.toLowerCase());
                        }   
                        if (clave === 'titulo') {
                            console.log('Valor de libro.titulo:', libro.titulo);
                            return libro.titulo.toLowerCase().includes(this.valorBusqueda.toLowerCase());
                        } 
                        if (clave === 'isbn') {
                            console.log('Valor de libro.titulo:', libro.isbn);
                            return libro.isbn.toLowerCase().includes(this.valorBusqueda.toLowerCase());
                        }                     
                        return false;
                    });
                    return isSelected;
                });
            }
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
        }
        
    }
}).mount("#salitaDeLibros");
