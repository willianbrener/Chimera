package br.com.ueg.pids.Control;

import java.sql.SQLException;
import java.util.List;

import org.zkoss.zul.Messagebox;

import br.com.ueg.pids.Colections.ColecaoDepartamento;
import br.com.ueg.pids.Model.Departamento;
import br.com.ueg.pids.Model.IModel;
import br.com.ueg.pids.Utils.Return;

public class DepartamentoController extends GenericController<Departamento> {

	Departamento departamento = new Departamento();

	@Override
	public List<Departamento> getLstEntities() {
		Departamento departamento = new Departamento();
		ColecaoDepartamento listaDepartamento = new ColecaoDepartamento();
		try {

			listaDepartamento.setAll(dao.listarTodos(departamento));

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listaDepartamento.getAll();
	}

	@Override
	public List<?> getLstEntities(String keyword) {
		Departamento departamento = new Departamento();
		ColecaoDepartamento listaDepartamento = new ColecaoDepartamento();
		try {

			listaDepartamento.setAll(dao.pesquisarNome(departamento, keyword));

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listaDepartamento.getAll();
	}

	public List<?> listarTodos(Departamento departamento) {
		ColecaoDepartamento listaDepartamento = new ColecaoDepartamento();
		try {
			listaDepartamento.setAll(dao.listarTodos(departamento));

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listaDepartamento.getAll();
	}

	@Override
	public Return validar(IModel<?> imodel) {
		Return ret = new Return(true);
		setDepartamento((Departamento) imodel);

		if (getDepartamento().getNome() == null
				|| getDepartamento().getNome().equals("")) {

			Messagebox.show("Nome em branco ou inv�lido!", "Error",
					Messagebox.OK, Messagebox.ERROR);
			ret.setValid(false);
		} else if (getDepartamento().getResponsavel() == null
				|| getDepartamento().getResponsavel().equals("")) {
			Messagebox.show("Responsavel em branco ou inv�lido!", "Error",
					Messagebox.OK, Messagebox.ERROR);
			ret.setValid(false);

		} else if (getDepartamento().getNivel() == null
				|| getDepartamento().getNivel().equals("")) {
			Messagebox.show("Nivel em branco ou inv�lido!", "Error",
					Messagebox.OK, Messagebox.ERROR);
			ret.setValid(false);

		}
		return ret;
	}

	@Override
	public Return validarItemUnico(IModel<?> imodel) {
		return null;
	}

	public Departamento getEntity(String id) {
		Departamento departamento = new Departamento();
		departamento.setIddepartamento(Integer.parseInt(id));
		ColecaoDepartamento listaDepartamento = new ColecaoDepartamento();
		try {
			listaDepartamento.setAll(dao.pesquisarID(departamento));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		departamento = ((ColecaoDepartamento) listaDepartamento).getIndice(0);
		return departamento;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

}
