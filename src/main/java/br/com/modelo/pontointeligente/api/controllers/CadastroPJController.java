package br.com.modelo.pontointeligente.api.controllers;

import br.com.modelo.pontointeligente.api.dtos.CadastroPJDto;
import br.com.modelo.pontointeligente.api.services.EmpresaService;
import br.com.modelo.pontointeligente.api.services.FuncionarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("api/cadastro-pj")
@CrossOrigin(origins = "*")
public class CadastroPJController {

    private static final Logger log = LoggerFactory.getLogger(CadastroPJDto.class);

    @Autowired
    private FuncionarioService funcionarioService;

    @Autowired
    private EmpresaService empresaService;

    public CadastroPJController() {
    }

    @PostMapping
    public ResponseEntity<CadastroPJDto> cadastrar(@Valid @RequestBody CadastroPJDto cadastroPJDto, BindingResult result)
        throws NoSuchAlgorithmException{

        log.info("Cadastrando PJ: {}", cadastroPJDto.toString());
        return null;
    }
}
