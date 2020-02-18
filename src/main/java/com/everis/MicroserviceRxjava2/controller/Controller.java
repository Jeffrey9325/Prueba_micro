package com.everis.MicroserviceRxjava2.controller;

import com.everis.MicroserviceRxjava2.model.Students;
import com.everis.MicroserviceRxjava2.service.StudentServiceImpl;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/Students/v1.0")
public class Controller {

  @Autowired
  public StudentServiceImpl repository;
  /**
   * search by Name method.
   * @param fullName full name
   * @return Students
   */

  @GetMapping("/names/{fullName}")
  public Flowable<Students> searchbyName(@PathVariable final String fullName) {
    return repository.searchbyName(fullName)
            .doOnComplete(() -> System.out.println("Search by Name Finished"))
            .doOnError(error -> System.out.println("Error"))
            .subscribeOn(Schedulers.io());
  }
  /**
   * search by Document method.
   * @param document document number
   * @return Students
   */

  @GetMapping("/documents/{document}")
  public Single<Students> searchbyDocument(@PathVariable final String document) {
    return repository.searchbyDocument(document)
            .doOnSuccess(ok -> System.out.println("Search by Document Finished"))
            .doOnError(error -> System.out.println("Error"))
            .subscribeOn(Schedulers.io());
  }
  /**
   * search by rank date of Birth method.
   * @param fromDate date
   * @param toDate date
   * @return Students
   */

  @GetMapping("/dates/{fromDate}/{toDate}")
  public Flowable<Students> searchbyrankdateofBirth(
      @PathVariable @DateTimeFormat(iso = ISO.DATE) final Date fromDate,
      @PathVariable  @DateTimeFormat(iso = ISO.DATE)  final Date toDate) {
    return repository.searchbyrankdateofBirth(fromDate, toDate)
            .doOnComplete(() -> System.out.println("Search by Date of Birth Finished"))
            .doOnError(error -> System.out.println("Error"))
            .subscribeOn(Schedulers.io());
  }
  /**
   * create Student method.
   * @param student student
   * @return Students
   */

  @PostMapping("/")
  @ApiOperation(value = "Obtiene los seguros del cliente que tiene acceso el sistema.",
          produces = MediaType.APPLICATION_JSON_VALUE,
          consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
          httpMethod = "POST",
          notes = "classpath:swagger/notes/insurance.md")
  @ApiResponses({
      @ApiResponse(
            code = 200,
            message = "Se obtuvo el detalle de la persona correctamente",
            response = Students.class),
        @ApiResponse(
            code = 400,
            message = "El cliente envi&oacute; datos incorrectos."),
        @ApiResponse(
            code = 500,
            message = "Error al obtener el detalle de la persona"),
        @ApiResponse(
            code = 503,
            message = "El servicio no se encuentra disponible.")})
  @ResponseStatus(HttpStatus.CREATED)
  public Single<Students> createStudent(@Valid @RequestBody final Students student) {
    return repository.createStudent(student)
            .doOnSuccess(s -> System.out.println("Create Finished"))
            .doOnError(e -> System.err.println("error"))
            .subscribeOn(Schedulers.io());
  }
  /**
   * all Students method.
   * @return Students
   */

  @GetMapping("/")
  /*public Flowable<Students> allStudents() {
    return repository.allStudents()
			.doOnComplete(() -> System.out.println("all students Finished"))
			.doOnError(error -> System.err.println("error"))
			.subscribeOn(Schedulers.io());
  }*/
  public Observable<Students> allStudents() {
	return repository.allStudents()
			.doOnComplete(() -> System.out.println("Finished"))
			.doOnError(error -> System.out.println("Error"))
			.subscribeOn(Schedulers.io());
  }

	/**
	 * modify Student method.
	 * @param idStudents students id.
	 * @param student students.
	 * @return Students
	 */

  @PutMapping("/{idStudents}")
  public Single<Students> modifyStudent(@PathVariable final String idStudents,
	  @Valid @RequestBody final Students student) {
	return repository.modifyStudent(idStudents, student)
	.doOnSuccess(s -> System.out.println("Finished modify Students"))
	.doOnError(e -> System.err.println("error modify Students"))
	.subscribeOn(Schedulers.io());
  }

    /**
     * delete Students method.
     * @param idStudents students id
     * @return
     */

  @DeleteMapping("/{idStudents}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Completable deleteStudents(@PathVariable final String idStudents) {
	return repository.deleteStudents(idStudents)
			.doOnComplete(() -> System.out.println("finished delete"))
			.doOnError(e -> System.out.println("Error delete"))
			.subscribeOn(Schedulers.io());
  }
}
