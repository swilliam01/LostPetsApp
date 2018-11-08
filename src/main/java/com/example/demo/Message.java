package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Message {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @NotNull
  @Size(min = 3)
  private String content;

  @NotNull
  @Size(min=4)
  private String sentBy;

  @NotNull
  @Size(min = 3)
  private String date;

  public Message() {
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getSentBy() {
    return sentBy;
  }

  public void setSentBy(String sentBy) {
    this.sentBy = sentBy;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }
}