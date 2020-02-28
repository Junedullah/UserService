/**SmartSoftware User - Service */
/**
 * Description: Interface for RepositoryWeekDay 
 * Name of Project: SmartSoftware
 * Created on: March 23, 2020
 * Modified on: March 23, 2020 10:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ss.model.WeekDay;


@Repository("repositoryWeekDay")
public interface RepositoryWeekDay extends JpaRepository<WeekDay, Integer> {

	List<WeekDay> findByLanguageLanguageId(int langId);

	List<WeekDay> findByIsDeletedAndLanguageLanguageId(boolean b, int i);
	WeekDay findByDayCodeAndIsDeletedAndLanguageLanguageId(String dayCode,boolean b, int i);
	Long countByIsDeletedAndLanguageLanguageId(boolean b, int i);
}
