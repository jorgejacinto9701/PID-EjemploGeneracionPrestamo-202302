package com.example.demo.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Cuota;
import com.example.demo.entity.Solicitud;
import com.example.demo.repository.CuotaRepository;
import com.example.demo.repository.SolicitudRepository;

@Service
public class CuotaServiceImpl implements CuotaService{

	@Autowired
	private CuotaRepository cuotaRepository;
	
	@Autowired
	private SolicitudRepository solicitudRepository;
	
	
	@Override
	public void registraCuota(int idSolicitud) {
	
		Optional<Solicitud> optSolicitud = solicitudRepository.findById(idSolicitud);
		if (optSolicitud.isPresent()) {
			int dias = optSolicitud.get().getDias();
			BigDecimal tasa = optSolicitud.get().getTasa();
			Date fechaPrestamo = optSolicitud.get().getFechaPrestamo();
			BigDecimal monto = new BigDecimal(500);
			Cuota objCuota;
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(fechaPrestamo);
			
			for (int i = 1; i <= dias; i++) {
				objCuota = new Cuota();
				objCuota.setNumero(i);
				objCuota.setMonto(monto);
				objCuota.setFechaPago(cal.getTime());
				objCuota.setSolicitud(optSolicitud.get());
				cuotaRepository.save(objCuota);
				cal.set(Calendar.DAY_OF_YEAR,  cal.get(Calendar.DAY_OF_YEAR) +1);
			}
			
		}
	
	}

}
