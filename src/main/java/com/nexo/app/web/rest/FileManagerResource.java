package com.nexo.app.web.rest;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nexo.app.service.FileManagerService;
import com.nexo.app.web.rest.errors.NexoInternalException;

@RestController
@RequestMapping("/api")
public class FileManagerResource {

	private final Logger log = LoggerFactory.getLogger(FileManagerResource.class);

	private final FileManagerService fileManagerService;

	public FileManagerResource(FileManagerService fileManagerService) {
		this.fileManagerService = fileManagerService;
	}

	/**
	 * @param file to save
	 * @return a new full path for the file
	 */
	@PostMapping("/files")
	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("razon") String razon) {
		log.debug("Post for upload a file");
		Map<String, Object> response = new HashMap<>();
		String fullPath = "";
		try {
			fullPath = fileManagerService.subirArchivo(file, razon);
		} catch (NexoInternalException e) {
			e.printStackTrace();
			response.put("mensaje", e.getMessage());
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			response.put("mensaje", "Error al intentar guardar el archivo");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("path", fullPath);
		log.info("Retornando en controller="+fullPath);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	/**
	 * @param idImagen
	 * @return el resource (imagen) del producto
	 */
	@GetMapping("/files/descargarimagenproducto/{idImagen}")
	public ResponseEntity<?> descargarImagenProducto(@PathVariable Long idImagen) {
		ResponseEntity<Resource> resource = null;
		Map<String, Object> response = new HashMap<>();
		try {
			resource = fileManagerService.downloadFileProducto(idImagen);
		} catch (NexoInternalException e) {
			e.printStackTrace();
			response.put("mensaje", e.getMessage());
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			response.put("mensaje", "Error al intentar descargar el archivo");
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return resource;
	}
}
