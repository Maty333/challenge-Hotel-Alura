package jdbc.modelo;




	public class Usuario {
		private String nombre;
	    private String apellido;
	    private String email;
	    private String telefono;
	    private int id;
	    private String usuario;
		private String contrasena;
		
		
		
	    public Usuario(String contrasena, String nombre, String apellido, String email, String telefono, int id,
				String usuario) {
			super();
			this.contrasena = contrasena;
			this.nombre = nombre;
			this.apellido = apellido;
			this.email = email;
			this.telefono = telefono;
			this.id = id;
			this.usuario = usuario;
		}
	    
	    
	
	    
	    
	    
	    public Usuario() {
			
		}






		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getUsuario() {
			return usuario;
		}
		public void setUsuario(String usuario) {
			this.usuario = usuario;
		}
		public String getContrasena() {
			return contrasena;
		}
		public void setContrasena(String contrasena) {
			this.contrasena = contrasena;
		}
		public String getNombre() {
			return nombre;
		}
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
		public String getApellido() {
			return apellido;
		}
		public void setApellido(String apellido) {
			this.apellido = apellido;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getTelefono() {
			return telefono;
		}
		public void setTelefono(String telefono) {
			this.telefono = telefono;
		}
		
}
