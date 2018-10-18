/*
 *	$Id: TestBean.java,v 1.7 2003/06/11 18:48:34 jhoeller Exp $
 */

package com.beans;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;


/**
 * Simple test bean used for testing bean factories,
 * AOP framework etc.
 * @author  Rod Johnson
 * @since 15 April 2001
 */
public class TestBean implements ITestBean, IOther {
    
	/** Holds value of property age. */
	private int age;

	/** Holds value of property name. */
	private String name;

	private ITestBean spouse;
	
	private String touchy;

	private Collection friends = new LinkedList();
	
	private Date date = new Date();

	private Float myFloat = new Float(0.0);

	public String getTouchy() {
		return touchy;
	}
	
	public TestBean() {
	}
	
	public TestBean(String name, int age) {
		this.name = name;
		this.age = age;
	}
	
	public void setTouchy(String touchy) throws Exception {
		if (touchy.indexOf('.') != -1)
			throw new Exception("Can't contain a .");
		if (touchy.indexOf(',') != -1)
			throw new NumberFormatException("Number format exception: contains a ,");
		this.touchy = touchy;
	}
    
    /** Getter for property age.
     * @return Value of property age.
     */
    public int getAge() {
        return age;
    }
    
    /** Setter for property age.
     * @param age New value of property age.
     */
  public void setAge(int age) {
        this.age = age;
    }
    
    /** Getter for property name.
     * @return Value of property name.
     */
    public String getName() {
        return name;
    }
    
    /** Setter for property name.
     * @param name New value of property name.
     */
    public void setName(String name) {
        this.name = name;
    }
    
    public String toString() {
        String s = "name=" + name + "; age=" + age + "; touchy=" + touchy;
		s += "; spouse={" + (spouse != null ? spouse.getName() : null) + "}";
		return s;
    }
	
	public ITestBean getSpouse() {
		return spouse;
	}
	
	public void setSpouse(ITestBean spouse) {
		this.spouse = spouse;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Float getMyFloat() {
		return myFloat;
	}

	public void setMyFloat(Float myFloat) {
		this.myFloat = myFloat;
	}

	public boolean equals(Object other) {
		if (other == null || !(other instanceof TestBean)) 
			return false;
		TestBean tb2 = (TestBean) other;
		if (tb2.age != age)
			return false;
			
		if (name == null)
			return tb2.name == null;
		
		if (!tb2.name.equals(name))
			return false;
		
		return true;
	}

	/**
	 * @see ITestBean#exceptional(Throwable)
	 */
	public void exceptional(Throwable t) throws Throwable {
		if (t != null)
			throw t;
	}

	/**
	 * @see ITestBean#returnsThis()
	 */
	public Object returnsThis() {
		return this;
	}

	/**
	 * @see IOther#absquatulate()
	 */
	public void absquatulate() {
		//System.out.println("IOther.absquatulate");
	}

	/**
	 * @return Collection
	 */
	public Collection getFriends() {
		return friends;
	}

	/**
	 * Sets the friends.
	 * @param friends The friends to set
	 */
	public void setFriends(Collection friends) {
		this.friends = friends;
	}

}	// class Test
