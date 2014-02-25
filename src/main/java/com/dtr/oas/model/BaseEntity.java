package com.dtr.oas.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass()
public abstract class BaseEntity {

	@Id
	@GeneratedValue
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
	    return String.format("%s(id=%d)", this.getClass().getSimpleName(), this.getId());
	}
	
	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		
		if(!(object instanceof BaseEntity))
	        return false;
		
	   return true;
	}
	
	@Override
	public int hashCode() {
		int hash = 0;
		
		if (this.getId() != null ) {
			hash = this.getId().hashCode();
		}
		
		return hash;
	}

}
