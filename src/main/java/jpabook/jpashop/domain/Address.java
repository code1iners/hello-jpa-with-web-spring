package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

/**
 * <h3>Embeddable type.</h5>
 * <ul>
 *     <li>Properties is only have to settable by public or <b>protected</b> constructor.</li>
 *     <li>Do not create setter methods <b>(for safety)</b>.</li>
 *     <li><b>Why?</b> it's when JPA implementation library created, able to support many features such as Reflection or Proxy.</li>
 * </ul>
 */
@Embeddable
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    protected Address() {
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
