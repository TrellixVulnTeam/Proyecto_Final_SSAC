package org.iesalixar.servidor.controller;

import java.util.List;

import org.iesalixar.servidor.error.TokenInvalidException;
import org.iesalixar.servidor.model.Category;
import org.iesalixar.servidor.model.Job;
import org.iesalixar.servidor.model.User;
import org.iesalixar.servidor.services.CategoryServiceImpl;
import org.iesalixar.servidor.services.JobServiceImpl;
import org.iesalixar.servidor.services.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class MainController {
	
	@Autowired 
	UsuarioServiceImpl usuarioService; 
	@Autowired
	CategoryServiceImpl categoryService;
	@Autowired
	JobServiceImpl jobService;
	
	@GetMapping("/categorias")
	public ResponseEntity<List<Category>> showCategories(){
		if (categoryService.showCategories().isEmpty()) {
			throw new RuntimeException("Error");
		} else {
			List<Category> categories = categoryService.showCategories();
			return ResponseEntity.ok(categories);	
		}
	}
	
	
	/**
	 * Este método recibe un token en la cabecera de la petición y comprueba si el token es válido, en caso de ser válido
	 * devuelve el usuario al que pertenece, en caso contrario devuelve la exepcion correspondiente
	 * @return usuario | null
	 */
	@GetMapping("comprobarToken")
    public ResponseEntity<User> validarToken(){
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(usuarioService.findByEmail(email)!= null) {
        	return ResponseEntity.ok(usuarioService.findByEmail(email));
        }
        else {
        	throw new TokenInvalidException();
        }
    }
	
	/**
	 * Este método recibe un objeto de tipo anuncio como parámetro y añade el
	 * anuncio donde corresponde
	 * 
	 * @param anuncio
	 * @return anuncio en caso que se haya añadido el anuncio, notFound en caso de
	 *         que el usuario no esté logueado y error correspondiente en caso de
	 *         que no se pueda crear debido a campos invalidos
	 */
//	@PostMapping("/anuncio")
//	public ResponseEntity<Job> addJob(@RequestParam MultipartFile file, @RequestParam String titulo,
//			@RequestParam String categoria, @RequestParam String precio, @RequestParam String tipoPrecio,
//			@RequestParam String descripcion, @RequestParam String ubicacion) {
//
//		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		if (email != null && usuarioService.findByEmail(email).orElse(null) != null) {
//
//			return new ResponseEntity<Job>(jobService.addJob(email, titulo, categoria, precio,
//					tipoPrecio, descripcion, ubicacion, file), HttpStatus.CREATED);
//
//		} else {
//			throw new TokenInvalidException();
//		}
//	}
	
}