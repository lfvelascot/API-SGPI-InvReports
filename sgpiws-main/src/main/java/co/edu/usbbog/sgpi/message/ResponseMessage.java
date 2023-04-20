package co.edu.usbbog.sgpi.message;

//Definimos los mensajes de respuesta para la carga del archivo
public class ResponseMessage {
	private String message;

	public ResponseMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}