package br.com.ueg.pids.ViewModel;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import br.com.ueg.pids.Control.CargoController;
import br.com.ueg.pids.Control.DepartamentoController;
import br.com.ueg.pids.Enum.TypeMessage;
import br.com.ueg.pids.Model.Cargo;
import br.com.ueg.pids.Model.Departamento;
import br.com.ueg.pids.Utils.Return;

@SuppressWarnings("serial")
public class CargoViewModel extends GenericViewModel<Cargo, CargoController> {

	@Wire("#CustomerCRUD")
	private Window win;
	private List<Departamento> departamentoList;
	private Departamento departamentoSelecionado;
	private List<?> lstCargo;
	String aux;
	private Integer cargoSelectedIndex;
	private String busca;

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
				Executions.sendRedirect("/paginas/cadastros_base/cargo/pesquisar.zul");
		
		}else{
			msgbox.mensagem(ret.getTypeMessage(), ret.getMensagem());
		}

		return ret;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Command
	@NotifyChange("lstCargo")
	public Return deletar() {

		Return ret = new Return(true);
		if (itemSelected == null) {
			msgbox.mensagem(TypeMessage.ERROR, "Selecione um item para ser deletado!");
		} else {
			String str = "Deseja deletar o cargo \""
					+ getItemSelected().getNome() + "\"?";
			Messagebox.show(str, "Confirm", Messagebox.YES | Messagebox.NO,
					Messagebox.QUESTION, new EventListener() {

						public void onEvent(Event event) throws Exception {
							if (event.getName().equals("onNo")) {
								return;
							} else {
								if (event.getName().equals("onYes")) {

									getControl().desativar(getItemSelected());
									msgbox.mensagem(TypeMessage.SUCESSO, "Cargo deletado com sucesso!");
									setItemSelected(null);
								}
							}
						}
					});

		}
		return ret;
	}

	@Command
	@NotifyChange("lstCargo")
	public void ListarTodos() {
		if (getBusca() == null || getBusca().equals("") || getBusca() == "") {
			setLstCargo(getControl().getListarTodos(getObject()));
		} else {
			setLstCargo(getControl().getLstEntities(busca));
		}

	}


	@Command
	public Return telaAlterar() {
		Return ret = new Return(true);
		if (itemSelected == null) {
			msgbox.mensagem(TypeMessage.AVISO, "Selecione algum item para alterar!");
		} else {
			final HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("CargoObject", this.itemSelected);
			map.put("recordMode", "EDIT");
			setCargoSelectedIndex(lstCargo.indexOf(itemSelected));
			Executions.createComponents("cargo_component.zul", null, map);
			// setItemSelected(null);
		}
		return ret;
	}

	@Command
	public void cancel() {
		Executions.sendRedirect("/paginas/initial_page.zul");
	}
	

	public List<?> getLstCargo() {
		return lstCargo;
	}

	public void setLstCargo(List<?> lstCargo) {
		this.lstCargo = lstCargo;
	}

	public String getAux() {
		return aux;
	}

	public void setAux(String aux) {
		this.aux = aux;
	}

	public Cargo getObject() {
		return new Cargo();
	}

	public CargoController getControl() {
		return new CargoController();
	}

	@NotifyChange("departamentoList")
	public List<Departamento> getDepartamentoList() {
		DepartamentoController departamentoController = new DepartamentoController();
		departamentoList = departamentoController.getLstEntities();
		return departamentoList;
	}
	

	public Departamento getDepartamentoSelecionado() {
		return departamentoSelecionado;
	}

	public void setDepartamentoSelecionado(Departamento departamentoSelecionado) {
		this.departamentoSelecionado = departamentoSelecionado;
	}

	public void setDepartamentoList(List<Departamento> departamentoList) {
		this.departamentoList = departamentoList;
	}

	public Integer getCargoSelectedIndex() {
		return cargoSelectedIndex;
	}

	public void setCargoSelectedIndex(Integer cargoSelectedIndex) {
		this.cargoSelectedIndex = cargoSelectedIndex;
	}

	public String getBusca() {
		return busca;
	}

	public void setBusca(String busca) {
		this.busca = busca;
	}

}
