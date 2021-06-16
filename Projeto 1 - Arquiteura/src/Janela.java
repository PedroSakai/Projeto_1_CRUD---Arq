import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.util.*;
import bd.daos.Imoveis;
import bd.dbos.Imovel;

public class Janela extends JFrame
{
    protected static final long serialVersionUID = 1L;
    
    // Cria os Botoes:
    protected JButton btnAlterar   = new JButton ("Alterar"  ),
    				  btnConsultar = new JButton ("Consultar"), 
    				  btnInserir   = new JButton ("Inserir"  ),
                      btnRemover   = new JButton ("Remover"  ), 
                      btnConfirmar = new JButton ("Confirmar"), 
                      btnCancelar  = new JButton ("Cancelar" ),
                      btnApagar    = new JButton ("Apagar"   );
    
    // Cria os Labels's:
    protected JLabel lbCodigo 	   = new JLabel ("Codigo:"),
    				 lbDono 	   = new JLabel ("Dono:"  ), 
    				 lbPreco 	   = new JLabel ("Preco:" ), 
    				 lbCep 		   = new JLabel ("CEP:"   ),
					 lbNRua 	   = new JLabel ("Numero da rua:"), 
					 lbComplemento = new JLabel ("Complemento:"  ), 
					 lbImovel 	   = new JLabel (""), 
					 lbLogradouro  = new JLabel ("");
    
    // Cria os Text Field:
	protected JTextField txtCodigo = new JTextField(), txtDono = new JTextField(), txtPreco = new JTextField(),
						 txtCep = new JTextField(), txtNRua = new JTextField(), txtComplemento = new JTextField();
	
	// Cria o objeto de Imovel, imovel:
    Imovel imovel = null;
    
    // Atributos usados para Dialogo (Botao Remover)
    Confirmar confirmacao;
    JDialog dialog = new JDialog();
    
    public Janela ()
    {
    	// Construtor:
        super("Imoveis");
        
        // Adiciona aos botoes a ação que acontecera ao clicar neles:
        btnAlterar.addActionListener   (new Alterar  ());
        btnConsultar.addActionListener (new Consultar());
        btnInserir.addActionListener   (new Inserir  ());
        btnRemover.addActionListener   (new Remover  ());
        btnApagar.addActionListener    (new Apagar   ());
        btnCancelar.addActionListener  (new Cancelar ());
        btnConfirmar.addActionListener (confirmacao = new Confirmar());
        
        // Cria o panel onde os botoes irao aparecer:
        JPanel pnlBotoes = new JPanel();
        FlowLayout flwBotoes = new FlowLayout();
        pnlBotoes.setLayout (flwBotoes);
        
        // 
		FlowLayout flwDialog = new FlowLayout();
		dialog.setLayout(flwDialog);
		
		// Adiciona os botoes ao panel de botoes:
		pnlBotoes.add (btnInserir  );
        pnlBotoes.add (btnAlterar  );
        pnlBotoes.add (btnConsultar);
        pnlBotoes.add (btnRemover  );
        pnlBotoes.add (btnApagar   );
        
        dialog.add(new JLabel("REMOVER?"));
        dialog.add (btnConfirmar);
        dialog.add (btnCancelar);
        dialog.setSize(300,240);
        
        // Cria o panel onde o formulario ira aparecer:
		JPanel pnlForms = new JPanel();
		GridLayout grdForms = new GridLayout(7,2);
		pnlForms.setLayout(grdForms);
		
		// Adiciona os Labels e os Texts no panel de formulario:
        pnlForms.add(lbCodigo );
        pnlForms.add(txtCodigo);
        pnlForms.add(lbDono   );
        pnlForms.add(txtDono  );
        pnlForms.add(lbPreco  );
        pnlForms.add(txtPreco );
        pnlForms.add(lbCep    );
        pnlForms.add(txtCep   );
        pnlForms.add(lbNRua   );
        pnlForms.add(txtNRua  );
        pnlForms.add(lbComplemento );
        pnlForms.add(txtComplemento);
        pnlForms.add(lbImovel      );
        pnlForms.add(lbLogradouro  );
        
        // Cria o container:
        Container cntForm = this.getContentPane();
        cntForm.setLayout (new BorderLayout());
        
        // Adiciona os Panels no container e define suas posiçoes:
        cntForm.add (pnlBotoes,  BorderLayout.SOUTH);
        cntForm.add (pnlForms, BorderLayout.CENTER);
        
        this.addWindowListener (new FechamentoDeJanela());
        
        // Torna a Janela Visivel e define seu tamanho:
        this.setSize (600,730);
        this.setVisible (true);
    }
    
    // Classe para se o Botão Alterar for clicado:
    protected class Alterar implements ActionListener
    {
          public void actionPerformed (ActionEvent e)
          {
        	  // Pega os valores dos Text Field e os coloca em variaveis locais:
        	  int   cod       = Integer.parseInt(txtCodigo.getText());
              float preco     = Float.parseFloat(txtPreco.getText ());
              int   numRua    = Integer.parseInt(txtNRua.getText  ());
              String dono     = txtDono.getText       ();
              String cep      = txtCep.getText        ();
              String comple   = txtComplemento.getText();
              
              try
              {
            	  // Instancia o objeto imovel passando as variaveis locais:
                  imovel = new Imovel(cod, dono, preco, cep, numRua, comple);
                  
                  // Cria o Logradouro passando o cep pego do text field:
                  Logradouro logradouro = (Logradouro)ClienteWS.getObjeto(Logradouro.class, "https://api.postmon.com.br/v1/cep", cep);
                  
                  // Escreve as informações do BD e do WS nos Labals:
                  lbImovel.setText(imovel.toString());
                  lbLogradouro.setText(logradouro.toString());
                  
                  // Altera o imovel, passando o objeto imovel instanciado acima:
                  Imoveis.alterar(imovel);
              }
              catch(Exception err) 
              {
            	  System.out.println(err.getMessage());
              }
              
              // Apaga os Text Fields:
        	  txtCodigo.setText		("");
              txtDono.setText  		("");
              txtPreco.setText 		("");
              txtCep.setText   		("");
              txtNRua.setText  		("");
              txtComplemento.setText("");
          }
    }

    // Classe para se o Botão Consultar for clicado:
    protected class Consultar implements ActionListener
    {
          public void actionPerformed (ActionEvent e)
          {
        	  try
              {
        		  // Pega o codigo digitado:
                  int cod = Integer.parseInt(txtCodigo.getText());
                  
                  // Instancia o objeto imovel:
                  imovel = new Imovel(Imoveis.getImovel(cod));
                  
                  // Cria o Logradouro passando o cep do imovel:
                  Logradouro logradouro = (Logradouro)ClienteWS.getObjeto(Logradouro.class, "https://api.postmon.com.br/v1/cep", imovel.getCep());
                  
                  // Escreve as informações do BD e do WS nos Labals:
                  lbImovel.setText(imovel.toString());
                  lbLogradouro.setText(logradouro.toString());

              }
              catch(Exception err) {System.out.println(err.getMessage());}
        	  
        	  // Escreve nos Text Fields as informações do imovel:
        	  txtCodigo.setText		("" + imovel.getCodigo());
              txtDono.setText  		(imovel.getDono());
              txtPreco.setText 	    ("" + imovel.getPreco ());
              txtCep.setText   		(imovel.getCep ());
              txtNRua.setText       ("" + imovel.getNumeroRua());
              txtComplemento.setText(imovel.getComplemento());
          }
    }

    // Classe para se o Botão Inserir for clicado:
    protected class Inserir implements ActionListener
    {
          public void actionPerformed (ActionEvent e)
          {
        	  // Pega os valores dos Text Field e os coloca em variaveis locais:
        	  int   cod       = Integer.parseInt(txtCodigo.getText());
              float preco     = Float.parseFloat(txtPreco.getText ());
              int   numRua    = Integer.parseInt(txtNRua.getText  ());
              String dono     = txtDono.getText       ();
              String cep      = txtCep.getText        ();
              String comple   = txtComplemento.getText();
              try
              {
            	  // Instancia o objeto imovel passando as variaveis locais:
                  imovel = new Imovel(cod, dono, preco, cep, numRua, comple);
                  
                  // Cria o Logradouro passando o cep pego do text field:
                  Logradouro logradouro = (Logradouro)ClienteWS.getObjeto(Logradouro.class, "https://api.postmon.com.br/v1/cep", cep);
                  
                  // Inclui o imovel:
                  Imoveis.incluir(imovel);
                  
                  // Escreve as informações do BD e do WS nos Labals:
                  lbImovel.setText(imovel.toString());
                  lbLogradouro.setText(logradouro.toString());
              }
              catch(Exception err) {System.out.println(err.getMessage());}
              
              // Apaga os Text Fields:
         	  txtCodigo.setText		("");
              txtDono.setText  		("");
              txtPreco.setText 		("");
              txtCep.setText   		("");
              txtNRua.setText  		("");
              txtComplemento.setText("");
          }
    }

    // Classe para se o Botão Remover for clicado:
    protected class Remover implements ActionListener
    {
        public void actionPerformed (ActionEvent e)
        {
    	    try
            {
    	    	// Pega o codigo digitado:
                int cod = Integer.parseInt(txtCodigo.getText());
                
                // Instancia o objeto imovel:
                imovel = new Imovel(Imoveis.getImovel(cod));
                
                // Cria o Logradouro passando o cep do imovel:
                Logradouro logradouro = (Logradouro)ClienteWS.getObjeto(Logradouro.class, "https://api.postmon.com.br/v1/cep", imovel.getCep());
                
                // Escreve as informações do BD e do WS nos Labals:
                lbImovel.setText(imovel.toString());
                lbLogradouro.setText(logradouro.toString());
                
                // Faz a caixa de dialogo ficar true:
				dialog.setVisible(true);
				
				// Torna o codigo da confirmação o codigo passado pelo usuario:
                confirmacao.setCodigo(cod);
            }
            catch(Exception err) {System.out.println(err.getMessage());}
    	    
            // Apaga os Text Fields:
       	  	txtCodigo.setText	  ("");
            txtDono.setText  	  ("");
            txtPreco.setText 	  ("");
            txtCep.setText   	  ("");
            txtNRua.setText       ("");
            txtComplemento.setText("");
        }
    }

    // Classe para se o Botão Confirmar for clicado:
    protected class Confirmar implements ActionListener
    {
    	// Atributo inteiro que representa o codigo:
        int cod = 0;
        
        public void actionPerformed (ActionEvent e)
        {
            try
            {
            	// Exclui o Imovel a partir do codigo:
                Imoveis.excluir(cod);
            }
            catch(Exception err)
            {
            	System.out.println(err.getMessage());
            }
            
            // Fecha o Dialog:
            dialog.dispose();
        }
        
        // Metodo para definir o codigo:
        public void setCodigo(int c)
        {
            this.cod = c;
        }
    }

    // Classe para se o Botão Cancelar for clicado:
    protected class Cancelar implements ActionListener
    {
        public void actionPerformed (ActionEvent e)
        {
        	// Fecha o dialog:
            dialog.dispose();
        }
    }

    // Classe para se o Botão Apagar for clicado:
    protected class Apagar implements ActionListener
    {
        public void actionPerformed (ActionEvent e)
        {
        	// Apaga os text fieds e os Labels com as informações, tornando seus textos vazios (""):
            txtCodigo.setText	  ("");
            txtDono.setText		  ("");
            txtPreco.setText	  ("");
            txtCep.setText		  ("");
            txtNRua.setText		  ("");
            txtComplemento.setText("");
            lbImovel.setText      ("");
            lbLogradouro.setText  ("");
        }
    }

    // Classe para se a Janela for fechada
    protected class FechamentoDeJanela extends WindowAdapter
    {
        public void windowClosing (WindowEvent e)
        {
            System.exit(0);
        }
    }
}
