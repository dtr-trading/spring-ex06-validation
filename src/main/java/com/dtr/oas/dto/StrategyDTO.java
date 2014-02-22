package com.dtr.oas.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value="session", proxyMode=ScopedProxyMode.TARGET_CLASS)
public class StrategyDTO implements Serializable {
	
	private static final long serialVersionUID = -6518171412015203128L;
	
	private Integer id;

	@NotNull(message="{error.strategy.type.null}")
	@NotEmpty(message = "{error.strategy.type.empty}")
	@Size(max=20, message = "{error.strategy.type.max}")
	private String type;
	
	@NotNull(message = "{error.strategy.name.null}")
	@NotEmpty(message = "{error.strategy.name.empty}")
	@Size(max=20, message = "{error.strategy.name.max}")
	private String name;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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
	
	public String getString() {
		return String.format("StrategyDTO - Id: [%s]  Type: [%s]  Name: [%s]", this.id, this.type, this.name);
	}

}
