/**SmartSoftware User - Service */
/**
 * Description: The persistent class for the grid database table.
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
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@org.hibernate.annotations.Entity(dynamicInsert = true)
@Table(name = "grids")
@NamedQuery(name = "Grid.findAll", query = "SELECT g FROM Grid g")
public class Grid extends BaseEntity implements Serializable {

	/**
	 * default generated serial version UID
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "grid_id")
	private Integer gridId;

	@ManyToOne
	@JoinColumn(name = "screen_id")
	private Screen screen;

	@ManyToOne
	@JoinColumn(name = "module_id")
	private Module module;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setGridId(Integer gridId) {
		this.gridId = gridId;
	}

	public int getGridId() {
		return gridId;
	}

	
	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public Screen getScreen() {
		return screen;
	}

	public void setScreen(Screen screen) {
		this.screen = screen;
	}

	 

}
