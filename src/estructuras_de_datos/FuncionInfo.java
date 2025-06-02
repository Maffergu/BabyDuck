package estructuras_de_datos;

import java.util.*;

public class FuncionInfo {
    private String tipoRetorno;
    private int direccionInicio;
    private List<ParameterInfo> parametros;
    private VariableTable variableTable;

    public FuncionInfo(String tipoRetorno, int direccionInicio) {
        this.tipoRetorno = tipoRetorno;
        this.direccionInicio = direccionInicio;
        this.parametros = new ArrayList<>();
        this.variableTable = new VariableTable();
    }

    public void agregarParametro(ParameterInfo parametro) {
        parametros.add(parametro);
    }

    public List<ParameterInfo> getParametros() {
        return parametros;
    }

    public String getTipoRetorno() {
        return tipoRetorno;
    }

    public int getDireccionInicio() {
        return direccionInicio;
    }

    public void setDireccionInicio(int direccionInicio) {
        this.direccionInicio = direccionInicio;
    }

    public VariableTable getVariableTable() {
        return variableTable;
    }

    public void setVariableTable(VariableTable variableTable) {
        this.variableTable = variableTable;
    }

    @Override
    public String toString() {
        return "FuncionInfo{" +
                "tipoRetorno='" + tipoRetorno + '\'' +
                ", direccionInicio=" + direccionInicio +
                ", parametros=" + parametros +
                ", variableTable=" + variableTable +
                '}';
    }
}
