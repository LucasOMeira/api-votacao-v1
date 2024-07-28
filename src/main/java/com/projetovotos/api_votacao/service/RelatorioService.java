package com.projetovotos.api_votacao.service;

import com.projetovotos.api_votacao.dto.RelatorioDTO;
import com.projetovotos.api_votacao.model.Candidato;
import com.projetovotos.api_votacao.model.Sessao;
import com.projetovotos.api_votacao.model.Voto;
import com.projetovotos.api_votacao.repository.CandidatoRepository;
import com.projetovotos.api_votacao.repository.SessaoRepository;
import com.projetovotos.api_votacao.repository.VotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RelatorioService {

    @Autowired
    private SessaoRepository sessaoRepository;

    @Autowired
    private VotoRepository votoRepository;

    @Autowired
    private CandidatoRepository candidatoRepository;

    public RelatorioDTO gerarRelatorio(Long idSessao) {
        Optional<Sessao> optionalSessao = sessaoRepository.findById(idSessao);
        if (optionalSessao.isEmpty() || optionalSessao.get().getFim() == null) {
            throw new IllegalArgumentException("Sessão não encontrada ou ainda não foi encerrada.");
        }

        Sessao sessao = optionalSessao.get();
        List<Voto> votos = votoRepository.findBySessao(sessao);
        Map<Candidato, Long> votosPorCandidato = votos.stream()
                .collect(Collectors.groupingBy(Voto::getCandidato, Collectors.counting()));

        if (votos.size() < 2) {
            return criarRelatorioVotosMinimos(votos, votosPorCandidato);
        }

        return criarRelatorioComResultados(votos, votosPorCandidato);
    }

    private RelatorioDTO criarRelatorioComResultados(List<Voto> votos, Map<Candidato, Long> votosPorCandidato) {
        RelatorioDTO relatorio = new RelatorioDTO();
        relatorio.setDataRelatorio(LocalDateTime.now());
        relatorio.setCargo("PRESIDENTE");
        relatorio.setTotalVotos(votos.size());

        Map<String, Integer> candidatosVotos = new HashMap<>();
        votosPorCandidato.forEach((candidato, qtd) -> candidatosVotos.put(candidato.getNome(), Math.toIntExact(qtd)));

        relatorio.setCandidatosVotos(candidatosVotos);

        String vencedor = votosPorCandidato.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .map(Candidato::getNome)
                .orElse("Nenhum vencedor");

        relatorio.setVencedor(vencedor);

        return relatorio;
    }

    private RelatorioDTO criarRelatorioVotosMinimos(List<Voto> votos, Map<Candidato, Long> votosPorCandidato) {
        RelatorioDTO relatorio = new RelatorioDTO();
        relatorio.setDataRelatorio(LocalDateTime.now());
        relatorio.setCargo("PRESIDENTE");
        relatorio.setTotalVotos(votos.size());

        Map<String, Integer> candidatosVotos = new HashMap<>();
        votosPorCandidato.forEach((candidato, qtd) -> candidatosVotos.put(candidato.getNome(), Math.toIntExact(qtd)));

        relatorio.setCandidatosVotos(candidatosVotos);
        relatorio.setVencedor("Nenhum vencedor");

        return relatorio;
    }
}
