Vue.createApp({
  data() {
    return {
      nuevoAutor: {
        nombreAutor: '',
        apellidoAutor: ''
      }
    };
  },
  methods: {
    capitalizarPalabra(palabra) {
      // Capitalizar la primera letra y convertir las siguientes a minúsculas
      return palabra.charAt(0).toUpperCase() + palabra.slice(1).toLowerCase();
    },
    capitalizarNombreCompleto(nombreCompleto) {
      // Dividir el nombre completo en palabras
      const palabras = nombreCompleto.split(' ');

      // Capitalizar cada palabra
      const palabrasCapitalizadas = palabras.map(this.capitalizarPalabra);

      // Unir las palabras capitalizadas de nuevo en un nombre completo
      return palabrasCapitalizadas.join(' ');
    },
    volverAlFormulario(){
      window.location.href = '../../index.html';
  },
    guardarNuevoAutor() {
      // Capitalizar nombres y apellidos antes de enviarlos a la base de datos
      this.nuevoAutor.nombreAutor = this.capitalizarNombreCompleto(this.nuevoAutor.nombreAutor);
      this.nuevoAutor.apellidoAutor = this.capitalizarNombreCompleto(this.nuevoAutor.apellidoAutor);

      // Resto del código para enviar a la base de datos
      const url = '/api/autores';
      console.log('URL de la solicitud:', url);
      axios.post('/api/autores', this.nuevoAutor)
        .then(response => {
          console.log(response.data);
          // Limpiar el formulario
          this.nuevoAutor.nombreAutor = '';
          this.nuevoAutor.apellidoAutor = '';
          // Mostrar mensaje de "Registro correcto"
          alert('Registro correcto');
          // Puedes redirigir a otra página o realizar otras acciones después de guardar
        })
        .catch(error => {
          console.error(error);
          alert('El Autor ya existe en la base de datos =)')
        });
    }
  }
}).mount('#autor');
