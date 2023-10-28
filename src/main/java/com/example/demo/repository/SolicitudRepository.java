package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Solicitud;

public interface SolicitudRepository extends JpaRepository<Solicitud, Integer>{
	
	

}
