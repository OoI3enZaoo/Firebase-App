package com.example.ben.firebase.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Ben on 12/10/2560.
 */
@JsonIgnoreProperties(ignoreUnknown = true) //จำเป็นต้องมี
public class course { //ชื่อ class เป็นชื่อเดียวกันกับที่ตั้งใน firebase
  private String subject; // child ของ parent course
  private String code; // child ของ parent course
  public String getSubject() {
    return subject;
  }
  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }



}
