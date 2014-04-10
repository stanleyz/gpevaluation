/*******************************************************************************
 * Copyright (c) 2006 Spirit Link GmbH
 * All rights reserved.
 * 
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/
package de.spiritlink.editor.printing.model;

/**
 * 
 * @author Tom Seidel - tom.seidel@spiritlink.de
 */
public class ExampleModel {
    
    private String firstName = null;
    
    private String lastName = null;
    
    private String company = null;
    
    private String street = null;
    
    private String zip = null;
    
    private String city = null;
    
    private String country = null;
    
    
    /**
     * Quick implementation for getting an example model-object.
     */
    public static ExampleModel MOCK = new ExampleModel() {
        public String getFirstName() { return "Tom"; };
        public String getLastName() { return "Seidel"; };
        public String getCompany() { return "Spirit Link"; };
        public String getStreet() { return "Paul-Gordan-Straﬂe 13";};
        public String getZip() { return "91052";};
        public String getCity() { return "Erlangen"; };
        public String getCountry() {return "Germany"; };
    };

    /**
     * @return the city
     */
    public String getCity() {
        return this.city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the company
     */
    public String getCompany() {
        return this.company;
    }

    /**
     * @param company the company to set
     */
    public void setCompany(String company) {
        this.company = company;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return this.country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the street
     */
    public String getStreet() {
        return this.street;
    }

    /**
     * @param street the street to set
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * @return the zip
     */
    public String getZip() {
        return this.zip;
    }

    /**
     * @param zip the zip to set
     */
    public void setZip(String zip) {
        this.zip = zip;
    }
    
    
    
    

}
