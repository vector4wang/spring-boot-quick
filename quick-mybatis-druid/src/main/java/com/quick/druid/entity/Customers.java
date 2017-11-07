package com.quick.druid.entity;


public class Customers {

  private long id;
  private String first_Name;
  private String last_Name;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getFirst_Name() {
    return first_Name;
  }

  public void setFirst_Name(String first_Name) {
    this.first_Name = first_Name;
  }


  public String getLast_Name() {
    return last_Name;
  }

  public void setLast_Name(String last_Name) {
    this.last_Name = last_Name;
  }

  @Override
  public String toString() {
    return "Customers{" +
            "id=" + id +
            ", first_Name='" + first_Name + '\'' +
            ", last_Name='" + last_Name + '\'' +
            '}';
  }
}
