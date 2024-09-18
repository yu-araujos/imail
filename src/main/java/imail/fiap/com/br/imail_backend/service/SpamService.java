package imail.fiap.com.br.imail_backend.service;

import imail.fiap.com.br.imail_backend.model.EmailEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class SpamService {

    private final List<String> spamKeywords = Arrays.asList("oferta", "promoção", "dinheiro", "ganhe", "prêmio");

    public boolean isSpam(EmailEntity email, int emailsEnviadosPeloRemetente) {
        return containsSpamKeywords(email) || isHighFrequency(emailsEnviadosPeloRemetente) || containsTooManyLinks(email);
    }

    private boolean containsSpamKeywords(EmailEntity email) {
        String assunto = email.getAssuntoEmail().toLowerCase();
        String corpo = email.getCorpoDoEmail().toLowerCase();

        return spamKeywords.stream().anyMatch(assunto::contains) || spamKeywords.stream().anyMatch(corpo::contains);
    }

    private boolean isHighFrequency(int emailsEnviadosPeloRemetente) {
        int limiteMaximoEmails = 10;
        return emailsEnviadosPeloRemetente > limiteMaximoEmails;
    }

    private boolean containsTooManyLinks(EmailEntity email) {
        String corpo = email.getCorpoDoEmail();
        int count = corpo.split("http").length - 1;
        int limiteLinks = 3;
        return count > limiteLinks;
    }
}
