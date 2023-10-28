package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.CuotaService;

@RestController
@RequestMapping("/url/ejecucion")
public class CuotaController {

	@Autowired
	private CuotaService cuotaService;
	
	@GetMapping
	public String ejecucion(){
		
		int idSolicitud = 1;
		cuotaService.registraCuota(idSolicitud);
		
		
		return "";
	}
}
