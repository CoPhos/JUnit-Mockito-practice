package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MessengerTest {
    private static final String CLIENT_EMAIL = "cleint@email.com";
    private static final String MSG_CONTENT = "Secret Message!";

    @Test
    void shouldSendEmail(){
        Template template = mock(Template.class);
        Client client = mock(Client.class);
        MailServer mailServer = mock(MailServer.class);
        TemplateEngine templateEngine = mock(TemplateEngine.class);

        Messenger sut = new Messenger(templateEngine,mailServer);

        when(client.getEmail()).thenReturn(CLIENT_EMAIL);
        when(templateEngine.prepareMessage(client,template)).thenReturn(MSG_CONTENT);

        sut.sendMessage(client,template);
        verify(mailServer).send(CLIENT_EMAIL, MSG_CONTENT);
    }
}