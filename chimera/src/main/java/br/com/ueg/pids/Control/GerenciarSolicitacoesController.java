package br.com.ueg.pids.Control;

import java.sql.SQLException;
import java.util.List;

import org.zkoss.zul.Messagebox;

import br.com.ueg.pids.Colections.ColecaoGerenciarSolicitacoes;
import br.com.ueg.pids.Colections.ColecaoUsuario;
import br.com.ueg.pids.Enum.TypeMessage;
import br.com.ueg.pids.Model.GerenciarSolicitacoes;
import br.com.ueg.pids.Model.IModel;
import br.com.ueg.pids.Model.Usuario;
import br.com.ueg.pids.Utils.Return;

public class GerenciarSolicitacoesController extends GenericController<GerenciarSolicitacoes> {
	
	private GerenciarSolicitacoes solicitacoes = new GerenciarSolicitacoes();
	
	public List<?> getLstUsuarioDados(String keyword) {
		IModel<?> usuario = (IModel<?>) new Usuario();
		ColecaoUsuario colecaoUsuario = new ColecaoUsuario();
		try {
			colecaoUsuario.setAll(dao.pesquisarNome(usuario, keyword));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return colecaoUsuario.getAll();
	}
	@Override
	public List<GerenciarSolicitacoes> getLstEntities(String string, Usuario usuario) {
		
		IModel<?> solicitacoes = (IModel<?>) new GerenciarSolicitacoes();
		ColecaoGerenciarSolicitacoes colecaoSolicitacoes = new ColecaoGerenciarSolicitacoes();
		try {
			colecaoSolicitacoes.setAll(dao.listarSolicitacoes(solicitacoes,usuario,string));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return colecaoSolicitacoes.getAll();
	}
	
	public Return alterarSolicitacao(IModel<?> imodel, String string) {
		Return ret = validarSolicitacao(imodel);
		if (ret.isValid()) {
			ret = dao.updateSolicitacao(imodel,string);
			if(ret.isValid()){
				if(string.equalsIgnoreCase("APROVADA")){
					msgbox.mensagem(TypeMessage.SUCESSO, "Solicitacao aprovada!");
				}else if(string.equalsIgnoreCase("REPROVADA")){
					msgbox.mensagem(TypeMessage.SUCESSO, "Solicitacao reprovada!");
				}else if(string.equalsIgnoreCase("EXECUTADA")){
					msgbox.mensagem(TypeMessage.SUCESSO, "Solicitacao executada!");
				}
			}
		}
		return ret;
	}
	
	@Override
	public Return validar(IModel<?> imodel) {
		setSolicitacoes((GerenciarSolicitacoes) imodel);
		Return ret = new Return(true);
		if (getSolicitacoes().getTitulo() == null || getSolicitacoes().getTitulo().equals("")) {

			ret = new Return(false, "Titulo em branco ou inv�lido!",
					TypeMessage.ERROR);
		}else  if (getSolicitacoes().getTitulo().length() < 3) {
		  ret = new Return(false, "Titulo com menos de 3 caracteres!",
					TypeMessage.ERROR);
		  
		}else  if (getSolicitacoes().getDescricao()== null || getSolicitacoes().getDescricao().equals("")) {
			ret = new Return(false, "Descri��o em branco ou inv�lida!",
					TypeMessage.ERROR);
		}else  if (getSolicitacoes().getData()== null || getSolicitacoes().getData().equals("")) {
			ret = new Return(false, "Data em branco ou inv�lida!",
					TypeMessage.ERROR);
					  
		}else if (getSolicitacoes().getHora()== null || getSolicitacoes().getHora().equals("")) {
			ret = new Return(false, "Hora em branco ou inv�lida!",
					TypeMessage.ERROR);
		}else if (getSolicitacoes().getRecurso()== null || getSolicitacoes().getRecurso().equals("")) {
			ret = new Return(false, "Recurso em branco ou inv�lida!",
					TypeMessage.ERROR);
		}
		return ret;
	}

	public GerenciarSolicitacoes getEntity(String id) {
		GerenciarSolicitacoes solicitacoes = new GerenciarSolicitacoes();
		solicitacoes.setIdsolicitacoes(Integer.parseInt(id));
		ColecaoGerenciarSolicitacoes listaSolicitacoes = new ColecaoGerenciarSolicitacoes();
		try {
			listaSolicitacoes.setAll(dao.pesquisarID(solicitacoes));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		solicitacoes = ((ColecaoGerenciarSolicitacoes) listaSolicitacoes).getIndice(0);
		return solicitacoes;
	}
	
	@Override
	public Return validarItemUnico(IModel<?> imodel) {
		// TODO Auto-generated method stub
		return null;
	}
	public GerenciarSolicitacoes getSolicitacoes() {
		return solicitacoes;
	}
	public void setSolicitacoes(GerenciarSolicitacoes solicitacoes) {
		this.solicitacoes = solicitacoes;
	}

}
