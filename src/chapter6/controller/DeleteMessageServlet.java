package chapter6.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import chapter6.logging.InitApplication;
import chapter6.service.MessageService;

@WebServlet(urlPatterns = { "/deleteMessage" })
public class DeleteMessageServlet extends HttpServlet {
	/**
	* ロガーインスタンスの生成
	*/
    Logger log = Logger.getLogger("twitter");

    /**
    * デフォルトコンストラクタ
    * アプリケーションの初期化を実施する。
    */
    public DeleteMessageServlet() {
        InitApplication application = InitApplication.getInstance();
        application.init();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

	  log.info(new Object(){}.getClass().getEnclosingClass().getName() +
        " : " + new Object(){}.getClass().getEnclosingMethod().getName());

        HttpSession session = request.getSession();
        List<String> errorMessages = new ArrayList<String>();

        String deleteMessageId = request.getParameter("deleteMessageId");
        if (!isValid(deleteMessageId, errorMessages)) {
            session.setAttribute("errorMessages", errorMessages);
            response.sendRedirect("./");
            return;
        }

        new MessageService().delete(deleteMessageId);
        response.sendRedirect("./");
    }

    private boolean isValid(String deleteMessageId, List<String> errorMessages ) {

    	log.info(new Object(){}.getClass().getEnclosingClass().getName() +
          " : " + new Object(){}.getClass().getEnclosingMethod().getName());

    	String errorMessage = "不正なパラメータが入力されました。";

        if(StringUtils.isEmpty(deleteMessageId)) {
       	 errorMessages.add(errorMessage);
        } else if(!deleteMessageId.matches("^[0-9]+$")) {
       	 errorMessages.add(errorMessage);
        } else if(!new MessageService().isValidId(deleteMessageId)) {
       	 errorMessages.add(errorMessage);
        }

        if (errorMessages.size() != 0) {
       	 return false;
        }

   	return true;
    }

}
