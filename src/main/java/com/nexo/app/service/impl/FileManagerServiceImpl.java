package com.nexo.app.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.nexo.app.config.Constants;
import com.nexo.app.service.FileManagerService;
import com.nexo.app.service.ProductoImagenesService;
import com.nexo.app.service.dto.ProductoImagenesDTO;
import com.nexo.app.web.rest.errors.NexoInternalException;
import com.nexo.app.web.rest.errors.NexoNotFoundException;

@Service
@Transactional
public class FileManagerServiceImpl implements FileManagerService {

	ProductoImagenesService productoImagenService;

	private final Logger log = LoggerFactory.getLogger(FileManagerServiceImpl.class);

	public FileManagerServiceImpl(ProductoImagenesService productoImagenService) {
		this.productoImagenService = productoImagenService;
	}

	@Override
	public String subirArchivo(MultipartFile archivo, String tipo) throws NexoInternalException {
		log.debug("Upload a File");
		if (archivo.isEmpty()) {
			log.error("error 43");
			throw new NexoInternalException("500", "Error, el archivo esta vacio (line:29)");
		}

		String pathUpload = getPathUpload(tipo);
		if (pathUpload.equals(Constants.ERROR_INTERNO)) {
			log.error("error 48");
			throw new NexoInternalException("500", "Error en parametro de upload (line:34)");
		}

		String nombreOriginal = archivo.getOriginalFilename();
		String nombreSinComas = "";

		if (nombreOriginal != null) {
			nombreSinComas = nombreOriginal.replace(" ", "");
		}

		String nombreArchivo = UUID.randomUUID().toString() + "_" + nombreSinComas;
		Path rutaArchivo = Paths.get(pathUpload).resolve(nombreArchivo).toAbsolutePath();
		String rutaCompleta = pathUpload + "/" + rutaArchivo.getFileName().toString();
		log.debug("Guardando archivo: {}", nombreOriginal);
		log.debug("Ubicacion de guardado: {}", rutaCompleta);

		try {
			Files.copy(archivo.getInputStream(), rutaArchivo);
		} catch (IOException e) {
			log.debug("Error");
			e.printStackTrace();
			throw new NexoInternalException("500", "Error al subir el archivo (line:54)");
		}
		log.debug("Retornando="+rutaCompleta);
		return rutaCompleta;
	}

	/**
	 * @param request
	 * @return a path for upload a file
	 */
	private String getPathUpload(String request) {
		switch (request) {
		case Constants.IMAGEN_PRODUCTO:
			return "C:/aws/pdf";

		default:
			return Constants.ERROR_INTERNO;
		}
	}

	@Override
	public ResponseEntity<Resource> downloadFileProducto(Long idFile) throws NexoNotFoundException, NexoInternalException {
		
		ProductoImagenesDTO imagen=productoImagenService.findOne(idFile).orElseThrow(()->new NexoNotFoundException(Constants.NO_ENCONTRADO,"Imagen no encontrada"));
		Path rutaArchivo = Paths.get(imagen.getPath());
		Resource recurso = null;
		try {
			recurso = new UrlResource(rutaArchivo.toUri());
		} catch (Exception e) {
			e.printStackTrace();
			throw new NexoInternalException(Constants.ERROR_INTERNO,"Error al descarga la imagen");
		}
		if (recurso != null && !recurso.isReadable()) {
			log.error("error 100");
			throw new NexoInternalException(Constants.ERROR_INTERNO,"Error No se Pudo Cargar la Imagen");
		}
		HttpHeaders cabecera = new HttpHeaders();
		String nombreFile = null;
		if (recurso != null && recurso.getFilename() != null) {
			nombreFile = recurso.getFilename();
		}
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + nombreFile + "\"");
		
		return new ResponseEntity<Resource>(recurso, cabecera, HttpStatus.OK);
	}
}
