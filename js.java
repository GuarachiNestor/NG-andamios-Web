import java.io.IOException;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/sendMail")
public class SendMailServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        String mensaje = request.getParameter("mensaje");

        final String remitente = "tu-correo@gmail.com"; // tu correo remitente
        final String clave = "tu-clave-o-token";        // contraseña de aplicación

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remitente, clave);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(remitente));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("contacto@ngandamios.com")); // tu correo destino
            message.setSubject("Nuevo mensaje desde el formulario");
            message.setText("Nombre: " + nombre + "\nEmail: " + email + "\nMensaje:\n" + mensaje);

            Transport.send(message);

            response.getWriter().println("¡Mensaje enviado correctamente!");
        } catch (MessagingException e) {
            response.getWriter().println("Error al enviar: " + e.getMessage());
        }
    }
}