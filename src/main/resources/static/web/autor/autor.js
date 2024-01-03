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
    guardarNuevoAutor() {
      const url = '/api/autores';
      console.log('URL de la solicitud:', url);
      // Llama a la API para guardar el nuevo autor usando Axios
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
        });
    }
  }

}).mount('#autor');