package br.com.modelo.pontointeligente.api.controller;

import br.com.modelo.pontointeligente.api.dtos.LancamentoDto;
import br.com.modelo.pontointeligente.api.entities.Funcionario;
import br.com.modelo.pontointeligente.api.entities.Lancamento;
import br.com.modelo.pontointeligente.api.enums.TipoEnum;
import br.com.modelo.pontointeligente.api.services.FuncionarioService;
import br.com.modelo.pontointeligente.api.services.LancamentoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class LancamentoControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private LancamentoService lancamentoService;

    @Autowired
    private FuncionarioService funcionarioService;

    private static final String URL_BASE = "/api/lancamentos/";
    private static final Long ID_FUNCIONARIO = 56L;
    private static final Long ID_LANCAMENTO = 303L;
    private static final String TIPO = TipoEnum.INICIO_TRABALHO.name();
    private static final Date DATA = new Date();

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Test
    public void testCadastrarLancamento() throws Exception {

        Lancamento lancamento = obterDadosLancamento();
        BDDMockito.given(this.funcionarioService.buscarPorId(Mockito.anyLong())).willReturn(Optional.of(new Funcionario()));
        BDDMockito.given(this.lancamentoService.persistir(Mockito.any(Lancamento.class))).willReturn(lancamento);

        mvc.perform(MockMvcRequestBuilders.post(URL_BASE)
                .content(this.obterJsonRequisicaoPost())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(ID_LANCAMENTO))
                .andExpect(jsonPath("$.data.tipo").value(TIPO))
                .andExpect(jsonPath("$.data.data").value(this.dateFormat.format(DATA)))
                .andExpect(jsonPath("$.data.funcionarioId").value(ID_FUNCIONARIO))
                .andExpect(jsonPath("$.errors").isEmpty());
    }

    //@Test
    public void testCadastrarLancamentoFuncionarioIdInvalido() throws Exception {
        BDDMockito.given(this.funcionarioService.buscarPorId(Mockito.anyLong())).willReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders.post(URL_BASE)
                .content(this.obterJsonRequisicaoPost())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").value("Funcionário não encontrado. ID inexistente."))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    //@Test
    public void testRemoverLancamento() throws Exception {
        BDDMockito.given(this.lancamentoService.buscarPorId(Mockito.anyLong())).willReturn(Optional.of(new Lancamento()));

        mvc.perform(MockMvcRequestBuilders.delete(URL_BASE + ID_LANCAMENTO)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private Lancamento obterDadosLancamento() {
        Lancamento lancamento = new Lancamento();
        lancamento.setId(ID_LANCAMENTO);
        lancamento.setData(DATA);
        lancamento.setTipo(TipoEnum.valueOf(TIPO));
        lancamento.setFuncionario(new Funcionario());
        lancamento.getFuncionario().setId(ID_FUNCIONARIO);
        return lancamento;
    }

    private String obterJsonRequisicaoPost() throws JsonProcessingException{
        LancamentoDto lancamentoDto = new LancamentoDto();
        lancamentoDto.setId(null);
        lancamentoDto.setData(this.dateFormat.format(DATA));
        lancamentoDto.setTipo(TIPO);
        lancamentoDto.setFuncionarioId(ID_FUNCIONARIO);
        ObjectMapper mapper = new ObjectMapper();

        return mapper.writeValueAsString(lancamentoDto);
    }
}
