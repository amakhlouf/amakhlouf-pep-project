package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

import io.javalin.Javalin;
import io.javalin.http.Context;
//import list

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
	AccountService accountService;
	MessageService messageService;

	public SocialMediaController(){
		this.accountService = new AccountService();
		this.messageService = new MessageService();
	}
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
		app.post("register", this::accountRegisterHandler);
		app.post("login", this::accountLoginHandler);
		app.get("accounts/{account_id}/messages", this::messageListByAccountIdHandler);
		app.get("messages", this::messageListHander);
		app.post("messages", this::messageCreateHandler);
		app.get("messages/{message_id}", this::messageGetHandler);
		app.delete("messages/{message_id}", this::messageDeleteHandler);
		app.patch("messages/{message_id}", this::messageUpdateHandler);

        return app;
    }

	/**
	 * accountRegisterHandler
	 * @param ctx
	 */
	private void accountRegisterHandler(Context ctx) {
		Account account = ctx.bodyAsClass(Account.class);
		Account registeredAccount = accountService.register(account);
		if(registeredAccount != null) {
			ctx.json(registeredAccount);
		} else {
			ctx.status(400);
		}
	}

	/**
	 * accountLoginHandler
	 * @param ctx
	 */
	private void accountLoginHandler(Context ctx) {
		Account account = ctx.bodyAsClass(Account.class);
		Account loggedInAccount = accountService.login(account);
		if(loggedInAccount != null) {
			ctx.json(loggedInAccount);
		} else {
			ctx.status(401);
		}
	}

    /**
	 * messageListByAccountIdHandler
	 * @param ctx
	 */
	private void messageListByAccountIdHandler(Context ctx) {
		int accountId = Integer.valueOf(ctx.pathParam("account_id"));

		ctx.json(messageService.listByAccountId(accountId));
	}

    /**
	 * messageListHander
	 * @param ctx
	 */
	private void messageListHander(Context ctx) {
		ctx.json(messageService.list());
	}

    /**
	 * messageCreateHandler
	 * @param ctx
	 */
	private void messageCreateHandler(Context ctx) {
		Message message = ctx.bodyAsClass(Message.class);
		message = messageService.create(message);
		if(message != null) {
			ctx.json(message);
		} else {
			ctx.status(400);
		}
	}

    /**
	 * messageGetHandler
	 * @param ctx
	 */
	private void messageGetHandler(Context ctx) {
		int id = Integer.valueOf(ctx.pathParam("message_id"));
		Message message = messageService.getById(id);
		if(message != null) {
			ctx.json(message);
		}
	}

    /**
	 * messageDeleteHandler
	 * @param ctx
	 */
	private void messageDeleteHandler(Context ctx) {
		int id = Integer.valueOf(ctx.pathParam("message_id"));

		Message message = messageService.delete(id);
		if(message != null) {
			ctx.json(message);
		}
	}

    /**
	 * messageUpdateHandler
	 * @param ctx
	 */
	private void messageUpdateHandler(Context ctx) {
		int id = Integer.valueOf(ctx.pathParam("message_id"));
		Message message = ctx.bodyAsClass(Message.class);
		message = messageService.update(id, message);

		if(message != null) {
			ctx.json(message);
		} else {
			ctx.status(400);
		}
	}

}