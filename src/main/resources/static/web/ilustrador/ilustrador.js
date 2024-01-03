// ilustrador.js

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
      guardarNuevoIlustrador() {
        // Aquí utilizas Axios para enviar la solicitud POST al endpoint correspondiente
        axios.post('/api/ilustradores', this.nuevoIlustrador)
          .then(response => {
           // Limpiar el formulario
           this.nuevoIlustrador.nombreIlustrador= '';
           this.nuevoIlustrador.apellidoIlustrador='';
           alert('Registro correcto');
            console.log(response.data);
          })
          .catch(error => {
            // Manejar el error, si es necesario
            console.error(error);
          });
      },
    },
  });
  
  app.mount('#ilustrador');
  