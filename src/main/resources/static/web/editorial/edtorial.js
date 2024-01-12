Vue.createApp({
    data() {
        return {
            nuevaEditorial: {
                nombreEditorial: ''
            }
        };
    },
    methods: {
        capitalizarPalabras(frase) {
            // Divide la frase en palabras
            const palabras = frase.split(' ');
            // Capitalizar la primera letra de cada palabra
            const palabrasCapitalizadas = palabras.map(palabra => {
                return palabra.charAt(0).toUpperCase() + palabra.slice(1).toLowerCase();
            });
            // Unir las palabras capitalizadas de nuevo en una frase
            return palabrasCapitalizadas.join(' ');
        },
        volverAlFormulario(){
          window.location.href = '../../index.html';
      },
        guardarNuevaEditorial() {
            // Capitalizar nombres y apellidos antes de enviarlos a la base de datos
            this.nuevaEditorial.nombreEditorial = this.capitalizarPalabras(this.nuevaEditorial.nombreEditorial);
  
            // Resto del código para enviar a la base de datos
            const url = '/api/editoriales';
            console.log('URL de la solicitud:', url);
            axios.post('/api/editoriales', this.nuevaEditorial)
                .then(response => {
                    console.log(response.data);
                    // Limpiar el formulario
                    this.nuevaEditorial.nombreEditorial = '';
  
                    // Mostrar mensaje de "Registro correcto"
                    alert('Registro correcto');
                    // Puedes redirigir a otra página o realizar otras acciones después de guardar
                })
                .catch(error => {
                    console.error(error);
                });
        }
    }
  }).mount('#editorial');