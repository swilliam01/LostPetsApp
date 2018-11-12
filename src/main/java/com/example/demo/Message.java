package com.example.demo;

import javax.persistence.*;
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

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id")
  private User user;

  public Message() {
  }

  public Message(@NotNull @Size(min = 3) String content, @NotNull @Size(min = 4) String sentBy, @NotNull @Size(min = 3) String date) {
    this.content = content;
    this.sentBy = sentBy;
    this.date = date;
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

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}