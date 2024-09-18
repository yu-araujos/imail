package imail.fiap.com.br.imail_backend.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Service
public class EmailGeneratorService {

    private final List<String> domains = List.of(
            "gmail.com", "outlook.com", "hotmail.com", "demo.com", "yahoo.com"
    );

    private final List<String> names = List.of(
            "Ana", "Bruno", "Carla", "Daniel", "Eduarda", "Felipe"
    );

    // Assuntos reais e de spam
    private final List<String> realSubjects = List.of(
            "Confirmação de compra", "Renovação de Assinatura", "Pagamento Aprovado",
            "Sua conta foi atualizada", "Oferta especial: Desconto de 50%",
            "Confirmação de agendamento", "Envio de pedido", "Bem-vindo à nossa comunidade!"
    );

    private final List<String> spamSubjects = List.of(
            "GANHE DINHEIRO RÁPIDO!!!", "Você foi selecionado para um prêmio!",
            "Oferta imperdível: Perda de peso em 7 dias", "Seu nome está na lista!",
            "Clique aqui para receber sua recompensa"
    );

    // Mensagens reais e de spam
    private final List<String> realMessages = List.of(
            "Prezado cliente, sua compra foi confirmada com sucesso. Obrigado por confiar em nós.",
            "Sua assinatura foi renovada com sucesso. Agradecemos por continuar usando nossos serviços.",
            "Seu pagamento foi aprovado. Estamos preparando seu pedido para envio. Agradecemos pela preferência.",
            "Sua conta foi atualizada conforme solicitado. Caso não tenha feito essa solicitação, entre em contato conosco.",
            "Você se qualificou para receber 50% de desconto em sua próxima compra. Acesse nosso site e aproveite a promoção.",
            "Seu agendamento foi confirmado para a data solicitada. Aguardamos sua visita.",
            "Seu pedido foi despachado e está a caminho. Obrigado por comprar conosco.",
            "Bem-vindo à nossa comunidade! Estamos ansiosos para ajudá-lo a aproveitar todos os nossos benefícios."
    );

    private final List<String> spamMessages = List.of(
            "Parabéns! Você foi escolhido para ganhar um prêmio exclusivo. Clique aqui para reivindicar sua recompensa!",
            "Ganhe dinheiro rapidamente sem sair de casa! Inscreva-se agora e veja como é fácil!",
            "Perda de peso garantida em 7 dias com nosso suplemento milagroso! Não perca essa chance!",
            "Seu nome foi selecionado para uma oferta especial! Ative seu prêmio antes que expire.",
            "Clique aqui para receber seu presente surpresa! Oferta válida por tempo limitado!"
    );

    public String generateRandomEmailAddress() {
        String name = names.get(new Random().nextInt(names.size())).toLowerCase();
        String domain = domains.get(new Random().nextInt(domains.size()));
        return name + "@" + domain;
    }

    public String generateRandomSubject() {
        Random random = new Random();
        if (random.nextBoolean()) {
            return realSubjects.get(random.nextInt(realSubjects.size()));
        } else {
            return spamSubjects.get(random.nextInt(spamSubjects.size()));
        }
    }

    public String generateRandomMessage() {
        Random random = new Random();
        if (random.nextBoolean()) {
            return realMessages.get(random.nextInt(realMessages.size()));
        } else {
            return spamMessages.get(random.nextInt(spamMessages.size()));
        }
    }

    public LocalDate generateRandomDate() {
        return LocalDate.now();
    }
}
