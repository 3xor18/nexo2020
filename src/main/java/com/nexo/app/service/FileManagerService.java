package com.nexo.app.service;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.nexo.app.web.rest.errors.NexoInternalException;
import com.nexo.app.web.rest.errors.NexoNotFoundException;

public interface FileManagerService {

	/**
	 * @param archivo
	 * @param tipo    de archivo subido (perfil, producto, ...)
	 * @return el full path donde quedo el file
	 * @throws NexoInternalException
	 */
	String subirArchivo(MultipartFile archivo, String tipo) throws NexoInternalException;

	/**
	 * @param idFile
	 * @return un archivo
	 * @throws NexoNotFoundException
	 * @throws NexoInternalException
	 */
	ResponseEntity<Resource> downloadFileProducto(Long idFile) throws NexoNotFoundException, NexoInternalException;

}
