package com.daeng.nyang.repo;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.daeng.nyang.dto.Animal;

@Repository
@Transactional
public interface AnimalApiRepo extends JpaRepository<Animal, String>{
	
	@Query(value="insert into animal(desertion_no,filename, happen_dt, happen_place,"
			+ "kind_p, kind_c, color_cd, age, weight,"
			+ "notice_no, notice_sdt, notice_edt, popfile, process_state,"
			+ "sex_cd, neuter_yn, special_mark,"
			+ "care_nm, care_tel, care_addr, org_nm, charge_nm, officetel, mbti) "
			+ "values(:desertion_no, :filename, :happen_dt, :happen_place,"
			+ ":kind_p, :kind_c, :color_cd, :age, :weight,"
			+ ":notice_no, :notice_sdt, :notice_edt, :popfile, :process_state,"
			+ ":sex_cd, :neuter_yn, :special_mark,"
			+ ":care_nm, :care_tel, :care_addr, :org_nm, :charge_nm, :officetel, :mbti )", nativeQuery=true)
	void insertAnimalFromApi(Long desertion_no,String filename, String happen_dt, String happen_place, 
			String kind_p, String kind_c, String color_cd, int age, float weight,
			String notice_no, String notice_sdt, String notice_edt, String popfile, String process_state,
			String sex_cd, String neuter_yn, String special_mark,
			String care_nm, String care_tel, String care_addr, String org_nm, String charge_nm, String officetel, String mbti);
}
