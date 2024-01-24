const app = Vue.createApp({
  data() {
    return {
      nuevoIlustrador: {
        nombreIlustrador: '',
        apellidoIlustrador: '',
      },
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
      window.location.href = '../formulariodecarga/formulario.html';
  },
    guardarNuevoIlustrador() {
      // Capitalizar nombres y apellidos antes de enviarlos a la base de datos
      this.nuevoIlustrador.nombreIlustrador = this.capitalizarNombreCompleto(this.nuevoIlustrador.nombreIlustrador);
      this.nuevoIlustrador.apellidoIlustrador = this.capitalizarNombreCompleto(this.nuevoIlustrador.apellidoIlustrador);

      // Resto del código para enviar a la base de datos
      axios.post('/api/ilustradores', this.nuevoIlustrador)
        .then(response => {
          console.log(response.data);
          // Limpiar el formulario
          this.nuevoIlustrador.nombreIlustrador = '';
          this.nuevoIlustrador.apellidoIlustrador = '';
          // Mostrar mensaje de "Registro correcto"
          alert('Registro correcto');
          // Redirigir a otra página o realizar otras acciones después de guardar
        })
        .catch(error => {
          console.error(error);
          alert('El Ilustrador ya existe en la base de datos =)')
        });
    },
  },
});

app.mount('#ilustrador');

  