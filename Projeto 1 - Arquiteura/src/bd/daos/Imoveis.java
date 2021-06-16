package bd.daos;

import java.sql.*;
import bd.*;
import bd.core.*;
import bd.dbos.*;

public class Imoveis
{
    public static boolean cadastrado (int codigo) throws Exception
    {
        boolean retorno = false;

        try
        {
            String sql;

            sql = "SELECT * " +
                  "FROM IMOVEIS " +
                  "WHERE CODIGO = ?";

            BDSQLServer.COMANDO.prepareStatement (sql);

            BDSQLServer.COMANDO.setInt (1, codigo);

            MeuResultSet resultado = (MeuResultSet)BDSQLServer.COMANDO.executeQuery ();

            retorno = resultado.first(); 

        }
        catch (SQLException erro)
        {
            throw new Exception ("Erro ao procurar imovel");
        }

        return retorno;
    }

    public static void incluir (Imovel imovel) throws Exception
    {
        if (imovel==null)
            throw new Exception ("Imovel nao fornecido");

        try
        {
            String sql;

            sql = "INSERT INTO IMOVEIS " +
                  "(CODIGO,DONO,PRECO,CEP,NUMERORUA,COMPLEMENTO) " +
                  "VALUES " +
                  "(?,?,?,?,?,?)";

            BDSQLServer.COMANDO.prepareStatement (sql);

            BDSQLServer.COMANDO.setInt    (1, imovel.getCodigo());
            BDSQLServer.COMANDO.setString (2, imovel.getDono());
            BDSQLServer.COMANDO.setFloat  (3, imovel.getPreco());
            BDSQLServer.COMANDO.setString (4, imovel.getCep());
            BDSQLServer.COMANDO.setInt    (5, imovel.getNumeroRua());
            BDSQLServer.COMANDO.setString (6, imovel.getComplemento());

            BDSQLServer.COMANDO.executeUpdate ();
            BDSQLServer.COMANDO.commit        ();
        }
        catch (SQLException erro)
        {
			BDSQLServer.COMANDO.rollback();
            throw new Exception ("Erro ao inserir imovel");
        }
    }

    public static void excluir (int codigo) throws Exception
    {
        if (!cadastrado (codigo))
            throw new Exception ("Nao cadastrado");

        try
        {
            String sql;

            sql = "DELETE FROM IMOVEIS " +
                  "WHERE CODIGO=?";

            BDSQLServer.COMANDO.prepareStatement (sql);

            BDSQLServer.COMANDO.setInt (1, codigo);

            BDSQLServer.COMANDO.executeUpdate ();
            BDSQLServer.COMANDO.commit        ();        
        }
        catch (SQLException erro)
        {
			BDSQLServer.COMANDO.rollback();
            throw new Exception ("Erro ao excluir movel");
        }
    }
    
    public static Imovel getImovel (int codigo) throws Exception
    {
        Imovel imovel = null;

        try
        {
            String sql;

            sql = "SELECT * " +
                  "FROM IMOVEIS " +
                  "WHERE CODIGO = ?";

            BDSQLServer.COMANDO.prepareStatement (sql);

            BDSQLServer.COMANDO.setInt (1, codigo);

            MeuResultSet resultado = (MeuResultSet)BDSQLServer.COMANDO.executeQuery ();

            if (!resultado.first())
                throw new Exception ("Nao cadastrado");

            imovel = new Imovel(resultado.getInt("CODIGO"), resultado.getString("DONO"),resultado.getFloat("PRECO"),resultado.getString("CEP"),resultado.getInt("NUMERORUA"),resultado.getString("COMPLEMENTO"));
        }
        catch (SQLException erro)
        {
            throw new Exception ("Erro ao procurar imovel");
        }

        return imovel;
    }
    
    public static void alterar (Imovel imovel) throws Exception
    {
        if (imovel==null)
            throw new Exception ("Imovel nao fornecido");

        if (!cadastrado (imovel.getCodigo()))
            throw new Exception ("Nao cadastrado");

        try
        {
            String sql;

            sql = "UPDATE IMOVEIS " +
                  "SET DONO=? ," +
                  " PRECO=? ," +
                  " CEP=? ," +
                  " NUMERORUA=? ," +
                  " COMPLEMENTO=? " +
                  "WHERE CODIGO = ? ";

            BDSQLServer.COMANDO.prepareStatement (sql);
            
            BDSQLServer.COMANDO.setString (1, imovel.getDono());
            BDSQLServer.COMANDO.setFloat  (2, imovel.getPreco());
            BDSQLServer.COMANDO.setString (3, imovel.getCep());
            BDSQLServer.COMANDO.setInt    (4, imovel.getNumeroRua());
            BDSQLServer.COMANDO.setString (5, imovel.getComplemento());
            BDSQLServer.COMANDO.setInt    (6, imovel.getCodigo());

            BDSQLServer.COMANDO.executeUpdate ();
            BDSQLServer.COMANDO.commit        ();
        }
        catch (SQLException erro)
        {
			BDSQLServer.COMANDO.rollback();
            throw new Exception ("Erro ao atualizar dados de imoveis");
        }
    }
}
