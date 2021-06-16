package bd.dbos;

public class Imovel implements Cloneable
{
	private int    codigo;
    private String dono;
    private float  preco;
    private String cep;
    private int    numeroRua;
    private String complemento;
    
    	// Setters:
    public void setCodigo (int codigo) throws Exception
    {
        if (codigo <= 0)
            throw new Exception ("Codigo invalido");

        this.codigo = codigo;
    }   

    public void setDono (String dono) throws Exception
    {
        if (dono==null || dono.equals(""))
            throw new Exception ("Dono nao fornecido");

        this.dono = dono;
    }

    public void setPreco (float preco) throws Exception
    {
        if (preco <= 0)
            throw new Exception ("Preco invalido");

        this.preco = preco;
    }
	
	public void setCep (String cep) throws Exception
    {
        if (cep==null || cep.equals(""))
            throw new Exception ("Cep nao fornecido");

        this.cep = cep;
    }
    
    public void setNumeroRua (int numeroRua) throws Exception
    {
        if (numeroRua <= 0)
            throw new Exception ("Numero da rua, invalido");

        this.numeroRua = numeroRua;
    }  
    
    public void setComplemento (String complemento) throws Exception
    {
        if (complemento==null || complemento.equals(""))
            throw new Exception ("Complemento nao fornecido");

        this.complemento = complemento;
    }
	
	// Getters:
    public int getCodigo ()
    {
        return this.codigo;
    }

    public String getDono ()
    {
        return this.dono;
    }

    public float getPreco ()
    {
        return this.preco;
    }
    
    public String getCep ()
    {
        return this.cep;
    }
    
    public int getNumeroRua ()
    {
        return this.numeroRua;
    }
    
    public String getComplemento ()
    {
        return this.complemento;
    }
	
    public Imovel (int codigo, String dono, float preco, String cep, int numeroRua, String complemento) throws Exception
    {
        this.setCodigo (codigo);
        this.setDono   (dono);
        this.setPreco  (preco);
        this.setCep (cep);
        this.setNumeroRua   (numeroRua);
        this.setComplemento  (complemento);
    }
		
    public String toString ()
    {
        String ret="<html>";

        ret+="Codigo.......: "+this.codigo+"<br>";
        ret+="Dono.........: "+this.dono  +"<br>";
        ret+="Preco........: "+this.preco +"<br>";
        ret+="CEP..........: "+this.cep   +"<br>";
        ret+="Numero da rua: "+this.numeroRua  +"<br>";
        ret+="Complemento..: "+this.complemento +"</html>";

        return ret;
    }

    public boolean equals (Object obj)
    {
        if (this==obj)
            return true;

        if (obj==null)
            return false;

        if (!(obj instanceof Imovel))
            return false;

        Imovel imovel = (Imovel)obj;

        if (this.codigo!=imovel.codigo)
            return false;

        if (this.dono.equals(imovel.dono))
            return false;

        if (this.preco!=imovel.preco)
            return false;
            
	    if (this.cep.equals(imovel.cep))
			return false;

		if (this.numeroRua!=imovel.numeroRua)
			return false;

		if (this.complemento.equals(imovel.complemento))
            return false;

        return true;
    }

    public int hashCode ()
    {
        int ret=666;

        ret = 7*ret + new Integer(this.codigo).hashCode();
        ret = 7*ret + this.dono.hashCode();
        ret = 7*ret + new Float(this.preco).hashCode();
        ret = 7*ret + this.cep.hashCode();
        ret = 7*ret + new Integer(this.numeroRua).hashCode();
        ret = 7*ret + this.complemento.hashCode();
        
        if(ret < 0)
			ret = -ret;

        return ret;
    }


    public Imovel (Imovel modelo) throws Exception
    {
        this.codigo = modelo.codigo; 
        this.dono   = modelo.dono;   
        this.preco  = modelo.preco;  
        this.cep = modelo.cep; 
        this.numeroRua   = modelo.numeroRua;   
        this.complemento  = modelo.complemento;  
    }

    public Object clone ()
    {
        Imovel ret=null;

        try
        {
            ret = new Imovel (this);
        }
        catch (Exception erro)
        {} 
        
        return ret;
    }
}
