
package egg.web.libreria.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;


@Service
public class EnvioMailServicio {
    
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private TemplateEngine templateEngine;
    
//   @Async
//    public void enviar(String cuerpo, String titulo, String mail){
//        
//        SimpleMailMessage mensaje=new SimpleMailMessage();
//        mensaje.setTo(mail);
//        mensaje.setFrom("LibreriaMartin.web@gmail.com");
//        mensaje.setSubject(titulo);
//        mensaje.setText(cuerpo);
//      
//        mailSender.send(mensaje);
//    }
//    
//     @Async
//    public void enviar(String cuerpo,String titulo, String mail) throws MessagingException{
//        MimeMessage mimeMessage= mailSender.createMimeMessage();
//        MimeMessageHelper mensaje = new MimeMessageHelper(mimeMessage, true, "UTF-8");
//        mensaje.setTo(mail);
//        mensaje.setFrom("LibreriaMartin.web@gmail.com");
//        mensaje.setSubject(titulo);
//                   
//        mensaje.setText("<h1>"+cuerpo+"</h1>", true);
//         
//        this.mailSender.send(mimeMessage);
//    }

}
