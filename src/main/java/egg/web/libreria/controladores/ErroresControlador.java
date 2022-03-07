
//package egg.web.libreria.controladores;
//
//import static antlr.Utils.error;
//import javax.servlet.http.HttpServletRequest;
//import org.springframework.boot.web.servlet.error.ErrorController;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.client.HttpServerErrorException;
//import org.springframework.web.servlet.ModelAndView;
//
//@Controller
//public class ErroresControlador implements ErrorController {
//    
//    @RequestMapping(Value=/error, method = {RequestMethod.GET, RequestMethod.POST})
//    public ModelAndView renderErrorPage(HttpServletRequest httpRequest ){
//    
//    ModelAndView errorPage = new ModelAndView("error");
//    String errorMsg="";
//    
//    switch(httpErrorCode){
//        case 400:{
//            errorMsg: "El recurso solicitado no existe";
//            break;
//        }
//           case 403:{
//            errorMsg: "El recurso solicitado no existe";
//            break;
//        }
//              case 401:{
//            errorMsg: "El recurso solicitado no existe";
//            break;
//            
//        }   case 404:{
//            errorMsg: "El recurso solicitado no existe";
//            break;
//        }
//           case 500:{
//            errorMsg: "El recurso solicitado no existe";
//            break;
//        }
//        errorPage.addObjects("codigo",httpErrorCode);
//    }
//    
//}
//            
//            
//}
