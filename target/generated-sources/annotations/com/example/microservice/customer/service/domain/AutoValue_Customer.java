
package com.example.microservice.customer.service.domain;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Id;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_Customer extends Customer {

  private final String id;
  private final String name;

  private AutoValue_Customer(
      String id,
      String name) {
    this.id = id;
    this.name = name;
  }

  @Id
  @Override
  public String id() {
    return id;
  }

  @Column
  @Override
  public String name() {
    return name;
  }

  @Override
  public String toString() {
    return "Customer{"
        + "id=" + id + ", "
        + "name=" + name
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof Customer) {
      Customer that = (Customer) o;
      return (this.id.equals(that.id()))
           && (this.name.equals(that.name()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.id.hashCode();
    h *= 1000003;
    h ^= this.name.hashCode();
    return h;
  }

  static final class Builder extends Customer.Builder {
    private String id;
    private String name;
    Builder() {
    }
    Builder(Customer source) {
      this.id = source.id();
      this.name = source.name();
    }
    @Override
    public Customer.Builder id(String id) {
      this.id = id;
      return this;
    }
    @Override
    public Customer.Builder name(String name) {
      this.name = name;
      return this;
    }
    @Override
    public Customer build() {
      String missing = "";
      if (id == null) {
        missing += " id";
      }
      if (name == null) {
        missing += " name";
      }
      if (!missing.isEmpty()) {
        throw new IllegalStateException("Missing required properties:" + missing);
      }
      return new AutoValue_Customer(
          this.id,
          this.name);
    }
  }

}
