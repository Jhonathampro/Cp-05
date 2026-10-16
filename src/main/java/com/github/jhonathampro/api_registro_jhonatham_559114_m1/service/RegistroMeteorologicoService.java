package com.github.jhonathampro.api_registro_jhonatham_559114_m1.service;

import com.github.jhonathampro.api_registro_jhonatham_559114_m1.dto.RegistroMeteorologicoRequestDTO;
import com.github.jhonathampro.api_registro_jhonatham_559114_m1.dto.RegistroMeteorologicoResponseDTO;
import com.github.jhonathampro.api_registro_jhonatham_559114_m1.entities.RegistroMeteorologico;
import com.github.jhonathampro.api_registro_jhonatham_559114_m1.exceptions.ResourceNotFoundException;
import com.github.jhonathampro.api_registro_jhonatham_559114_m1.repositories.RegistroMeteorologicoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RegistroMeteorologicoService {

    @Autowired
    private RegistroMeteorologicoRepository registroRepository;

    @Transactional(readOnly = true)
    public List<RegistroMeteorologicoResponseDTO> findAllRegistros() {

        List<RegistroMeteorologico> registros = registroRepository.findAll();

        return registros.stream().map(RegistroMeteorologicoResponseDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public RegistroMeteorologicoResponseDTO findRegistroById(Long id) {

        RegistroMeteorologico registro = registroRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado. ID: " + id)
        );

        return new RegistroMeteorologicoResponseDTO(registro);
    }

    @Transactional
    public RegistroMeteorologicoResponseDTO saveRegistro(RegistroMeteorologicoRequestDTO inputDTO) {

        RegistroMeteorologico registro = new RegistroMeteorologico();
        copyDtoToRegistro(inputDTO, registro);
        registro = registroRepository.save(registro);
        return new RegistroMeteorologicoResponseDTO(registro);
    }

    @Transactional
    public RegistroMeteorologicoResponseDTO updateRegistro(Long id, RegistroMeteorologicoRequestDTO inputDTO) {

        try {
            RegistroMeteorologico registro = registroRepository.getReferenceById(id);
            copyDtoToRegistro(inputDTO, registro);
            registro = registroRepository.save(registro);
            return new RegistroMeteorologicoResponseDTO(registro);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado. ID: " + id);
        }
    }

    public void deleteRegistroById(Long id) {

        if (!registroRepository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso não encontrado. ID: " + id);
        }

        registroRepository.deleteById(id);
    }

    private void copyDtoToRegistro(RegistroMeteorologicoRequestDTO inputDTO, RegistroMeteorologico registro) {

        registro.setEstacaoNome(inputDTO.getEstacaoNome());
        registro.setDataRegistro(inputDTO.getDataRegistro());
        registro.setTemperatura(inputDTO.getTemperatura());
        registro.setPercentualUmidade(inputDTO.getPercentualUmidade());
        registro.setPrecipitacao(inputDTO.getPrecipitacao());
        registro.setCondicaoClimatica(inputDTO.getCondicaoClimatica());
    }
}
