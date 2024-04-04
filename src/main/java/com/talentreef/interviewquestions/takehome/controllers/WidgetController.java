package com.talentreef.interviewquestions.takehome.controllers;

import com.talentreef.interviewquestions.takehome.models.Widget;
import com.talentreef.interviewquestions.takehome.services.WidgetService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@Validated
@CrossOrigin(origins = { "*" })
@RequestMapping(value = "/v1/widgets", produces = MediaType.APPLICATION_JSON_VALUE)
public class WidgetController {

  private final WidgetService widgetService;

  public WidgetController(WidgetService widgetService) {
    Assert.notNull(widgetService, "widgetService must not be null");
    this.widgetService = widgetService;
  }

  @GetMapping
  public ResponseEntity<List<Widget>> getAllWidgets() {
    return ResponseEntity.ok(widgetService.getAllWidgets());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Widget> getWidgetById(@PathVariable @Size(min=3, max=100) String id) {
    return ResponseEntity.ok(widgetService.getWidgetById(id));
  }

  @PostMapping
  public ResponseEntity<Widget> addWidget(@Valid @RequestBody Widget widget) {
    return ResponseEntity.ok(widgetService.addWidget(widget));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Widget> updateWidget(@PathVariable @Size(min=3, max=100) String id, @Valid @RequestBody Widget widget) {
    return ResponseEntity.ok(widgetService.updateWidget(id, widget));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<List<Widget>> deleteWidget(@PathVariable @Size(min=3, max=100) String id) {
    return ResponseEntity.ok(widgetService.deleteWidget(id));
  }

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
    return new ResponseEntity<>("not valid due to validation error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
  }
}
