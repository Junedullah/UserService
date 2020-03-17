/**SmartSoftware User - Service */
/**
 * Description: The persistent class for the app_config_setting database table.
 * Name of Project: SmartSoftware
 * Created on: March 11, 2020
 * Modified on: March 11, 2020 11:19:38 AM
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
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity @org.hibernate.annotations.Entity(dynamicInsert = true)
@Table(name = "app_config_settings")
@NamedQuery(name = "AppConfigSetting.findAll", query = "SELECT a FROM AppConfigSetting a")
public class AppConfigSetting extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "app_config_setting_id")
	private int appConfigSettingId;

	@Column(name = "config_name")
	private String configName;

	@Column(name = "config_value")
	private String configValue;

	public int getAppConfigSettingId() {
		return appConfigSettingId;
	}

	public void setAppConfigSettingId(int appConfigSettingId) {
		this.appConfigSettingId = appConfigSettingId;
	}

	public String getConfigName() {
		return configName;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}

	public String getConfigValue() {
		return configValue;
	}

	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}

}