package com.example.restapi.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.restapi.model.Product;
import com.example.restapi.service.ProductsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(path = "/api/products/")
@Api(value = "ProductsControllerAPI", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductsController {

	private ProductsService productsService;

	private Logger LOG = LoggerFactory.getLogger(ProductsController.class);

	@Autowired
	public void setProductsService(ProductsService productsService) {
		this.productsService = productsService;
	}

	@ApiOperation(value = "Gets all the products", responseContainer = "List", response = Product.class)
	@RequestMapping(path = "/", method = RequestMethod.GET)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Obtención exitosa de los productos", responseContainer = "List", response = Product.class),
			@ApiResponse(code = 401, message = "El usuario no está autorizado para ejecutar esta operación"),
			@ApiResponse(code = 403, message = "El usuario no tiene los permisos necesarios para ejecutar esta operación"),
			@ApiResponse(code = 500, message = "Ha ocurrido un error al obtener los productos") })
	public List<Product> getAllProducts() {
		return productsService.getAllProducts();
	}

	@RequestMapping(path = "{id}", method = RequestMethod.GET)
	@ApiOperation(value = "Gets the product with specific id", response = Product.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Obtención exitosa del producto", response = Product.class),
			@ApiResponse(code = 401, message = "El usuario no está autorizado para ejecutar esta operación"),
			@ApiResponse(code = 403, message = "El usuario no tiene los permisos necesarios para ejecutar esta operación"),
			@ApiResponse(code = 404, message = "El producto no existe para el id especificado"),
			@ApiResponse(code = 500, message = "Ha ocurrido un error al obtener el Producto") })
	public Product getProduct(@ApiParam(value = "Id of a product") @PathVariable(name = "id") String id) {
		return verifyProduct(id);
	}

	@ApiOperation("Save a Product")
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "El producto fue creado satisfactoriamente", response = Product.class),
			@ApiResponse(code = 400, message = "Error al validar el producto a guardar"),
			@ApiResponse(code = 401, message = "El usuario no está autorizado para ejecutar esta operación"),
			@ApiResponse(code = 403, message = "El usuario no tiene los permisos necesarios para ejecutar esta operación"),
			@ApiResponse(code = 404, message = "El producto no existe para el id especificado"),
			@ApiResponse(code = 500, message = "Ha ocurrido un error al crear el Producto") })
	public Product saveProduct(@ApiParam(value = "The product to save") @RequestBody Product productToSave) {
		return productsService.saveProduct(productToSave);
	}

	@ApiOperation("Update a Product")
	@RequestMapping(path = "{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "El producto fue modificado satisfactoriamente", response = Product.class),
			@ApiResponse(code = 400, message = "Error al validar el producto a modificar"),
			@ApiResponse(code = 401, message = "El usuario no está autorizado para ejecutar esta operación"),
			@ApiResponse(code = 403, message = "El usuario no tiene los permisos necesarios para ejecutar esta operación"),
			@ApiResponse(code = 404, message = "El producto no existe para el id especificado"),
			@ApiResponse(code = 500, message = "Ha ocurrido un error al modificar el Producto") })
	public Product updateProduct(@ApiParam(value = "The product to update") @RequestBody Product productToUpdate,
			@PathVariable(name = "id") String id) {
		verifyProduct(id);
		return productsService.updateProduct(productToUpdate, id);
	}

	@ApiOperation(value = "Delete a product")
	@RequestMapping(path = "{id}", method = RequestMethod.DELETE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "El producto fue eliminado satisfactoriamente", response = Product.class),
			@ApiResponse(code = 204, message = "Esta operación no retorna contenido en el body"),
			@ApiResponse(code = 401, message = "El usuario no está autorizado para ejecutar esta operación"),
			@ApiResponse(code = 403, message = "El usuario no tiene los permisos necesarios para ejecutar esta operación"),
			@ApiResponse(code = 404, message = "El producto no existe para el id especificado"),
			@ApiResponse(code = 500, message = "Ha ocurrido un error al eliminar el Producto") })
	public void deleteProduct(@ApiParam(value = "Id of a product to delete") @PathVariable(name = "id") String id) {
		verifyProduct(id);
		productsService.deleteProduct(id);
	}
	
	/**
	 * Verify and return the Tour given a tourId.
	 * 
	 * @param tourId
	 * @return the found Tour
	 * @throws NoSuchElementException if no Tour found
	 */
	private Product verifyProduct(String productId) throws NoSuchElementException {
		Product product = productsService.getProduct(productId);
		if (product == null) {
			throw new NoSuchElementException("El producto no existe para el id especificado: " + productId);
		}
		return product;
	}
	
	/**
	 * Exception handler if NoSuchElementException is thrown in this Controller
	 * 
	 * @param ex
	 * @return Error message String.
	 */
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NoSuchElementException.class)
	public String return400(NoSuchElementException ex) {
		return ex.getMessage();
	}
}
