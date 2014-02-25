package com.dtr.oas.model;

import java.io.Serializable;
import com.google.common.base.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="STRATEGY")
public class Strategy extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 96285180113476324L;
	
	@NotNull(message="{error.strategy.type.null}")
	@NotEmpty(message = "{error.strategy.type.empty}")
	@Size(max=20, message = "{error.strategy.type.max}")
	@Column(name = "TYPE", length = 20)
	private String type;
	
	@NotNull(message = "{error.strategy.name.null}")
	@NotEmpty(message = "{error.strategy.name.empty}")
	@Size(max=20, message = "{error.strategy.name.max}")
	@Column(name = "NAME", length = 20)
	private String name;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
	    return String.format("%s(id=%d, type='%s', name=%s)",
	            this.getClass().getSimpleName(),
	            this.getId(),
	            this.getType(),
	            this.getName());
	}
	
	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		
		if(!(object instanceof Strategy))
	        return false;
		
	    if(getType() == null && getName() == null)
	        return false;

	    Strategy strategy = (Strategy) object;
	    
	    if( ! (getName().equals(strategy.getName()) ) )
	        return false;

	    if( ! (getType().equals(strategy.getType()) ) )
	        return false;

	   return true;
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(getId(), getType(), getName());
		/*
		int hash = 0;
		if (this.getId() != null && this.getType() != null && this.getName() != null) {
			hash = this.getId().hashCode();
			hash += this.getType().hashCode();
			hash += this.getName().hashCode();
		}
		
		return hash;
		*/
	}
}
