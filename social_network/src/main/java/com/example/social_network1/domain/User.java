  package com.example.social_network1.domain;

/**
 * User class extends Entity<ID> where ID is Long type
 */
public class User extends Entity<Long> {
    private String firstName;
    private String password;
    private String email;
    private Integer age;

    /**
     * Constructor for  User type Object
     *
     * @param firstName -string
     * @param password  -string
     * @param email     -string
     * @param age       -integer
     */
    public User(String firstName, String password, String email, Integer age) {
        this.firstName = firstName;
        this.password = password;
        this.email = email;
        this.age = age;
    }

    /**
     * Getter for First Name
     *
     * @return firstName - string
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Getter for Email
     *
     * @return email - string
     */
    public String getEmail() {
        return email;
    }

    /**
     * Getter for Age
     *
     * @return age - integer
     */
    public Integer getAge() {
        return age;
    }

    /**
     * Getter for Password
     *
     * @return password - string
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for Age
     *
     * @param age - integer
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * Setter for Email
     *
     * @param email -string
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Setter for FirstName
     *
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Setter for LastName
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return "Name:" + firstName +
                ", Email:'" + email+
                ", Age:" + age ;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User that = (User) o;
        return getFirstName().equals(that.getFirstName()) &&
                getPassword().equals(that.getPassword());
    }
}
