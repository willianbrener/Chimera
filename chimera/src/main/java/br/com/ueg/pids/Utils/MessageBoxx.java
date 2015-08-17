package br.com.ueg.pids.Utils;

import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.util.Clients;

import br.com.ueg.pids.Enum.TypeMessage;

public class MessageBoxx {
	
	public void mensagemSucesso(String mensagem){
		Clients.showNotification(mensagem, "info", null, "middle_right", 3000);
	}

	public void mensagemAviso(String mensagem){
	
		Clients.showNotification(mensagem, "warning", null, "middle_right", 3000);
	}
	
	public void mensagemConfirma(String mensagem){
		 Messagebox.show(mensagem, "Confirma��o", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION);
	}
	
	public void mensagemError(String mensagem){
		Clients.showNotification(mensagem, "error", null, "middle_right", 1000);
	}
	
	public void mensagem(TypeMessage typeMessage, String msg){
		if(typeMessage == TypeMessage.AVISO){
			this.mensagemAviso(msg);
		} else if(typeMessage == TypeMessage.CONFIRMA){
			this.mensagemConfirma(msg);
		} else if(typeMessage == TypeMessage.ERROR){
			this.mensagemError(msg);
		} else if(typeMessage == TypeMessage.SUCESSO){
			this.mensagemSucesso(msg);
		}
	}

}
