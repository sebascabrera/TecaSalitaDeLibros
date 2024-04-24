Vue.createApp({
  data() {
      return {
          nuevaCategoria: {
              palabraCategoria: ''
          }
      };
  },
  methods: {
      capitalizarPalabra(palabra) {
          // Capitalizar la primera letra y convertir las siguientes a minúsculas
          return palabra.charAt(0).toUpperCase() + palabra.slice(1).toLowerCase();
      },
      volverAlFormulario(){
        window.location.href = '/formulario.html';
    },
      guardarNuevaCategoria() {
          // Capitalizar categoria antes de enviarla a la base de datos
          this.nuevaCategoria.palabraCategoria = this.capitalizarPalabra(this.nuevaCategoria.palabraCategoria);

          // Resto del código para enviar a la base de datos
          const url = '/api/categorias';
          console.log('URL de la solicitud:', url);
          axios.post('/api/categorias', this.nuevaCategoria)
              .then(response => {
                  console.log(response.data);
                  // Limpiar el formulario
                  this.nuevaCategoria.palabraCategoria = '';

                  // Mostrar mensaje de "Registro correcto"
                  alert('Registro correcto');
                  // Redirigir a otra página o realizar otras acciones después de guardar
              })
              .catch(error => {
                  console.error(error);
              });
      }
  }
}).mount('#categoria');
