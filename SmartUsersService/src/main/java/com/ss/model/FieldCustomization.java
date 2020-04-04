/**SmartSoftware User - Service */
/**
 * Description: The persistent class for the field_customization database table.
 * Name of Project: SmartSoftware
 * Created on: March 20, 2020
 * Modified on: March 20, 2020 11:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "field_customizations")
public class FieldCustomization extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 2213874593359008620L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "field_customization_id")
	private Integer id;

	
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;


	@Column(name = "code")
	private String code;

	@Column(name = "fields_to_show")
	private String fieldsToShow;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getFieldsToShow() {
		return fieldsToShow;
	}

	public void setFieldsToShow(String fieldsToShow) {
		this.fieldsToShow = fieldsToShow;
	}

		
}
