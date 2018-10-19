package com.server.bean;

/**
 * 人员信息-bean-测试
 *
 * @author CYX
 * @create 2018-10-18-15:13
 */
public class PeopleBean {

	private String name;
	private String phone;
	private String id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "PeopleBean{" +
				"name='" + name + '\'' +
				", phone='" + phone + '\'' +
				", id='" + id + '\'' +
				'}';
	}
}
