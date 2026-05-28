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

import chapter6.beans.Message;
import chapter6.logging.InitApplication;
import chapter6.service.MessageService;

@WebServlet(urlPatterns = { "/edit" })
public class EditServlet extends HttpServlet {

	/**
	* ロガーインスタンスの生成
	*/
    Logger log = Logger.getLogger("twitter");

    /**
    * デフォルトコンストラクタ
    * アプリケーションの初期化を実施する。
    */
    public EditServlet() {
        InitApplication application = InitApplication.getInstance();
        application.init();

    }
 // --- 編集するメッセージIDを取得し、編集画面に移動 ---
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

	  log.info(new Object(){}.getClass().getEnclosingClass().getName() +
        " : " + new Object(){}.getClass().getEnclosingMethod().getName());

	  HttpSession session = request.getSession();
      List<String> errorMessages = new ArrayList<String>();

	  String messageId = request.getParameter("editMessageId");
	  if (!isValidId(messageId, errorMessages)) {
            session.setAttribute("errorMessages", errorMessages);
            response.sendRedirect("./");
            return;
	  }

	  Message message = new MessageService().selectText(messageId);

	  request.setAttribute("editMessage", message);
	  request.getRequestDispatcher("edit.jsp").forward(request, response);
    }

    // --- 更新メッセージをDBに登録し、Top画面に移動 ---
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

	  log.info(new Object(){}.getClass().getEnclosingClass().getName() +
        " : " + new Object(){}.getClass().getEnclosingMethod().getName());

		List<String> errorMessages = new ArrayList<String>();

		String editText = request.getParameter("editText");
		String editMessageId = request.getParameter("editMessageId");

		if (!isValidEditText(editText, errorMessages)) {
			request.setAttribute("errorMessages", errorMessages);
            request.getRequestDispatcher("edit.jsp").forward(request, response);
            return;
		}

		Message editMessage = new Message();
		editMessage.setId(Integer.parseInt(editMessageId));
		editMessage.setText(editText);

		new MessageService().update(editMessage);
		response.sendRedirect("./");
    }

    private boolean isValidId(String messageId, List<String> errorMessages ) {

    	log.info(new Object(){}.getClass().getEnclosingClass().getName() +
          " : " + new Object(){}.getClass().getEnclosingMethod().getName());

    	String errorMessage = "不正なパラメータが入力されました。";
    	Message message = new MessageService().selectText(messageId);

         if(StringUtils.isEmpty(messageId)) {
        	 errorMessages.add(errorMessage);
         } else if(!messageId.matches("^[0-9]+$")) {
        	 errorMessages.add(errorMessage);
         } else if(message.getId() == 0) {
        	 errorMessages.add(errorMessage);
         }

         if (errorMessages.size() != 0) {
        	 return false;
         }

    	return true;
    }

    private boolean isValidEditText(String editText, List<String> errorMessages ) {

    	log.info(new Object(){}.getClass().getEnclosingClass().getName() +
          " : " + new Object(){}.getClass().getEnclosingMethod().getName());


         if (StringUtils.isBlank(editText)) {
             errorMessages.add("メッセージを入力してください");
         } else if (140 < editText.length()) {
             errorMessages.add("140文字以下で入力してください");
         }

         if (errorMessages.size() != 0) {
             return false;
         }

    	return true;
    }
}

