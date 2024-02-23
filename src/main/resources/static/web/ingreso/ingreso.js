Vue.createApp({
    data() {
        return {
            libros: []
        }
    },
    created() {
        this.loadLibros();
       
    },
    methods: {
        loadLibros() {
            axios.get("/api/libros/libros")
                .then(response => {                    ;
                    const librosTemp = response.data;
                    librosTemp.sort((a, b) => (a.titulo > b.titulo) ? 1 : -1);
                    this.libros = librosTemp;
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
        }
    }
}).mount("#salitaDeLibros");
