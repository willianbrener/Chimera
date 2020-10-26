package br.com.ueg.pids.ViewModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import br.com.ueg.pids.Control.CargoController;
import br.com.ueg.pids.Control.UsuarioController;
import br.com.ueg.pids.Enum.Permissao;
import br.com.ueg.pids.Enum.TypeMessage;
import br.com.ueg.pids.Model.Cargo;
import br.com.ueg.pids.Model.Usuario;
import br.com.ueg.pids.Utils.Return;

@SuppressWarnings("serial")
public class UsuarioViewModel extends GenericViewModel<Usuario, UsuarioController>{

	@Wire("#CustomerCRUD")
	private Window win;
	private List<?> cargoList;
	private List<?> lstUsuario;
	String aux;
	private Integer usuarioSelectedIndex;
	private String busca;
	private List<Permissao> permissaoList = new ArrayList<Permissao>();
		
	@Init
	public void init() {
		super.init();
	}
	
	@NotifyChange("entity")
	@Command
	public Return salvar() throws SQLException {
		Return ret = new Return(true);
					getEntity().setAtivo(true);
					ret = getControl().salvar(getEntity());
			if (ret.isValid()) {
				msgbox.mensagem(TypeMessage.SUCESSO, "Cadastro realizado com sucesso!");
				Executions
						.sendRedirect("/paginas/cadastros_base/usuario/pesquisar.zul");
		
		}

		return null;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Command
	@NotifyChange("lstUsuario")
	public Return deletar() {

		Return ret = new Return(true);
		if (itemSelected == null) {
			msgbox.mensagem(TypeMessage.AVISO, "Selecione um item a ser deletado!");
		} else {
			String str = "Deseja deletar o usuario \""
					+ getItemSelected().getNome() + "\"?";
			Messagebox.show(str, "Confirm", Messagebox.YES | Messagebox.NO,
					Messagebox.QUESTION, new EventListener() {

						public void onEvent(Event event) throws Exception {
							if (event.getName().equals("onNo")) {
								return;
							} else {
								if (event.getName().equals("onYes")) {

									getControl().desativar(getItemSelected());
									msgbox.mensagem(TypeMessage.SUCESSO, "Usu�rio deletado com sucesso!");
									setItemSelected(null);
								}
							}
						}
					});

		}
		return ret;
	}
	
	@Command
	@NotifyChange("lstUsuario")
	public void ListarTodos() {
		if (getBusca() == null || getBusca().equals("") || getBusca() == "") {
			setLstUsuario(getControl().getListarTodos(getObject()));
		} else {
			setLstUsuario(getControl().getLstEntities(busca));
		}

	}
	
	@Command
	public Return telaAlterar() {
		Return ret = new Return(true);
		if (itemSelected == null) {
			msgbox.mensagem(TypeMessage.AVISO, "Selecione algum item para alterar!");
		} else {
			final HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("UsuarioObject", this.itemSelected);
			map.put("recordMode", "EDIT");
			setUsuarioSelectedIndex(lstUsuario.indexOf(itemSelected));
			Executions.createComponents("usuario_component.zul", null, map);
		}
		return ret;
	}
	

	@NotifyChange("cargoList")
	public List<?> getCargoList() {
		Cargo cargo = new Cargo();
		CargoController cargoController = new CargoController();
		cargoList = cargoController.getListarTodos(cargo);
		return cargoList;
	}
	
	@NotifyChange("permissaoList")
	public List<Permissao> getPermissaoList() {
		for (Permissao permissao : Permissao.values()) {
			permissaoList.add(permissao);
		}
		return permissaoList;
	}

	@Override
	public Usuario getObject() {
		return new Usuario();
	}

	@Override
	public UsuarioController getControl() {
		return new UsuarioController();
	}

	public Window getWin() {
		return win;
	}

	public void setWin(Window win) {
		this.win = win;
	}


	public void setCargoList(List<?> cargoList) {
		this.cargoList = cargoList;
	}

	public String getAux() {
		return aux;
	}

	public void setAux(String aux) {
		this.aux = aux;
	}

	public String getBusca() {
		return busca;
	}

	public void setBusca(String busca) {
		this.busca = busca;
	}

	public List<?> getLstUsuario() {
		return lstUsuario;
	}

	public void setLstUsuario(List<?> lstUsuario) {
		this.lstUsuario = lstUsuario;
	}

	public Integer getUsuarioSelectedIndex() {
		return usuarioSelectedIndex;
	}

	public void setUsuarioSelectedIndex(Integer usuarioSelectedIndex) {
		this.usuarioSelectedIndex = usuarioSelectedIndex;
	}

	public void setPermissaoList(List<Permissao> permissaoList) {
		this.permissaoList = permissaoList;
	}
}
