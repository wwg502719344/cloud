/*
* CreateDate : 2018-012-15 17:43:37
* CreateBy   : vigo wu  
 */
package com.wwg.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table( name = "user_info" )
public class UserInfo {

	@GeneratedValue(generator = "JDBC")
	@Id
	@Column(name="id")
	private Integer id;

	@Column(name="name")
	private String name;

	@Column(name="create_date")
	private Date createDate;

	@Column(name="address")
	private String address;

	@Column(name="tel")
	private String tel;

	@Column(name="redis_key")
	private String redisKey;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getRedisKey() {
		return redisKey;
	}

	public void setRedisKey(String redisKey) {
		this.redisKey = redisKey;
	}

	public void setId(Integer id){
		this.id=id;
	}

	public Integer getId(){
		return this.id;
	}

	public void setName(String name){
		this.name=name;
	}

	public String getName(){
		return this.name;
	}

	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

}