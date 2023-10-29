package com.example.demo.service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Cuota;
import com.example.demo.entity.Solicitud;
import com.example.demo.repository.CuotaRepository;
import com.example.demo.repository.SolicitudRepository;

import lombok.extern.apachecommons.CommonsLog;

@Service
@CommonsLog
public class CuotaServiceImpl implements CuotaService{

	@Autowired
	private CuotaRepository cuotaRepository;
	
	@Autowired
	private SolicitudRepository solicitudRepository;
	
	
	@Override
	public void registraCuota(int idSolicitud) {
	
		Optional<Solicitud> optSolicitud = solicitudRepository.findById(idSolicitud);
		if (optSolicitud.isPresent()) {
			
			//Datos de Entrada de la solicitud
			int dias = optSolicitud.get().getDias();//30
			double tasa = optSolicitud.get().getTasa(); //20.0
			Date fechaPrestamo = optSolicitud.get().getFechaPrestamo();//2023-11-01
			double montoSolicitado  = optSolicitud.get().getMontoSolicitado(); //1000.0
		
			log.info("dias >>> " + dias );
			log.info("tasa >>> " + tasa );
			log.info("fechaPrestamo >>> " + fechaPrestamo );
			log.info("montoSolicitado >>> " + montoSolicitado );
			
			
			//Generar las cuotas
			
			double tasaDiaria = Math.pow( (tasa/100.0 + 1) , (1/30.0)) -1;
			double interes  = 	montoSolicitado * tasaDiaria * dias;
			double montoTotal = montoSolicitado + interes;
			double cuota = montoTotal / dias;

			log.info("tasaDiaria >>> " + tasaDiaria );
			log.info("interes >>> " + interes );
			log.info("montoTotal >>> " + montoTotal );
			log.info("cuota >>> " + cuota );
			

			
			Calendar cal = Calendar.getInstance();
			cal.setTime(fechaPrestamo);
			
			Cuota objCuota = null;
			for (int i = 1; i <= dias; i++) {
				objCuota = new Cuota();
				objCuota.setNumero(i);
				objCuota.setMonto(cuota);
				objCuota.setFechaPago(cal.getTime());
				objCuota.setSolicitud(optSolicitud.get());
				cuotaRepository.save(objCuota);
				
				//Se añade un día a la fecha de prestamo
				cal.set(Calendar.DAY_OF_YEAR,  cal.get(Calendar.DAY_OF_YEAR) +1);
			}
			
		}
	
	}

}
