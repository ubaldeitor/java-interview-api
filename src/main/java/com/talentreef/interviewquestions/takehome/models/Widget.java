package com.talentreef.interviewquestions.takehome.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Data
@Table
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder(toBuilder=true)
public class Widget {
  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  private Long id;
  @Size(min=3, max=100)
  private String name;
  @Size(min=5, max=1000)
  private String description;
  @Min(1)
  @Max(20000)
  private Double price;

  public Widget(String name, String description, Double price) {
    this.name = name;
    this.description = description;
    this.price = price;
  }

  @Override
  public String toString() {
    return "Widget{" +
            "name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", price=" + price +
            '}';
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }
}
