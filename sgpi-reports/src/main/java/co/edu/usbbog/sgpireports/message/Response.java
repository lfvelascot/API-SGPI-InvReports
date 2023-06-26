package co.edu.usbbog.sgpireports.message;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class Response {


	public ResponseEntity<ResponseMessage> getRespuestaMensaje(String mensaje, int opc) {
		switch (opc) {
		case 0:
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(mensaje));
		case 1:
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(mensaje));
		case 2:
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		default:
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
		}
	}

	public ResponseEntity<Resource> sentRespuestaRecurso(Resource file, int opc) {
		switch (opc) {
		case 0:
			if (file != null) {
				return (file.getFilename() == null)
						? ResponseEntity.ok()
								.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + "firma.png" + "\"")
								.body(file)
						: ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
								"attachment; filename=\"" + file.getFilename() + "\"").body(file);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
		case 1:
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		case 2:
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		default:
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
		}
	}
}
