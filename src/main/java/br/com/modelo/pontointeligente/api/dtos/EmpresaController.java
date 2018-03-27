package br.com.modelo.pontointeligente.api.dtos;

import br.com.modelo.pontointeligente.api.controllers.EmpresaDto;
import br.com.modelo.pontointeligente.api.entities.Empresa;
import br.com.modelo.pontointeligente.api.response.Response;
import br.com.modelo.pontointeligente.api.services.EmpresaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/empresas")
@CrossOrigin(origins = "*")
public class EmpresaController {

    private static Logger log = LoggerFactory.getLogger(EmpresaController.class);

    @Autowired
    private EmpresaService empresaService;

    public EmpresaController() {
    }

    /**
     * Retorna uma empresa dado um CNPJ.
     *
     * @param cnpj
     * @return ResponseEntity<Response<EmpresaDto>>
     * */
    @GetMapping(value = "/getempresa/{cnpj}")
    public ResponseEntity<Response<EmpresaDto>> buscarPorCnpj(@PathVariable("cnpj") String cnpj){
        log.info("Bucando empresa por CNPJ: {}", cnpj);
        Response<EmpresaDto> response = new Response<EmpresaDto>();
        Optional<Empresa> empresa = empresaService.buscarPorCnpj(cnpj);

        if(!empresa.isPresent()){
            log.info("Empresa não encontrada para o CNPJ: {}", cnpj);
            response.getErrors().add("Empresa não encontrada para o CNPJ "+cnpj);
            return ResponseEntity.badRequest().body(response);
        }

        response.setData(this.converterEmpresaDto(empresa.get()));
        return ResponseEntity.ok(response);
    }

    /**
     * Converte Objeto para um Empresa DTO
     *
     * @param empresa
     * @return EmpresaDto
     * */
    private EmpresaDto converterEmpresaDto(Empresa empresa){
        EmpresaDto empresaDto = new EmpresaDto();
        empresaDto.setId(empresa.getId());
        empresaDto.setCnpj(empresa.getCnpj());
        empresaDto.setRazaoSocial(empresaDto.getRazaoSocial());
        return empresaDto;
    }


}
