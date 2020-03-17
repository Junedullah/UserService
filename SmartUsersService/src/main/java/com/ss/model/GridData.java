/**SmartSoftware User - Service */
/**
 * Description: The persistent class for the grid_data database table.
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
@Table(name = "grid_datas")
@NamedQuery(name = "GridData.findAll", query = "SELECT gd FROM GridData gd")
public class GridData extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "gridData_id")
	private int gridDataId;

	@ManyToOne
	@JoinColumn(name = "screen_id")
	private Screen screenId;

	@ManyToOne
	@JoinColumn(name = "module_id")
	private Module moduleId;

	@ManyToOne
	@JoinColumn(name = "field_id")
	private Field fieldId;

	@ManyToOne
	@JoinColumn(name = "grid_id")
	private Grid gridId;

	@Column(name = "col_order")
	private Integer colOrder;

	private Boolean isVisible;

	private Boolean isReset;

	public int getGridDataId() {
		return gridDataId;
	}

	public void setGridDataId(int gridDataId) {
		this.gridDataId = gridDataId;
	}

	public Screen getScreenId() {
		return screenId;
	}

	public void setScreenId(Screen screenId) {
		this.screenId = screenId;
	}

	public Module getModuleId() {
		return moduleId;
	}

	public void setModuleId(Module moduleId) {
		this.moduleId = moduleId;
	}

	public Field getFieldId() {
		return fieldId;
	}

	public void setFieldId(Field fieldId) {
		this.fieldId = fieldId;
	}

	public Grid getGridId() {
		return gridId;
	}

	public void setGridId(Grid gridId) {
		this.gridId = gridId;
	}

	public Integer getColOrder() {
		return colOrder;
	}

	public void setColOrder(Integer colOrder) {
		this.colOrder = colOrder;
	}

	public Boolean getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(Boolean isVisible) {
		this.isVisible = isVisible;
	}

	public Boolean getIsReset() {
		return isReset;
	}

	public void setIsReset(Boolean isReset) {
		this.isReset = isReset;
	}

}
