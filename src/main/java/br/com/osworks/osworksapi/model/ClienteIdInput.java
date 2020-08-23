package br.com.osworks.osworksapi.model;

import com.sun.istack.NotNull;

public class ClienteIdInput {
	
	@NotNull
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
}
